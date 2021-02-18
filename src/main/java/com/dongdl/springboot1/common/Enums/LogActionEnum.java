package com.dongdl.springboot1.common.Enums;
/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/18 14:36 UTC+8
 * @description
 **/
public enum LogActionEnum {
    ADD("ADD", "新增"),
    UPDATE("UPDATE", "修改"),
    DELETE("DELETE", "删除"),
    SELECT("SELECT", "查询"),
    CANCEL("CANCEL", "取消"),
    OPEN("OPEN", "开启"),
    GET("GET", "请求第三方get接口"),
    POST("POST", "请求第三方post接口"),
    REGISTER("REGISTER", "一键注册接口"),
    OpenAu("OpenAu", "开权限"),
    IPCONF("IPCONF", "配置白名单"),
    FILE("FILE", "获取文件"),
    RELOAD("RELOAD", "重载接口"),
    RELOAD_FAIL("RELOAD_FAIL", "重载失败接口"),
    RELOAD_ALL_FAIL("RELOAD_ALL_FAIL", "重载所有失败接口"),
    LOGIN("LOGIN", "登录"),
    UREGISTER("USER_REGISTER", "用户注册"),
    HEALTH_CODE("HEALTH_CODE", "健康码数据处理"),
    BACKUP_MYSQL("BACKUP_MYSQL", "数据库备份")
    ;

    LogActionEnum (String action, String name) {
        this.action = action;
        this.name = name;
    }

    private String action;
    private String name;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
