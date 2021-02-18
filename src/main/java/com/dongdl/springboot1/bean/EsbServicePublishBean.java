package com.dongdl.springboot1.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dongdl.springboot1.common.Enums.EsbServicePublishApiAddressEnum;
import com.dongdl.springboot1.common.Enums.EsbServicePublishLinkmanEnum;
import com.dongdl.springboot1.common.Enums.EsbServicePublishReqExampleEnum;
import com.dongdl.springboot1.common.Enums.EsbServicePublishStatusEnum;
import com.dongdl.springboot1.common.MessageException;
import com.dongdl.springboot1.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author <a mailto:dongdongliang13@hotmail.com>zzt002</a>
 * @date 2021/1/20 17:09 UTC+8
 * @description
 **/
@Data
@TableName("esb_service_publish")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsbServicePublishBean {

    /**
     * 主键
     */
    private String id;
    /**
     * 默认 33.1111
     */
    private String providerCode;
    /**
     * 项目名称 e.g. xxx接口
     */
    private String appName;
    /**
     * 接口服务名称
     */
    private String servName;
    private String isGroup;
    private String servCode;
    private String servVersion;
    private String servType;
    /**
     * 状态
     * 0-新建申请单 1-待批准 2-待发布 3-已发布 4-已作废
     */
    private String status;
    /**
     * 接口描述
     */
    private String description;
    /**
     * 接口返回示例
     */
    private String example;
    /**
     * 服务访问地址
     */
    private String apiAddress;
    /**
     * 支持数据格式
     */
    private String supportFormat;
    /**
     * 接口请求示例
     */
    private String reqExample;
    /**
     * 部门名称
     */
    private String provideDept;
    /**
     * 部门编码
     */
    private String provideDeptCode;
    /**
     * 上报状态   0：未上报  1：已上报
     */
    private String reportedStatus;
    /**
     * 关联联系人id
     */
    private String linkmanId;
    /**
     * 接口类型 1-数据接口 2-验证接口
     */
    private String apiType;
    /**
     * 共享类型 IN-内部共享 OUT-外部共享 BOTH-内外部共享
     */
    private String shareType;

    /**
     * 原始接口编码
     */
    private String firstApiCode;

    public void initParam() {
        this.providerCode = "33.1111";
        this.supportFormat = "JSON";
        this.isGroup = "0";
        this.apiType = "1";
        this.shareType = "BOTH";
        this.servVersion = "1.0";
        this.apiAddress = EsbServicePublishApiAddressEnum.HTTP.getUrl();
        this.status = EsbServicePublishStatusEnum.PUBLISHED.getCode();
    }

    /**
     * 省接口初始
     */
    public void initPro() {
        this.linkmanId = EsbServicePublishLinkmanEnum.PRO.getId();
        this.reqExample = EsbServicePublishReqExampleEnum.PRO.getCode();

    }

    /**
     * 市接口初始
     */
    public void initCity() {
        this.linkmanId = EsbServicePublishLinkmanEnum.CITY.getId();
        this.reqExample = EsbServicePublishReqExampleEnum.CITY.getCode();
        this.firstApiCode = null;
    }

    /**
     * 数据校验完后的参数补充
     */
    public void restParam() {
        if (StringUtil.isEmpty(this.description)) {
            this.description = this.servName;
        }
        if (StringUtil.isEmpty(this.appName)) {
            this.appName = String.format("%s接口", this.provideDept);
        }
        if (StringUtil.isEmpty(this.firstApiCode)) {
            this.firstApiCode = StringUtil.getScaidByReg(this.getServCode());
        }

    }

    /**
     * 必填判断
     */
    public void valid() throws IllegalAccessException {
        // todo： 必填
        if (StringUtil.isEmpty(providerCode)) {

            throw new MessageException("");
        }

        Field[] fields = this.getClass().getDeclaredFields();
        for(Field f : fields) {
            System.out.println("Field: " + f.getName() + ", value: " + f.get(this));
        }
    }

}


