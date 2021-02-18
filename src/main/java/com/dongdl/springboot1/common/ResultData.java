package com.dongdl.springboot1.common;

import org.springframework.http.HttpStatus;
/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/7 22:36 UTC+8
 * @description custom result of data
 */
public class ResultData {

    private Integer code;
    private String message;
    private Object data;

    public ResultData() {
        this.code = HttpStatus.OK.value();
        this.message = HttpStatus.OK.getReasonPhrase();
    }

    public ResultData(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResultData(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultData(Object data) {
        this.code = HttpStatus.OK.value();
        this.message = HttpStatus.OK.getReasonPhrase();
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
