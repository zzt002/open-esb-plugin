package com.dongdl.springboot1.bean;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/7 22:35 UTC+8
 * @description just for test
 */
@TableName("pic")
public class PicBean {

    private int id;
    private String name;
    private String base64;
    private String createTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
