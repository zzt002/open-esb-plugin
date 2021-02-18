package com.dongdl.springboot1.config;

import com.dongdl.springboot1.config.jwt.AuthenticationInterceptor;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/8/5 11:45 UTC+8
 * @description
 */
@Configuration
@Profile("prod")
public class InterceptorConfig implements WebMvcConfigurer {

    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePathList = Lists.newArrayList();
        // swagger 页面
        excludePathList.add("/swagger-resources/**");
        excludePathList.add("/webjars/**");
        excludePathList.add("/v2/**");
        excludePathList.add("/swagger-ui.html/**");
        // 登录
        excludePathList.add("/login");
        excludePathList.add("/");
        // error
        excludePathList.add("/error");
        // 静态
        excludePathList.add("/static/**");

        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(excludePathList);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // swagger资源
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        // 静态资源
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("/static/index.html");
//        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
//    }
}
