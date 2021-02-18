package com.dongdl.springboot1.service.impl;

import com.dongdl.springboot1.bean.EsbDeptBean;
import com.dongdl.springboot1.bean.EsbInboundMainBean;
import com.dongdl.springboot1.bean.EsbServicePublishBean;
import com.dongdl.springboot1.common.MessageException;
import com.dongdl.springboot1.dao.idao.IEsbServicePublishDao;
import com.dongdl.springboot1.service.IEsbDeptService;
import com.dongdl.springboot1.service.IEsbInboundMainService;
import com.dongdl.springboot1.service.IEsbServicePublishService;
import com.dongdl.springboot1.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a mailto:dongdongliang13@hotmail.com>zzt002</a>
 * @date 2021/1/20 21:15 UTC+8
 * @description
 **/
@Service
public class EsbServicePublishServiceImpl implements IEsbServicePublishService {

    @Autowired
    private IEsbServicePublishDao esbServicePublishDao;
    @Autowired
    private IEsbInboundMainService esbInboundMainService;
    @Autowired
    private IEsbDeptService esbDeptService;

    @Override
    @Transactional(transactionManager = "data1TransactionManager")
    public void saveOneByServCode(String servCode) {
        // 服务信息校验
        EsbInboundMainBean esbInboundMainBean = esbInboundMainService.getOneByServcode(servCode);
        if (null == esbInboundMainBean) {
            throw new MessageException(String.format("没有找到相关服务信息【servcode:[%s]】", servCode));
        }
        EsbServicePublishBean esbServicePublishBean = EsbServicePublishBean.builder()
                .servCode(servCode)
                .servName(esbInboundMainBean.getServiceName())
                .build();
        // 数据保存
        saveOne(esbServicePublishBean);
    }

    private String saveOne(EsbServicePublishBean esbServicePublishBean) {
        // 数据校验
        initAndValid(esbServicePublishBean);

        if (esbServicePublishDao.saveOne(esbServicePublishBean) <= 0) {
            throw new MessageException("Save Fail: API文档发布");
        }
        return null;
    }

    /**
     * 参数初始并校验
     */
    private void initAndValid(EsbServicePublishBean esbServicePublishBean) {
        // 参数初始
        esbServicePublishBean.initParam();

        // todo: 省市接口判断
        if (StringUtil.isPro(esbServicePublishBean.getServCode())) {
            // 省接口数据初始
            esbServicePublishBean.initPro();
            // 省数据校验
            validForPro(esbServicePublishBean);
        } else {
            // 市接口数据初始
            esbServicePublishBean.initCity();
            // 市数据校验
            validForCity(esbServicePublishBean);
        }

        // 数据校验
        dataValid(esbServicePublishBean);

        // 其余参数补充
        esbServicePublishBean.restParam();
    }

    /**
     * 市接口参数校验
     */
    private void validForCity(EsbServicePublishBean esbServicePublishBean) {
        // 市接口初始
        esbServicePublishBean.initCity();

    }

    /**
     * 省接口参数校验
     */
    private void validForPro(EsbServicePublishBean esbServicePublishBean) {
        // 省接口初始
        esbServicePublishBean.initPro();

        if (StringUtil.isEmpty(esbServicePublishBean.getFirstApiCode())) {
            throw new MessageException("省接口请填写原始编码");
        }
    }

    /**
     * 其他数据库数据校验
     *
     * @param esbServicePublishBean
     */
    private void dataValid(EsbServicePublishBean esbServicePublishBean) {
        String provideDeptName = esbServicePublishBean.getProvideDept();

        // 提供部门校验
        EsbDeptBean esbDeptBean = esbDeptService.getOneByName(provideDeptName);
        if (null == esbDeptBean) {
            throw new MessageException(String.format("未找到部门: %s", provideDeptName));
        } else {
            esbServicePublishBean.setProvideDeptCode(esbDeptBean.getCode());
        }
    }

}
