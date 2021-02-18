package com.dongdl.springboot1.service.impl;

import com.alibaba.druid.util.DruidDataSourceUtils;
import com.dongdl.springboot1.bean.TestBean;
import com.dongdl.springboot1.dao.idao.ITestOracleDao;
import com.dongdl.springboot1.dao.localdao.ITestMysqlDao;
import com.dongdl.springboot1.service.ITestMysqlService;
import com.dongdl.springboot1.service.ITestOracleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/7 22:55 UTC+8
 * @description
 */
@Service
public class TestOracleServiceImpl implements ITestOracleService {

    @Autowired
    private ITestOracleDao testOracleDao;
    @Autowired
    ITestMysqlService testMysqlService;

    @Override
    public ArrayList<TestBean> list() {
        return testOracleDao.list();
    }

    @Override
    @Transactional(transactionManager = "data1TransactionManager",propagation = Propagation.NESTED)
    public Object saveOne(TestBean testBean) {
        testMysqlService.saveOne(testBean);
        int o = testOracleDao.saveOne(testBean);
        int i = 1/0;

        return o;
    }

    @Override
    public ArrayList<TestBean> listAll() {
        return testOracleDao.listAll();
    }

    @Override
    public TestBean getOneByIp(String ip) {
        return testOracleDao.getOneByIp(ip);
    }

}