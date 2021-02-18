package com.dongdl.springboot1.controller;

import com.dongdl.springboot1.bean.EsbServiceConsumerBean;
import com.dongdl.springboot1.common.Enums.LogActionEnum;
import com.dongdl.springboot1.common.ResultData;
import com.dongdl.springboot1.config.annotation.SystemLog;
import com.dongdl.springboot1.service.IEsbServiceConsumerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/8 1:31 UTC+8
 * @description
 */
@RestController
@Api(tags = "ESB消费者")
@RequestMapping("/esbServiceConsumer")
public class EsbServiceConsumerController {

    @Autowired
    private IEsbServiceConsumerService esbServiceConsumerService;

//    @PostMapping("/saveOne")
    public ResultData saveOne(EsbServiceConsumerBean esbServiceConsumerBean) {
        return new ResultData(esbServiceConsumerService.saveOne(esbServiceConsumerBean));
    }

//    @PostMapping("/update")
    public ResultData updateById(@PathVariable String id, Integer status) {
        return new ResultData(esbServiceConsumerService.updateOneById(id, status));
    }

    @GetMapping("/getOneByIpIdAndServcode")
    public ResultData getOneByIpIdAndServcode(String ipId, String servcode) {
        return new ResultData(esbServiceConsumerService.getOneByIpIdAndServcode(ipId, servcode));
    }

    @PostMapping("/openIp")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ipStr", value = "逗号隔开ip", required = true),
            @ApiImplicitParam(name = "serviceStr", value = "逗号隔开服务", required = true),
            @ApiImplicitParam(name = "status", value = "1开 0关", required = true, example = "1", dataType = "int")
    })
    @ApiOperation("根据ip和服务信息开权限")
    @SystemLog(title = "ESB手动开权限", action = LogActionEnum.OpenAu)
    public ResultData openIps(String ipStr, String serviceStr, Integer status) {
        return new ResultData(esbServiceConsumerService.openIps(ipStr, serviceStr, status));
    }

    @PostMapping("/openIp/excel")
    @ApiOperation("EXCEL开权限")
    @SystemLog(title = "EXCEL开权限", action = LogActionEnum.OpenAu)
    @ApiImplicitParam(name = "status", value = "1开 0关", required = true, example = "1", dataType = "int")
    public ResultData openIpsByExcel(MultipartFile file, Integer status) {
        return new ResultData(esbServiceConsumerService.openIpsByExcel(file, status));
    }

    @GetMapping(value = "/openIp/getExcel")
    @ApiOperation(value = "获取开权限EXCEL模板文件", produces="application/octet-stream")
    @SystemLog(title = "获取开权限EXCEL模板文件", action = LogActionEnum.FILE)
    public ResultData getJSessionExcel(HttpServletResponse response) {
        esbServiceConsumerService.getExcel(response);
        return new ResultData();
    }

    @GetMapping("/history/list/{ip}")
    @ApiOperation("历史IP权限开通记录")
    @SystemLog(title = "历史IP权限开通记录", action = LogActionEnum.SELECT)
    public ResultData listHistoryIp(@PathVariable String ip) {
        return new ResultData(esbServiceConsumerService.listHistoryIp(ip));
    }
}
