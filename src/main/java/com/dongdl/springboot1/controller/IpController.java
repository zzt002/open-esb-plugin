package com.dongdl.springboot1.controller;

import com.dongdl.springboot1.common.Enums.LogActionEnum;
import com.dongdl.springboot1.common.ResultData;
import com.dongdl.springboot1.config.annotation.SystemLog;
import com.dongdl.springboot1.service.IIpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "IP白名单")
@RequestMapping("/whiteList")
public class IpController {

    @Autowired
    private IIpService ipService;

    @GetMapping("/ip/{ip}")
    @SystemLog(title = "ip白名单", action = LogActionEnum.SELECT)
    public ResultData getOneByIp(@PathVariable String ip) {
        return new ResultData(ipService.getOneByIp(ip));
    }

    @GetMapping("/list/")
    @SystemLog(title = "ip白名单", action = LogActionEnum.SELECT)
    public ResultData list() {
        return new ResultData(ipService.list());
    }

    @PostMapping("/conf/{ip}")
    @ApiImplicitParam(name = "status", value = "1-加 2-删", dataType = "int", example = "1")
    @SystemLog(title = "ip白名单", action = LogActionEnum.IPCONF)
    public ResultData confIp(@PathVariable String ip, Integer status) {
        return new ResultData(ipService.confIp(ip, status));
    }
}
