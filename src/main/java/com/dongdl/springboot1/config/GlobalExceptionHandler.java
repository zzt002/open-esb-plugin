package com.dongdl.springboot1.config;

import com.dongdl.springboot1.common.MessageException;
import com.dongdl.springboot1.common.ResultData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/8/5 21:14 UTC+8
 * @description 
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MessageException.class)
    @ResponseBody
    public ResultData messageException(MessageException e) {
        return new ResultData(e.getCode(), e.getMessage());
    }

//    @ExceptionHandler(value = Exception.class)
//    @ResponseBody
//    public ResultData exception(Exception e) {
//        return new ResultData(500, e.getMessage());
//    }
}
