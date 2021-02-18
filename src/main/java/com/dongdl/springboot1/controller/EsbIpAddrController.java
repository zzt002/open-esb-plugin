package com.dongdl.springboot1.controller;

import com.dongdl.springboot1.common.Enums.LogActionEnum;
import com.dongdl.springboot1.common.ResultData;
import com.dongdl.springboot1.config.annotation.SystemLog;
import com.dongdl.springboot1.service.IEsbIpAddrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "工单分配ip查询")
@RequestMapping("/IpAddr")
@RestController
public class EsbIpAddrController {

    @Autowired
    private IEsbIpAddrService esbIpAddrService;

    @GetMapping("/list/{myApp}")
    @ApiOperation("工单权限IP")
    @SystemLog(title = "工单权限IP", action = LogActionEnum.SELECT)
    public ResultData list(@PathVariable String myApp) {
        return new ResultData(esbIpAddrService.list(myApp));
    }
}
