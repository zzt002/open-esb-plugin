package com.dongdl.springboot1.bean;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/7 22:36 UTC+8
 * @description ip存储类
 */
public class EsbSystemIpBean {
    private String id;
    private String systemId;
    private String ip;

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
