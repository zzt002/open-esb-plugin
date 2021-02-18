package com.dongdl.springboot1.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dongdl.springboot1.util.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@TableName("esb_reload_fail")
public class EsbReloadFailBean {

    @TableId(type = IdType.AUTO)
    private int id;
    @JsonFormat(pattern = DateUtil.FORMAT_TIME, timezone = DateUtil.TIMEZONE)
    private Date createTime;
    private String reloadUrl;
    private String errorMessage;
    private int retryCount;
}
