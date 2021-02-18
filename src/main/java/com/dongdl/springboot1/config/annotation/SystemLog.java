package com.dongdl.springboot1.config.annotation;

import com.dongdl.springboot1.common.Enums.LogActionEnum;

import java.lang.annotation.*;
/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/16 17:39 UTC+8
 * @description
 **/
@Target({ElementType.METHOD})//定义注解的作用目标**作用范围类
@Retention(RetentionPolicy.RUNTIME)//注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Documented//说明该注解将被包含在javadoc中
public @interface SystemLog {
    String title() default "";
    LogActionEnum action();
}
