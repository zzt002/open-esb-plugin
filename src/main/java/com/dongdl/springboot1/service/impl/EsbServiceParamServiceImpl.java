package com.dongdl.springboot1.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dongdl.springboot1.bean.EsbServiceParamBean;
import com.dongdl.springboot1.common.MessageException;
import com.dongdl.springboot1.dao.idao.IEsbServiceParamDao;
import com.dongdl.springboot1.service.IEsbServiceParamService;
import com.dongdl.springboot1.util.StringUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author <a mailto:dongdongliang13@hotmail.com>zzt002</a>
 * @date 2021/1/20 21:15 UTC+8
 * @description
 **/
@Service
public class EsbServiceParamServiceImpl implements IEsbServiceParamService {

    @Autowired
    private IEsbServiceParamDao esbServiceParamDao;

    @Override
    @Transactional(transactionManager = "data1TransactionManager")
    public Integer saveBath(String publishId, String inJson, String outJson) {
        List<EsbServiceParamBean> list = Lists.newArrayList();

        EsbServiceParamBean esbServiceParamBean = EsbServiceParamBean.builder().build();
        esbServiceParamBean.initParam(publishId);

        // 入参
        Map<String, String> inMap = null;
        // 出参
        Map<String, String> outMap = null;

        try {
            inMap = getMapByJson(inJson);
            outMap = getMapByJson(outJson);
        } catch (Exception e) {
            throw new MessageException("出入参JSON转换异常：" + e.getMessage());
        }

        dealMapParam(list, esbServiceParamBean, inMap, 0);
        dealMapParam(list, esbServiceParamBean, outMap, 1);

        int result = esbServiceParamDao.saveBath(list);
        if (result != list.size()) {
            throw new MessageException("Save path Fail: API基本信息");
        }

        return result;
    }

    private Map getMapByJson(String json) {
        return StringUtil.isEmpty(json) ? null : JSONObject.parseObject(json, Map.class);
    }

    /**
     *
     * @param list
     * @param bean 需要拷贝的类
     * @param map 传入的map
     * @param paramMode
     * @return
     */
    private void dealMapParam(List<EsbServiceParamBean> list, EsbServiceParamBean bean, Map<String, String> map, Integer paramMode) {
        if (map != null && map.size() > 0) {
            map.forEach((key, value) -> {
                EsbServiceParamBean esbServiceParamBeanTemp = bean.clone();
                esbServiceParamBeanTemp.setParamMode(paramMode);
                list.add(esbServiceParamBeanTemp);
            });
        }
    }

}
