package com.dongdl.springboot1.service.impl;

import com.dongdl.springboot1.bean.EsbSourceInfoBean;
import com.dongdl.springboot1.dao.pdao.IEsbSourceInfoDao;
import com.dongdl.springboot1.service.IEsbSourceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/11 14:03 UTC+8
 * @description
 **/
@Service
public class EsbSourceInfoServiceImpl implements IEsbSourceInfoService {

    @Autowired
    private IEsbSourceInfoDao esbSourceInfoDao;

    @Override
    public EsbSourceInfoBean getOneById(String id) {
        return esbSourceInfoDao.getOneById(id);
    }
}
