package com.dongdl.springboot1.controller;

import com.dongdl.springboot1.common.ResultData;
import com.dongdl.springboot1.service.IEsbSourceInfoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/11 13:59 UTC+8
 * @description
 **/
@RestController
@RequestMapping("/sourceInfo")
@Api
public class EsbSourceInfoController {

    @Autowired
    private IEsbSourceInfoService esbSourceInfoService;

    @GetMapping("/get/{id}")
    public ResultData getOne(@PathVariable String id) {
        return new ResultData(esbSourceInfoService.getOneById(id));
    }
}
