package com.dongdl.springboot1.common.Enums;
/**
 * @author <a mailto:dongdongliang13@hotmail.com>zzt002</a>
 * @date 2021/1/20 22:42 UTC+8
 * @description
 **/
public enum EsbServiceLogOpTypeEnum {
    VERIFYING("5", "提交审核"),
    VERIFIED("2", "审核通过"),
    PUBLISH("3", "已发布"),
    MODIFIED("1", "修改")
    ;

    EsbServiceLogOpTypeEnum(String id, String description) {
        this.id = id;
        this.description = description;
    }

    private String id;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
