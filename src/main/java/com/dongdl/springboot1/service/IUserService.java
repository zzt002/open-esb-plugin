package com.dongdl.springboot1.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dongdl.springboot1.bean.UserBean;

import java.util.Map;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/8/5 15:21 UTC+8
 * @description
 */
public interface IUserService extends IService<UserBean> {
    /**
     * 登录
     * @param userBean
     * @return
     */
    Map login(UserBean userBean);
}
