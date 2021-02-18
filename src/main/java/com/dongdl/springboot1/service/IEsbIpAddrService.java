package com.dongdl.springboot1.service;

import com.dongdl.springboot1.bean.EsbIpAddrBean;

import java.util.List;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/11 14:11 UTC+8
 * @description
 **/
public interface IEsbIpAddrService {

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/11 14:11 UTC+8
     * @description
     * @param myApp
     * @return
     */
    List<EsbIpAddrBean> list(String myApp);
}
