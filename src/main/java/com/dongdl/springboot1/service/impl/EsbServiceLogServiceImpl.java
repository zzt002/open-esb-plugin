package com.dongdl.springboot1.service.impl;

import com.dongdl.springboot1.bean.EsbServiceLogBean;
import com.dongdl.springboot1.common.Constants;
import com.dongdl.springboot1.common.Enums.EsbServiceLogOpTypeEnum;
import com.dongdl.springboot1.common.MessageException;
import com.dongdl.springboot1.dao.idao.IEsbServiceLogDao;
import com.dongdl.springboot1.service.IEsbServiceLogService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author <a mailto:dongdongliang13@hotmail.com>zzt002</a>
 * @date 2021/1/20 21:15 UTC+8
 * @description
 **/
@Service
public class EsbServiceLogServiceImpl implements IEsbServiceLogService {

    @Autowired
    private IEsbServiceLogDao esbServiceLogDao;

    @Override
    @Transactional(transactionManager = "data1TransactionManager")
    public Integer saveBath(String publishId) {
        List<EsbServiceLogBean> list = Lists.newArrayList();

        EsbServiceLogBean esbServiceLogBean = EsbServiceLogBean.builder().build();
        esbServiceLogBean.initParam(publishId);

        // 提交审核
        EsbServiceLogBean esbServiceLogBean1 = esbServiceLogBean.clone();
        esbServiceLogBean1.resetParam(EsbServiceLogOpTypeEnum.VERIFYING.getId());
        list.add(esbServiceLogBean1);

        // 审核通过
        EsbServiceLogBean esbServiceLogBean2 = esbServiceLogBean.clone();
        esbServiceLogBean2.resetParam(EsbServiceLogOpTypeEnum.VERIFIED.getId());
        list.add(esbServiceLogBean2);

        // 已发布
        EsbServiceLogBean esbServiceLogBean3 = esbServiceLogBean.clone();
        esbServiceLogBean3.resetParam(EsbServiceLogOpTypeEnum.PUBLISH.getId());
        list.add(esbServiceLogBean3);

        // 修改
        EsbServiceLogBean esbServiceLogBean4 = esbServiceLogBean.clone();
        esbServiceLogBean4.resetParam(EsbServiceLogOpTypeEnum.MODIFIED.getId());
        list.add(esbServiceLogBean4);

        int result = esbServiceLogDao.saveBath(list);
        if (result != Constants.INT_FOUR) {
            throw new MessageException("Save path Fail: API文档");
        }

        return result;
    }



}
