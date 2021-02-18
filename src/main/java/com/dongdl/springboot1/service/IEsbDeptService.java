package com.dongdl.springboot1.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dongdl.springboot1.bean.EsbDeptBean;

public interface IEsbDeptService extends IService<EsbDeptBean> {

    /**
     * 获取部门信息
     * by 部门名称
     * @return
     */
    EsbDeptBean getOneByName(String name);
}
