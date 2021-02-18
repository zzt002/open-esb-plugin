package com.dongdl.springboot1.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dongdl.springboot1.bean.EsbInboundMainBean;
import com.dongdl.springboot1.common.Constants;
import com.dongdl.springboot1.common.MessageException;
import com.dongdl.springboot1.config.RabbitMqConfig;
import com.dongdl.springboot1.service.IThirdPartyService;
import com.dongdl.springboot1.util.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/9 19:31 UTC+8
 * @description
 */
@Service
@Slf4j
public class ThirdPartyServiceImpl implements IThirdPartyService {

    private static final String RELOAD_EXCEL_FILE_NAME = "reload.xls";

    // ESB重载参数
    private static final String SUCCESS = "SUCCESS";
    private static final String FAIL = "FAIL";
    private static final String ESB_RELOAD_OK = "ok";
    private static final String IDS = "ids";
    private static final String SERV_CODES = "servCodes";

    // 百度ip归属参数
    private static final String BAI_DU_IP = "https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api" +
            ".php?resource_id=6006&query=";

    // 监控中心重载地址
    @Value("${zzt.URL_VPN1:xxxxxxxxxxxxxxxxxxxxxxx?}")
    public String URL_VPN1;
    @Value("${zzt.URL_VPN2:xxxxxxxxxxxxxxxx?}")
    public String URL_VPN2;

    // 省健康码
    // 省秘钥
    @Value("${zzt.PROVINCE_APP_KEY:xxxxxxxxxxxx}")
    private String PROVINCE_APP_KEY;
    // 省健康码
    @Value("${zzt.HEALTH_URL:http:xxxxxxxxxxxxxxxxxxxxxxxxxxx}")
    private String HEALTH_URL;

    private static String SECURITY;
    private AtomicBoolean healthQuit = new AtomicBoolean(false);
    // 健康码excel模板文件
    private static final String HEALTH_EXCEL_FILE = "healthCode.xlsx";

    @Autowired
    private AmqpTemplate rabbitTemplate;
    @Autowired
    private EsbServiceConsumerServiceImpl esbServiceConsumerService;
    @Resource
    private ExecutorService healthCodeFixedThreadPool;

    private String getReloadParamUrl(String esbServiceIdStr, String esbServiceCodeStr) {
        Assert.notNull(esbServiceIdStr, "参数id为空");
        Assert.notNull(esbServiceCodeStr, "参数编码为空");
        return IDS + "=" + esbServiceIdStr + "&" + SERV_CODES + "=" + esbServiceCodeStr;
    }

    @Override
    public String reloadEsb(String esbServiceIdStr, String esbServiceCodeStr, Integer backstage) {
        return reloadEsb(esbServiceIdStr, esbServiceCodeStr, backstage, 2);
    }

    public String reloadEsb(String esbServiceIdStr, String esbServiceCodeStr, Integer backstage, Integer VpnType) {
        StringBuilder url_ajax = new StringBuilder();
        if (VpnType == null || VpnType != 1) {
            url_ajax.append(URL_VPN2);
        } else {
            url_ajax.append(URL_VPN1);
        }
        url_ajax.append(getReloadParamUrl(esbServiceIdStr, esbServiceCodeStr));

        StringBuilder result = new StringBuilder(esbServiceCodeStr);
        backstage = backstage == null ? Constants.INT_ONE : backstage; // 默认为1
        if (backstage == Constants.INT_ONE) {
            rabbitTemplate.convertAndSend(RabbitMqConfig.APPLICATION_NAME +".reload", null, url_ajax.toString());
            result.append("重载已加入队列");
        } else {
            result.append(reloadAjax(url_ajax.toString()) ? "已重载" : "重载失败");
        }
        return result.toString();
    }

    public static boolean reloadAjax(String url) {
        String result = HttpUtil.doGet(url, 10000, 60000);
        String state = JSONObject.parseObject(result).getString("state");
        return state.equals(ESB_RELOAD_OK);
    }

    @Override
    public String getBaiDuIp(String ip) {
        String result = null;
        String response = HttpUtil.doGet(BAI_DU_IP + ip, "GBK");
        JSONObject jsonObject = JSONObject.parseObject(response);
        JSONArray data = jsonObject.getJSONArray("data");
        if (data != null && data.size() > 0) {
            result = data.getJSONObject(0).get("location").toString();
        }
        return result;
    }

    @Override
    public Map reloadByExcel(Integer vpnType, MultipartFile file, Integer backstage) {
        Map<Integer, List> excelMap = null;
        try {
            excelMap = OfficeUtil.readExcel2Map(file, 1);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MessageException("重载excel读取失败：" + e.getMessage());
        }

        List<String> list = Lists.newArrayList();
        if (excelMap != null && excelMap.size() > 0) {
            excelMap.forEach((key, value) -> {
                list.add(String.valueOf(value.get(0)));
            });
        }

        StringBuilder serviceErrorMessage = new StringBuilder();
        ArrayList<EsbInboundMainBean> esbInboundMainBeanList = Lists.newArrayList();
        String[] services = new String[list.size()];
        esbServiceConsumerService.checkService(list.toArray(services), serviceErrorMessage, esbInboundMainBeanList);

        HashMap<String, Object> map = null;
        if (!esbInboundMainBeanList.isEmpty()) {
            map = Maps.newHashMap();
            List<String> resultList = Lists.newArrayList();
            for (EsbInboundMainBean entity : esbInboundMainBeanList) {
                resultList.add(reloadEsb(entity.getEsbServiceId(), entity.getServiceCode(), backstage, vpnType));
                map.put("resultList", resultList);
            }
        }
        return map;
    }

    /**
     * @param vpnType
     * @param esbServiceId
     * @param servcode
     * @date 2020-07-27 11:50 GMT+8
     * @deprecated use {@link #reloadEsb(String, String, Integer, Integer)} instead
     */
    @Deprecated
    private HashMap<String, Object> reloadByPage(Integer vpnType, String esbServiceId, String
            servcode) {
        String url = null;
        if (vpnType == null || vpnType == Constants.INT_ONE) {
            url = URL_VPN1;
        } else {
            url = URL_VPN2;
        }
        HashMap<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("ids", esbServiceId);
        paramMap.put("servCodes", servcode);
        String result = HttpUtil.doPost(url, paramMap);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (!"ok".equals(jsonObject.get("state"))) {
            throw new MessageException(String.format("reload fail:%s", jsonObject.get("message")));
        }
        return paramMap;
    }

    @Override
    public void getReloadExcel(HttpServletResponse response) {
        IOUtil.downloadFile(RELOAD_EXCEL_FILE_NAME, String.format("templates/%s", RELOAD_EXCEL_FILE_NAME), response);
    }

    @Override
    public void healthCodeListExcel(HttpServletResponse response, MultipartFile file) {
        // 从excel文件info
        Map<Integer, List> infoMap = null;
        try {
            infoMap = OfficeUtil.readExcel2Map(file, 2);
        } catch (Exception e) {
            String errorMessage = String.format("excel文件读取失败：%s", e.getMessage());
            log.error(errorMessage);
            throw new MessageException(errorMessage);
        }

        // 请求健康码接口
        Vector<Map<String, Object>> vector = new Vector<>();
        // 初始化线程标记
        healthQuit.set(false);

        if (infoMap != null && !infoMap.isEmpty()) {
            // 秘钥
            setProSecurity();
            CompletableFuture[] completableFutures = infoMap.entrySet().stream().map(map -> {
                // 身份证
                List oneList = (List) map.getValue();
                String sfzh = String.valueOf(oneList.get(0));
                String xm = String.valueOf(oneList.get(1));
                String mzt = "null";

                // 浙江省健康码获取
                return CompletableFuture.supplyAsync(() -> {
                    String healthStr = getHealthOfZJ(HEALTH_URL, sfzh);
                    if (healthStr != null) {
                        // 健康码接口返回数据处理
                        return getHealthCode(healthStr);
                    }
                    return mzt;
                }, healthCodeFixedThreadPool).thenAccept((str) -> {
                    Map<String, Object> oneMap = Maps.newHashMap();
                    oneMap.put("sfzh", sfzh);
                    oneMap.put("xm", xm);
                    oneMap.put("mzt", str);
                    vector.add(oneMap);
                });
            }).toArray(CompletableFuture[]::new);

            CompletableFuture.allOf(completableFutures).join();
        }

        // 写入数据到excel
        if (!vector.isEmpty()) {
            LinkedHashMap<String, String> linkedMap = Maps.newLinkedHashMap();
            linkedMap.put("sfzh", "zjhm");
            linkedMap.put("xm", "ksxm");
            linkedMap.put("mzt", "码状态");
            String fileName = "省健康码返回结果";
            OfficeUtil.exportExcel(vector, linkedMap, 0, fileName, response);
        }
    }

    private synchronized void setProSecurity() {
        JSONObject security = null;
        if (SECURITY != null) {
            security = JSONObject.parseObject(SECURITY);
        }
        if (security == null || !"200".equals(security.getString("result_code"))) {
            int z = 0;
            do {
                z++;
                if (z > 10) {
                    throw new RuntimeException("省秘钥请求异常，请稍后再试");
                } else if (z > 1) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                SECURITY = HttpUtil.doPost(PROVINCE_APP_KEY, null);
                log.info("请求了秘钥:" + SECURITY);
                security = JSONObject.parseObject(SECURITY);
            } while (security == null || !"200".equals(security.getString("result_code")));
        }
    }

    private String getHealthOfZJ(String url, String sfzh) {
        // 数据校验
        if (StringUtil.isEmpty(sfzh)) {
            return null;
        }

        // 入参存放
        HashMap<String, Object> partMap = Maps.newHashMap();
        partMap.put("sfzh", sfzh);

        // 请求
        JSONObject jsonObject = null;
        String response = null;
        int i = 0;
        do {
            // 多线程状态判断 - 标记中断
            if (healthQuit.get()) {
                return null;
            }
            // 再次请求缓冲
            if (i > 0) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            dealParts(SECURITY, partMap);
            try {
                response = HttpUtil.doPost(url, partMap);
            } catch (Exception e) {
                if (i > 4) {
                    healthQuit.set(true);
                    log.error(String.format("[%s]健康码接口请求错误：%s；错误信息：%s", url, response, e.getMessage()));
                    throw new RuntimeException("健康码接口请求错误超过" + i + "次，请稍后再试");
                }
            }
            if (response != null) {
                jsonObject = JSONObject.parseObject(response);
                if (jsonObject != null && jsonObject.containsKey("code")) {
                    String code = jsonObject.getString("code");
                    if ("00".equals(code)) {
                        break;
                    } else {
                        setProSecurity();
                    }
                }
            }
            i++;
        } while (true);
        return response;
    }

    /**
     * 省接口
     * 入参
     *
     * @param partMap 原始入参数据
     * @return
     */
    private void dealParts(String security, HashMap<String, Object> partMap) {
        JSONObject json = JSONObject.parseObject(security);
        JSONArray data = json.getJSONArray("data");
        String requestToken = data.getJSONObject(0).getString("requestSecret");
        long requestTime = System.currentTimeMillis();
        // 获取appKey和sign
        String appKey = data.getJSONObject(0).getString("app_key");
        String sign = MD5Utils.ecodeByMD5(appKey + requestToken + requestTime);

        partMap = partMap == null ? new HashMap<>() : partMap;
        partMap.put("appKey", appKey);
        partMap.put("sign", sign);
        partMap.put("requestTime", String.valueOf(requestTime));
    }

    private String getHealthCode(String healthStr) {
        JSONObject j = JSONObject.parseObject(healthStr);
        JSONObject j2 = JSONObject.parseObject(j.getString("datas"));
        JSONArray jsonArray = j2.getJSONArray("data");
        if (jsonArray.size() > 0) {
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            if (jsonObject.containsKey("mzt")) {
                return jsonObject.getString("mzt");
            }
        }
        return null;
    }

    @Override
    public void getHealthCodeListExcel(HttpServletResponse response) {
        IOUtil.downloadFile(HEALTH_EXCEL_FILE, String.format("templates/%s", HEALTH_EXCEL_FILE), response);
    }
}
