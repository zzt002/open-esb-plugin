package com.dongdl.springboot1.config.jwt;

import com.dongdl.springboot1.common.MessageException;
import com.dongdl.springboot1.util.StringUtil;
import org.apache.commons.httpclient.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/8/5 10:56 UTC+8
 * @description token 拦截器
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (StringUtil.isEmpty(token) || "null".equals(token) || "undefined".equals(token)){
            throw new MessageException(HttpStatus.SC_UNAUTHORIZED, "未登录");
        }
        if(!JwtUtil.verifyToken(token)) {
            throw new MessageException(HttpStatus.SC_UNAUTHORIZED, "登录失效");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
