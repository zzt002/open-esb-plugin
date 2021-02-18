package com.dongdl.springboot1.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * @author <a mailto:dongdongliang13@hotmail.com>zzt002</a>
 * @date 2021/1/20 21:07 UTC+8
 * @description
 **/
@Data
@TableName("esb_service_param")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsbServiceParamBean {

    private String id;
    private String publishId;
    private Integer paramMode;
    private String paramName;
    private String paramType;
    private String paramRemarker;
    /**
     * 实名认证 1-是 2-否 空
     */
    private String verified;


    @Override
    public EsbServiceParamBean clone() {
        EsbServiceParamBean bean = new EsbServiceParamBean();
        BeanUtils.copyProperties(this, bean);
        return bean;
    }

    public void initParam(String publishId) {
        this.publishId = publishId;
        this.paramType = "String";
    }
}
