package com.dongdl.springboot1.config;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@EnableSwagger2
@Configuration
public class Swagger2Config {

    private final static String TOKEN = "xxxxxxx";

    @Value("${swagger.enable}")
    Boolean swaggerEnable;

    @Bean
    public Docket createRestApi() {
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        parameterBuilder.name("token")
                .defaultValue(TOKEN)
//                .description("default order by DESC, any value to ASC")
                .parameterType("header") //.parameterType("header/query")
                .modelRef(new ModelRef("string"))
                .hidden(false)// hidden属性没作用----官方bug？
        ;
        List<Parameter> pars = Lists.newArrayList();
        pars.add(parameterBuilder.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerEnable)
                .apiInfo(apiInfo())
                .globalOperationParameters(pars)  // 使用自定配置的参数
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.dongdl.springboot1.controller"))
                .paths(PathSelectors.any())
                .build();
//        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)).build();
    }
    //构建 api文档的详细信息函数,注意这里的注解引用的是哪个
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("HSB VPN2 后台便捷运维RESTful接口文档")
                //创建人
                .contact(new Contact("dongdl", "", "dongdongliang13@hotmail.com"))
                //版本号
                .version("2.13.02")
                //描述
                .description("last update time 2020-12-11")
                .build();
    }
}
