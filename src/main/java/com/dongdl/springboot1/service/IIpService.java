package com.dongdl.springboot1.service;

import com.dongdl.springboot1.bean.IpBean;

import java.util.List;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/26 14:15 UTC+8
 * @description 
 **/
public interface IIpService {

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/26 14:15 UTC+8
     * @description 
     * @param ip
     * @return
     */
    IpBean getOneByIp(String ip);

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/26 14:22 UTC+8
     * @description 
     * @param ip
     * @param status
     * @return
     */
    Integer     confIp(String ip, Integer status);

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/26 14:51 UTC+8
     * @description
     * @return
     */
    List<IpBean> list();
}
