package com.dongdl.springboot1.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dongdl.springboot1.common.Enums.EsbServiceLogOpTypeEnum;
import com.dongdl.springboot1.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author <a mailto:dongdongliang13@hotmail.com>zzt002</a>
 * @date 2021/1/20 21:05 UTC+8
 * @description
 **/
@Data
@TableName("esb_service_log")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsbServiceLogBean {

    private String id;
    private String publishId;
    private String opType;
    private Date opTime;
    private String opRange;
    private String username;

    /**
     * 重设opType
     *
     * @param opType
     */
    public void resetParam(String opType) {
        this.opTime = DateUtil.getCurrentDate();
        this.opType = opType;
        this.id = null;
        if (opType.equals((EsbServiceLogOpTypeEnum.MODIFIED.getId()))) {
            this.opRange = "API基本信息";
        }
    }

    /**
     * 设置固定值
     *
     * @param publishId
     */
    public void initParam(String publishId) {
        this.publishId = publishId;
        this.opRange = "API申请单";
        this.username = "admin";
    }

    @Override
    public EsbServiceLogBean clone() {
        EsbServiceLogBean bean = new EsbServiceLogBean();
        BeanUtils.copyProperties(this, bean);
        return bean;
    }
}
