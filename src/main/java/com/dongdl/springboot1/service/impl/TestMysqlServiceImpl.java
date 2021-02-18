package com.dongdl.springboot1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dongdl.springboot1.bean.BaseBean;
import com.dongdl.springboot1.bean.TestBean;
import com.dongdl.springboot1.common.Constants;
import com.dongdl.springboot1.common.MessageException;
import com.dongdl.springboot1.dao.localdao.ITestMysqlDao;
import com.dongdl.springboot1.service.ITestMysqlService;
import com.dongdl.springboot1.util.DateUtil;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.sql.DataSource;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/7 22:55 UTC+8
 * @description
 */
@Service
@Slf4j
public class TestMysqlServiceImpl implements ITestMysqlService {

    @Autowired
    private ITestMysqlDao testMysqlDao;

    @Override
    public ArrayList<TestBean> list() {
        return testMysqlDao.list();
    }

    @Override
    @Transactional(transactionManager = "data2TransactionManager",propagation = Propagation.NESTED)
    public Object saveOne(TestBean testBean) {
        Integer o = testMysqlDao.insert(testBean);
        return o;
    }

    @Override
    public List<TestBean> listAll() {
        QueryWrapper queryWrapper = new QueryWrapper();

        List<TestBean> list = testMysqlDao.selectList(queryWrapper);
//        List<TestBean> list = testMysqlDao.listAll();
        return list;
//        return testMysqlDao.listAll();
    }

    @Override
    public TestBean getOneByIp(String ip) {
        return testMysqlDao.getOneByIp(ip);
    }

    @Override
    public Integer savePath() {
        List<TestBean> list = Lists.newArrayList();
        TestBean testBean1 = TestBean.builder()
                .testDate(DateUtil.getCurrentDate())
                .testParam("1")
                .build();
        TestBean testBean2 = TestBean.builder()
                .testDate(DateUtil.getCurrentDate())
                .testParam("2")
                .build();
        TestBean testBean3 = TestBean.builder()
                .testDate(DateUtil.getCurrentDate())
                .testParam("3")
                .build();

        list.add(testBean1);
        list.add(testBean2);
        list.add(testBean3);

        int result = testMysqlDao.saveBath(list);
        log.info("批量存储{}条", result);

        return 1;
    }

}