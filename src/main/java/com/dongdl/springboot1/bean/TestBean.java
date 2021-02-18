package com.dongdl.springboot1.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiParam;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/7 22:35 UTC+8
 * @description just for test
 */
@TableName("test")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestBean {

    private int id;
    @ApiParam(required = true)
    @TableField(exist = false)
    private String ip;
    @TableField(exist = false)
    private String content;
    private String name;
    @TableField(exist = false)
    private String base64;
    @TableField(exist = false)
    private String createTime;

    private Date testDate;
    private String testParam;

    private String text;
}
