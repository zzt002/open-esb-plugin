package com.dongdl.springboot1.common.Enums;

/**
 * @author <a mailto:dongdl@citycloud.com.cn>dongdl</a>
 * @date 2021/1/27 11:45 UTC+8
 * @description API文档请求示例枚举
 **/
public enum EsbServicePublishReqExampleEnum {
    CITY("String request = RSAUtils.encryptByPrivateKey(入参的json格式, 私钥);\nString sign = RSAUtils.sign(request, 私钥);\n" +
            "parts = new Part[]{\n\tnew StringPart(\"request\", request, \"UTF-8\"),\n" +
            "\tnew StringPart(\"sign\", sign, \"UTF-8\")\n};",
            "市平台请求示例"),
    PRO("String appkey = 请求appKey;\nString requestToken = 请求密钥;\nString currentTime = System.currentTimeMillis();\n" +
            "String sign = MD5Utils.ecodeByMD5(appkey + requestToken + currentTime);\nPart[] parts = new Part[]{\n" +
            "\tnew StringPart(\"appKey\", appkey),\n\tnew StringPart(\"sign\", requestToken),\n" +
            "\tnew StringPart(\"requestTime\", currentTime),\n\tnew StringPart(入参1的key, 入参1的value),\n" +
            "\tnew StringPart(入参2的key, 入参2的value)\n};",
            "省平台请求示例"),

    ;

    EsbServicePublishReqExampleEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    private String code;
    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
