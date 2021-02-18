package com.dongdl.springboot1.common.Enums;

/**
 * @author <a mailto:dongdl@citycloud.com.cn>dongdl</a>
 * @date 2021/1/27 11:45 UTC+8
 * @description API文档发布状态枚举
 **/
public enum EsbServicePublishStatusEnum {
    NEW("0", "新建申请单"),
    WAIT_CONFIRM("1", "待批准"),
    WAIT_PUBLISH("2", "待发布"),
    PUBLISHED("3", "已发布"),
    CANCEL("4", "已作废")
    ;

    EsbServicePublishStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
