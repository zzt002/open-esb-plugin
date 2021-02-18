package com.dongdl.springboot1.bean;
/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/7 22:36 UTC+8
 * @description 原子服务配置
 */
public class EsbSystemServiceBean {

    private String id;
    private String systemId;
    private String servcode;
    private String priority;
    private String defaultversion;
    private String maxclient;
    private String isverify;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getServcode() {
        return servcode;
    }

    public void setServcode(String servcode) {
        this.servcode = servcode;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDefaultversion() {
        return defaultversion;
    }

    public void setDefaultversion(String defaultversion) {
        this.defaultversion = defaultversion;
    }

    public String getMaxclient() {
        return maxclient;
    }

    public void setMaxclient(String maxclient) {
        this.maxclient = maxclient;
    }

    public String getIsverify() {
        return isverify;
    }

    public void setIsverify(String isverify) {
        this.isverify = isverify;
    }
}
