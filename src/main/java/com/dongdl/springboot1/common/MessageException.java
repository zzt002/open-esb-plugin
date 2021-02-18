package com.dongdl.springboot1.common;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/8 2:41 UTC+8
 * @description
 */
public class MessageException extends RuntimeException {

    private Integer code;
    private String message;

    public MessageException(String message) {
        this.message = message;
        this.code = 555;
    }

    public MessageException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
