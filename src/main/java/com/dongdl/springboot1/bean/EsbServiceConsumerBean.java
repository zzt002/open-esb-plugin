package com.dongdl.springboot1.bean;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/7 22:36 UTC+8
 * @description ip权限
 */
@TableName("ESB_SERVICE_CONSUMER")
public class EsbServiceConsumerBean {

    private String id;
    private String servcode;
    private String ipId;
    private String status;
    private String servname;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getServcode() {
        return servcode;
    }

    public void setServcode(String servcode) {
        this.servcode = servcode;
    }

    public String getIpId() {
        return ipId;
    }

    public void setIpId(String ipId) {
        this.ipId = ipId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServname() {
        return servname;
    }

    public void setServname(String servname) {
        this.servname = servname;
    }
}
