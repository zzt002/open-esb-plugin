package com.dongdl.springboot1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableTransactionManagement
@EnableScheduling
//@EnableSwagger2Doc
public class Springboot1Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Springboot1Application.class, args);
    }

    /**
     * tomcat启动 继承SpringBootServletInitializer类  重写configure方法
     * @param builder
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Springboot1Application.class);
    }
}
