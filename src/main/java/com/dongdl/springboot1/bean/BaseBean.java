package com.dongdl.springboot1.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.dongdl.springboot1.common.Constants;
import com.dongdl.springboot1.util.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/18 15:03 UTC+8
 * @description
 **/
public class BaseBean implements Serializable {

    private static final long serialVersionUID = -33398641333362515L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    @JsonFormat(pattern = DateUtil.FORMAT_TIME, timezone = DateUtil.TIMEZONE)
    private Date createDate;
    @JsonFormat(pattern = DateUtil.FORMAT_TIME, timezone = DateUtil.TIMEZONE)
    private Date modifyDate;
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void init() {
        this.createDate = Calendar.getInstance().getTime();
        this.status = Constants.INT_ONE;
    }
}
