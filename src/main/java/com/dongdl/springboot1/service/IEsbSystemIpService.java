package com.dongdl.springboot1.service;

import com.dongdl.springboot1.bean.EsbSystemIpBean;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/7 22:43 UTC+8
 * @description
 */
public interface IEsbSystemIpService {
    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/7 22:58 UTC+8
     * @description 
     */
    EsbSystemIpBean getOneByIp(String ip);
}
