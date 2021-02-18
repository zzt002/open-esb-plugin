package com.dongdl.springboot1.service;

import com.dongdl.springboot1.bean.TestBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/7 22:55 UTC+8
 * @description
 */
public interface ITestMysqlService {

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/7 22:57 UTC+8
     * @description
     */
    ArrayList<TestBean> list();

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/7 23:02 UTC+8
     * @description
     */
    Object saveOne(TestBean testBean);

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/7 23:45 UTC+8
     * @description
     */
    List<TestBean> listAll();

    /**
     * @description
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/8 2:47 UTC+8
     * @param ip
     * @return
     */
    TestBean getOneByIp(String ip);

    Integer savePath();
}
