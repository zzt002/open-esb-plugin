package com.dongdl.springboot1.controller;

import com.dongdl.springboot1.common.Enums.LogActionEnum;
import com.dongdl.springboot1.common.ResultData;
import com.dongdl.springboot1.config.annotation.SystemLog;
import com.dongdl.springboot1.service.IEsbSystemIpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/7 22:39 UTC+8
 * @description
 */
@RestController
@RequestMapping("/esbSystemIp")
@Api(tags = "ESB消费者ip")
public class EsbSystemIpController {

    @Autowired
    private IEsbSystemIpService esbSystemIpService;

    @GetMapping("/getOneByIp/{ip}")
    @ApiOperation("根据ip查看ip信息")
    @SystemLog(title = "ESB消费者IP", action = LogActionEnum.SELECT)
    public ResultData getOneByIp(@PathVariable String ip) {
        return new ResultData(esbSystemIpService.getOneByIp(ip));
    }
}
