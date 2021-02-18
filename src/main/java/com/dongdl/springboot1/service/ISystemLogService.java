package com.dongdl.springboot1.service;

import com.dongdl.springboot1.bean.SystemLogBean;

import java.util.List;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/18 14:47 UTC+8
 * @description 
 **/
public interface ISystemLogService {

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/18 14:47 UTC+8
     * @description 
     * @param id
     * @return
     */
    SystemLogBean findOneById(Integer id);

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/18 14:47 UTC+8
     * @description
     * @param logBean
     * @return
     */
    List<SystemLogBean> list(SystemLogBean logBean);

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/18 14:53 UTC+8
     * @description
     * @param systemLog
     */
    Object saveOne(SystemLogBean systemLog);

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/18 18:21 UTC+8
     * @description 
     * @param systemLog
     */
    void saveOneByMq(SystemLogBean systemLog);
}
