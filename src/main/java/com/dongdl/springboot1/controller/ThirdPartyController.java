package com.dongdl.springboot1.controller;

import com.dongdl.springboot1.common.Enums.LogActionEnum;
import com.dongdl.springboot1.common.ResultData;
import com.dongdl.springboot1.config.annotation.SystemLog;
import com.dongdl.springboot1.service.IEsbInboundMainService;
import com.dongdl.springboot1.service.IThirdPartyService;
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
 * @date
 * @description
 **/
@RestController
@Api(tags = "调用第三方接口")
@RequestMapping("/thirdParty")
public class ThirdPartyController {

    @Autowired
    private IThirdPartyService thirdPartyService;
    @Autowired
    private IEsbInboundMainService esbInboundMainService;

    @GetMapping("/reload/{service}")
    @ApiImplicitParam(name = "backstage", value = "1-后台处理（默认） 0-非后台处理", example = "1", dataType = "int")
    @SystemLog(title = "ESB接口重载", action = LogActionEnum.GET)
    @ApiOperation("根据服务名/长编码重载服务接口")
    public ResultData reloadByService(@PathVariable String service, Integer backstage) {
        return new ResultData(esbInboundMainService.reloadByService(service, backstage));
    }

//    @GetMapping("/baiDu/ip/{ip}")
//    @SystemLog(title = "百度ip地址", action = LogActionEnum.SELECT)
//    @ApiOperation("根据ip查看归属地")
    public ResultData getBaiDuIp(@PathVariable String ip) {
        return new ResultData(thirdPartyService.getBaiDuIp(ip));
    }

    @PostMapping("/esb/reloadExcel")
    @ApiOperation("根据EXCEL重载接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vpnType", value = "vpn环境选择 1-vpn1 2-vpn2", example = "2", dataType = "int"),
            @ApiImplicitParam(name = "backstage", value = "1-后台处理（默认） 0-非后台处理", example = "1", dataType = "int"),
    })
    @SystemLog(title = "根据EXCEL重载接口", action = LogActionEnum.POST)
    public ResultData reloadByExcel(Integer vpnType, MultipartFile file, Integer backstage) {
        return new ResultData(thirdPartyService.reloadByExcel(vpnType, file, backstage));
    }

    @GetMapping(value = "/esb/reloadExcel/template")
    @ApiOperation(value = "获取重载EXCEL模板文件", produces="application/octet-stream")
    @SystemLog(title = "获取重载EXCEL模板文件", action = LogActionEnum.FILE)
    public ResultData getReloadExcel(HttpServletResponse response) {
        thirdPartyService.getReloadExcel(response);
        return new ResultData();
    }

    @PostMapping(value = "/healthCodeListExcel")
    @ApiOperation(value = "健康码数据处理", produces="application/octet-stream")
    @SystemLog(title = "健康码数据处理", action = LogActionEnum.HEALTH_CODE)
    public void healthCodeListExcel(HttpServletResponse response, MultipartFile file) {
        thirdPartyService.healthCodeListExcel(response, file);
    }

    @GetMapping(value = "/healthCodeExcel/template")
    @ApiOperation(value = "健康码数据处理EXCEL模板文件", produces="application/octet-stream")
    @SystemLog(title = "健康码数据处理EXCEL模板文件", action = LogActionEnum.FILE)
    public void getHealthCodeListExcel(HttpServletResponse response) {
        thirdPartyService.getHealthCodeListExcel(response);
    }
}
