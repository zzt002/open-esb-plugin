package com.dongdl.springboot1.bean;
/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/11 12:35 UTC+8
 * @description
 **/
public class EsbSourceInfoBean {

    private String id;
    private String sourceName;
    private String sourceCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }
}
