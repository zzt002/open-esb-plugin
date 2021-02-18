package com.dongdl.springboot1.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dongdl.springboot1.bean.UserBean;
import com.dongdl.springboot1.common.MessageException;
import com.dongdl.springboot1.config.jwt.JwtUtil;
import com.dongdl.springboot1.dao.localdao.IUserDao;
import com.dongdl.springboot1.service.IUserService;
import com.dongdl.springboot1.util.StringUtil;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/8/5 15:23 UTC+8
 * @description
 */
@Service
public class UserServiceImpl extends ServiceImpl<IUserDao, UserBean> implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Override
    public Map login(UserBean userBean) {
        userBean.valid();

        UserBean resultBean = getOneByAccount(userBean);
        if (null == resultBean) {
            throw new MessageException("用户不存在");
        }

        String inputMd5 = userBean.getPassword();
        String password = resultBean.getPassword();
        if (!password.equals(inputMd5)) {
            throw new MessageException("密码错误");
        }

        Map<String, Object> map = Maps.newHashMap();
        String name = resultBean.getName();
        if (StringUtil.isEmpty(name)) {
            name = "";
        }
        map.put("name", name);
        List<Map> menuList = userDao.listMenu(1);
        map.put("role", menuList);

        Map<String, Object> tokenMap = Maps.newHashMap();
        String token = JwtUtil.createToken(map);
        tokenMap.put("token", token);
        tokenMap.put("name", name);
        tokenMap.put("exp", JwtUtil.getExpiresAt(token).getTime());
        return tokenMap;
    }

    @Override
    public boolean save(UserBean entity) {
        entity.valid();
        entity.init();
        entity.encodePwd();

        if (getOneByAccount(entity) != null) {
             throw new MessageException("用户名已注册");
        }
        return super.save(entity);
    }

    private UserBean getOneByAccount(UserBean userBean) {
        QueryWrapper<UserBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserBean::getAccount, userBean.getAccount());
        return userDao.selectOne(queryWrapper);
    }
}
