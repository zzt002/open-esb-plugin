package com.dongdl.springboot1.service.impl;

import com.dongdl.springboot1.bean.TestBean;
import com.dongdl.springboot1.service.ITestMysqlService;
import com.dongdl.springboot1.service.ITestOracleService;
import com.dongdl.springboot1.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestServiceImpl implements ITestService {

    @Autowired
    ITestOracleService testOracleService;
    @Autowired
    ITestMysqlService testMysqlService;

    @Override
    public Object test() {
        TestBean bean = new TestBean();
        bean.setIp("10.10.10.10");
        bean.setContent("999999999");

        testOracleService.saveOne(bean);
//        testMysqlService.saveOne(bean);
        int i = 1/0;
        return bean;
    }
}
