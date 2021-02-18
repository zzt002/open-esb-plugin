package com.dongdl.springboot1.controller;

import com.dongdl.springboot1.bean.UserBean;
import com.dongdl.springboot1.common.Enums.LogActionEnum;
import com.dongdl.springboot1.common.ResultData;
import com.dongdl.springboot1.config.annotation.SystemLog;
import com.dongdl.springboot1.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/8/5 11:57 UTC+8
 * @description
 */
@RequestMapping
@RestController
@ApiIgnore
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    @SystemLog(title = "用户", action = LogActionEnum.LOGIN)
    public ResultData login(UserBean userBean) {
        return new ResultData(55, "登录成功", userService.login(userBean));
    }

    @PostMapping("/register")
    @SystemLog(title = "用户", action = LogActionEnum.UREGISTER)
    public ResultData save(UserBean userBean) {
        return new ResultData(userService.save(userBean));
    }
}
