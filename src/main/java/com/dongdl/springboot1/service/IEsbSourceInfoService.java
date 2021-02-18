package com.dongdl.springboot1.service;

import com.dongdl.springboot1.bean.EsbSourceInfoBean;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/11 14:02 UTC+8
 * @description
 **/
public interface IEsbSourceInfoService {

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/11 14:02 UTC+8
     * @description
     * @param id
     * @return
     */
    EsbSourceInfoBean getOneById(String id);
}
