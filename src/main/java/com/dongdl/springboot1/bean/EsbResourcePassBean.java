package com.dongdl.springboot1.bean;

import java.util.Date;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/11 12:33 UTC+8
 * @description 
 **/
public class EsbResourcePassBean {

    private String id;
    // 实施工单号
    private String implNum;
    // 资源id
    private String resId;
    // 资源类型
    private String resourceType;
    // 消费者id
    private String customerId;
    // 配置说明
    private String implRecord;
    // 修改人id
    private String modifyUserId;
    // 实施状态 Y U D C
    private String sts;
    // 修改时间
    private Date modifyDate;

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImplNum() {
        return implNum;
    }

    public void setImplNum(String implNum) {
        this.implNum = implNum;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getImplRecord() {
        return implRecord;
    }

    public void setImplRecord(String implRecord) {
        this.implRecord = implRecord;
    }

    public String getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(String modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    public String getSts() {
        return sts;
    }

    public void setSts(String sts) {
        this.sts = sts;
    }
}
