package com.dongdl.springboot1.service.impl;

import com.dongdl.springboot1.bean.EsbInboundMainBean;
import com.dongdl.springboot1.bean.EsbServiceConsumerBean;
import com.dongdl.springboot1.bean.EsbSystemIpBean;
import com.dongdl.springboot1.bean.EsbSystemServiceBean;
import com.dongdl.springboot1.common.MessageException;
import com.dongdl.springboot1.dao.idao.IEsbInboundMainDao;
import com.dongdl.springboot1.dao.idao.IEsbServiceConsumerDao;
import com.dongdl.springboot1.dao.idao.IEsbSystemIpDao;
import com.dongdl.springboot1.dao.idao.IEsbSystemServiceDao;
import com.dongdl.springboot1.service.IEsbServiceConsumerService;
import com.dongdl.springboot1.service.IEsbSystemServiceService;
import com.dongdl.springboot1.service.IIpService;
import com.dongdl.springboot1.util.IOUtil;
import com.dongdl.springboot1.util.IPUtils;
import com.dongdl.springboot1.util.OfficeUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/8 1:35 UTC+8
 * @description
 */
@Service
public class EsbServiceConsumerServiceImpl implements IEsbServiceConsumerService {

    @Autowired
    private IEsbServiceConsumerDao esbServiceConsumerDao;
    @Autowired
    private IEsbSystemIpDao esbSystemIpDao;
    @Autowired
    private IEsbInboundMainDao esbInboundMainDao;
    @Autowired
    private IEsbSystemServiceDao esbSystemServiceDao;
    @Autowired
    private IEsbSystemServiceService esbSystemServiceService;
    @Autowired
    private ThirdPartyServiceImpl thirdPartyService;
    @Autowired
    private IIpService ipService;

    public static final String LAN_NAME = "本地局域网";

    @Override
    public Object saveOne(EsbServiceConsumerBean esbServiceConsumerBean) {
        esbServiceConsumerBean.setStatus("1");
        esbServiceConsumerDao.saveOne(esbServiceConsumerBean);
        return esbServiceConsumerBean.getId();
    }

    @Override
    public Object updateOneById(String id, Integer status) {
        status = status == null ? 1 : status;
        return esbServiceConsumerDao.updateOneById(id, String.valueOf(status));
    }

    @Override
    public EsbServiceConsumerBean getOneByIpIdAndServcode(String ipId, String servcode) {
        return esbServiceConsumerDao.getOneByIpIdAndServcode(ipId, servcode);
    }

    @Override
    public Map openIps(String ipStr, String serviceStr, Integer status) {
        if (ipStr == null || serviceStr == null) {
            throw new MessageException("入参不能为空");
        }
        String[] ips = ipStr.split(",");
        String[] services = serviceStr.split(",");

        return open(ips, services, status);
    }

    /**
     * @param ips
     * @param services
     * @param status
     * @return
     * @author <a href="mailto:dongdongliang13@hotmail.com">zzt002</a>
     * @date 2020/6/29 16:21 GMT+8
     * @description
     */
    @Transactional(transactionManager = "data1TransactionManager")
    public Map open(String[] ips, String[] services, Integer status) {
        StringBuffer ipErrorMessage = new StringBuffer();
        StringBuilder otherIpMessage = new StringBuilder();
        StringBuilder serviceErrorMessage = new StringBuilder();
        ArrayList<EsbInboundMainBean> esbInboundMainBeanList = Lists.newArrayList();
        ArrayList<EsbSystemIpBean> esbSystemIpBeanList = Lists.newArrayList();

        // 数据格式校验
        for (String ip : ips) {
            if (!IPUtils.isIP4(ip)) {
                ipErrorMessage.append(",").append(ip);
            } else if ( !IPUtils.LocalIp4.internalIp(ip) && ipService.getOneByIp(ip) == null && !ip
                    .matches("^59\\..*")) {
                otherIpMessage.append(",").append(ip);
            }
        }
        if (ipErrorMessage.length() > 0) {
            throw new MessageException("非法IP4地址：" + ipErrorMessage.substring(1));
        }
        if (otherIpMessage.length() > 0) {
            throw new MessageException("互联网IP：" + otherIpMessage.substring(1));
        }

        ipErrorMessage = new StringBuffer();
        for (String ip : ips) {
            EsbSystemIpBean esbSystemIpBean = checkIp(ip);
            // 数据库ip验证
            if (esbSystemIpBean == null) {
                ipErrorMessage.append(",").append(ip);
            }
            esbSystemIpBeanList.add(esbSystemIpBean);
        }
        if (ipErrorMessage.length() > 0) {
            throw new MessageException("未添加消费者IP：" + ipErrorMessage.substring(1));
        }

        checkService(services, serviceErrorMessage, esbInboundMainBeanList);

        for (EsbInboundMainBean serviceEntity : esbInboundMainBeanList) {
            for (EsbSystemIpBean ipEntity : esbSystemIpBeanList) {
                // 部门权限
                openDep(ipEntity, serviceEntity);
                // ip权限
                openIp(ipEntity, serviceEntity, status);
            }
        }

        // ESB重载接口功能
        reload(esbInboundMainBeanList);

        HashMap<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("TITLE", status == 1 ? "开启" : "关闭");
        resultMap.put("IP", Arrays.toString(ips));
        resultMap.put("SERVICE", Arrays.toString(services));
        resultMap.put("MESSAGE", "重载队列已放入MQ队列中，成功与否请关注相关日志");

        return resultMap;
    }

    public void checkService(String[] services, StringBuilder serviceErrorMessage, ArrayList<EsbInboundMainBean>
            esbInboundMainBeanList) {
        for (String service : services) {
            // 数据库服务验证
            EsbInboundMainBean esbInboundMainBean = checkServiceName(service);
            if (esbInboundMainBean == null) {
                serviceErrorMessage.append(",").append(service);
                continue;
            }
            esbInboundMainBeanList.add(esbInboundMainBean);
        }
        if (serviceErrorMessage.length() > 0) {
            throw new MessageException("不存在的服务：" + serviceErrorMessage.substring(1));
        }
    }

    @Override
    public List<EsbServiceConsumerBean> listHistoryIp(String ip) {
        EsbSystemIpBean ipBean = esbSystemIpDao.getOneByIp(ip);
        if (ipBean != null) {
            List<EsbServiceConsumerBean> list = esbServiceConsumerDao.listHistoryIpByIpId(ipBean.getId());
            return list;
        }
        return null;
    }

    /**
     * @param esbInboundMainBeanList
     * @author <a href="mailto:dongdongliang13@hotmail.com">zzt002</a>
     * @date 2020/6/29 10:08 GMT+8
     * @description
     */
    private void reload(ArrayList<EsbInboundMainBean> esbInboundMainBeanList) {
        for (EsbInboundMainBean entity : esbInboundMainBeanList) {
            thirdPartyService.reloadEsb(entity.getEsbServiceId(), entity.getServiceCode(), null);
        }
    }

    /**
     * @param ipEntity
     * @param serviceEntity
     * @author <a href="mailto:dongdongliang13@hotmail.com">zzt002</a>
     * @date 2020/6/29 10:12 GMT+8
     * @description 开启部门
     */
    private void openDep(EsbSystemIpBean ipEntity, EsbInboundMainBean serviceEntity) {
        EsbSystemServiceBean esbSystemServiceBean = esbSystemServiceDao.getOneBySystemIdAndServcode(ipEntity.getSystemId(), serviceEntity
                .getServiceCode());
        if (esbSystemServiceBean == null) {
            EsbSystemServiceBean newEsbSystemServiceBean = new EsbSystemServiceBean();
            newEsbSystemServiceBean.setSystemId(ipEntity.getSystemId());
            newEsbSystemServiceBean.setServcode(serviceEntity.getServiceCode());
            esbSystemServiceService.saveOne(newEsbSystemServiceBean);
        }
    }

    /**
     * @param ipEntity
     * @param serviceEntity
     * @param status
     * @author <a href="mailto:dongdongliang13@hotmail.com">zzt002</a>
     * @date 2020/6/29 10:15 GMT+8
     * @description 开启ip
     */
    private void openIp(EsbSystemIpBean ipEntity, EsbInboundMainBean serviceEntity, Integer status) {
        EsbServiceConsumerBean esbServiceConsumerBean = esbServiceConsumerDao.getOneByIpIdAndServcode(ipEntity.getId(), serviceEntity.getServiceCode());
        if (esbServiceConsumerBean == null) {
            esbServiceConsumerBean = new EsbServiceConsumerBean();
            esbServiceConsumerBean.setIpId(ipEntity.getId());
            esbServiceConsumerBean.setServcode(serviceEntity.getServiceCode());
            this.saveOne(esbServiceConsumerBean);
        } else {
            status = status == 1 ? status : 0;
            esbServiceConsumerDao.updateOneById(esbServiceConsumerBean.getId(), String.valueOf(status));
        }
    }


    /**
     * @param service
     * @return
     * @author <a href="mailto:dongdongliang13@hotmail.com">zzt002</a>
     * @date 2020/6/29 14:46 GMT+8
     * @description 检测数据库服务名
     */
    public EsbInboundMainBean checkServiceName(String service) {
        EsbInboundMainBean esbInboundMainBean = null;
        if (service.matches("^33\\.1111\\..*\\.SynReq$")) {
            esbInboundMainBean = esbInboundMainDao.getOneByServcode(service);
        } else if (!service.isEmpty()) {
            esbInboundMainBean = esbInboundMainDao.getOneByServname(service);
        }
        return esbInboundMainBean;
    }

    /**
     * @param ip
     * @return
     * @author <a href="mailto:dongdongliang13@hotmail.com">zzt002</a>
     * @date 2020/6/29 15:43 GMT+8
     * @description
     */
    private EsbSystemIpBean checkIp(String ip) {
        return esbSystemIpDao.getOneByIp(ip);
    }

    @Override
    public Map openIpsByExcel(MultipartFile file, Integer status) {

        StringBuilder errorString = new StringBuilder();
        StringBuffer serExcelErrorMessage = new StringBuffer();
        StringBuffer ipExcelErrorMessage = new StringBuffer();

        // 读取服务excel
        Map<Integer, List> serMap = null;
        List<String> services = Lists.newArrayList();
        try {
            serMap = OfficeUtil.readExcel2Map(file, 1);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MessageException("权限excel读取失败:" + e.getMessage());
        }
        if (!serMap.isEmpty()) {
            serMap.forEach((key, value) -> {
                String service = String.valueOf(value.get(0)).trim();
                if (service.isEmpty() || "null".equals(service)) {
                    serExcelErrorMessage.append(",").append(key);
                } else {
                    services.add(service);
                }
            });
        }

        // 读取IP excel
        Map<Integer, List> ipMap = null;
        List<String> ips = Lists.newArrayList();
        try {
            ipMap = OfficeUtil.readExcel2Map(file, 1, 2);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MessageException("IP excel读取失败:" + e.getMessage());
        }
        if (!ipMap.isEmpty()) {
            ipMap.forEach((key, value) -> {
                String ip = String.valueOf(value.get(0)).trim();
                System.out.println(ip);
                if (ip.isEmpty() || "null".equals(ip) || !IPUtils.isIP4(ip)) {
                    ipExcelErrorMessage.append(",").append(key);
                } else {
                    ips.add(ip);
                }
            });
        }

        // excel数据错误
        if (serExcelErrorMessage.length() > 0) {
            errorString.append("服务错误:第").append(serExcelErrorMessage.substring(1)).append("行");
        }
        if (ipExcelErrorMessage.length() > 0) {
            errorString.append(" ip错误:第").append(ipExcelErrorMessage.substring(1)).append("行");
        }
        if (errorString.length() > 0) {
            throw new MessageException(errorString.toString());
        }

        return open(ips.toArray(new String[]{}), services.toArray(new String[]{}), status);
    }

    @Override
    public void getExcel(HttpServletResponse response) {
        String excelName = "open_ip.xls";
        IOUtil.downloadFile(excelName, String.format("templates/%s", excelName), response);
    }

    @Override
    public int updateServcode(String oldServcode, String newServcode) {
        return esbServiceConsumerDao.updateServcode(oldServcode, newServcode);
    }
}
