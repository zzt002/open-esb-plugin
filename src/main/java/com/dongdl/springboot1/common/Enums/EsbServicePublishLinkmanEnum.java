package com.dongdl.springboot1.common.Enums;

/**
 * @author <a mailto:dongdl@citycloud.com.cn>dongdl</a>
 * @date 2021/1/27 11:45 UTC+8
 * @description API文档联系人枚举
 **/
public enum EsbServicePublishLinkmanEnum {
    PRO("1162", "省接口技术支持"),
    CITY("1182", "鸿程技术支持")
    ;

    EsbServicePublishLinkmanEnum(String id, String name) {
        this.id = id;
        this.name = name;
    }

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
