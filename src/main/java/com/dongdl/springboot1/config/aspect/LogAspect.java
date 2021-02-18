package com.dongdl.springboot1.config.aspect;

import com.dongdl.springboot1.bean.SystemLogBean;
import com.dongdl.springboot1.common.Constants;
import com.dongdl.springboot1.config.annotation.SystemLog;
import com.dongdl.springboot1.service.ISystemLogService;
import com.dongdl.springboot1.util.IPUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/16 15:52 UTC+8
 * @description
 **/
@Component
@Aspect
public class LogAspect {

    @Autowired
    private ISystemLogService systemLogService;
    @Autowired
    private HttpServletRequest request;

    @Pointcut("@annotation(com.dongdl.springboot1.config.annotation.SystemLog)")
    private void logPointcut() {

    }

    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void afterMethod(JoinPoint joinPoint, Exception e) {
       handleLog(joinPoint, e);
    }

    @AfterReturning(pointcut = "logPointcut()")
    public void afterMethod(JoinPoint joinPoint) {
        handleLog(joinPoint, null);
    }

    private void handleLog(JoinPoint joinPoint, Exception e) {
        //获取注解
        SystemLog logAnnotation = null;
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            logAnnotation = method.getAnnotation(SystemLog.class);
        }

        SystemLogBean systemLog = new SystemLogBean();
        //标题
        systemLog.setTitle(logAnnotation.title());
        //操作行为
        systemLog.setAction(logAnnotation.action().getName());
        //操作状态
        if (e == null) {
            systemLog.setOperationStatus(Constants.INT_ONE);

        } else {
            systemLog.setOperationStatus(Constants.INT_ZERO);
            systemLog.setMessage(e.getMessage());
        }
        //url
        systemLog.setUrl(request.getRequestURI());
        //ip
        String ip = IPUtils.getIp(request);
        systemLog.setIp(ip);
        //参数
        Map<String, String[]> paramMap = request.getParameterMap();
        StringBuffer param = new StringBuffer();
        Set<String> sets = paramMap.keySet();
        boolean flag = false;
        for (String s : sets) {
            String[] values = paramMap.get(s);
            for (String v : values) {
                if (flag) {
                    param.append("&");
                } else {
                    flag = true;
                }
                if ("password".equals(s.toLowerCase())) {
                    v = "******";
                }
                param.append(s).append("=").append(v);
            }
        }
        systemLog.setParam("".equals(param.toString()) ? null : param.toString());
        //save the log
        systemLogService.saveOneByMq(systemLog);
    }


}
