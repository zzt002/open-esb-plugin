package com.dongdl.springboot1.common.Enums;

/**
 * @author <a mailto:dongdl@citycloud.com.cn>dongdl</a>
 * @date 2021/1/27 11:45 UTC+8
 * @description API文档服务访问地址枚举
 **/
public enum EsbServicePublishApiAddressEnum {
    HTTP("https://sql.hz.gov.cn/ESBWeb/servlets/", "http接口"),
    WEB_SERVICE("https://sql.hz.gov.cn/ESBWeb/services/", "web_service接口")
    ;

    EsbServicePublishApiAddressEnum(String url, String description) {
        this.url = url;
        this.description = description;
    }

    private String url;
    private String description;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
