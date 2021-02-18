package com.dongdl.springboot1.controller;

import com.alibaba.fastjson.JSONObject;
import com.dongdl.springboot1.bean.EsbInboundMainBean;
import com.dongdl.springboot1.common.Enums.LogActionEnum;
import com.dongdl.springboot1.common.ResultData;
import com.dongdl.springboot1.config.annotation.SystemLog;
import com.dongdl.springboot1.service.IEsbInboundMainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/7 22:39 UTC+8
 * @description
 */
@RestController
@RequestMapping("/esbInboundMain")
@Api(tags = "VPN2 ESB服务基本信息")
public class EsbInboundMainServiceController {

    @Autowired
    private IEsbInboundMainService esbInboundMainService;

    @GetMapping("/getOneByServname/{servname}")
    @ApiOperation("根据服务名查服务信息")
    @SystemLog(title = "ESB服务基本信息", action = LogActionEnum.SELECT)
    public ResultData getOneByServname(@PathVariable String servname) {
        return new ResultData(esbInboundMainService.getOneByServname(servname));
    }

    @GetMapping("/getOneByServcode")
    @ApiOperation("根据服务编码（长）查服务信息")
    @SystemLog(title = "ESB服务基本信息", action = LogActionEnum.SELECT)
    public String getOneByServcode(String servcode) {
        EsbInboundMainBean e = esbInboundMainService.getOneByServcode(servcode);
        return JSONObject.toJSONString(e);
    }


    @PostMapping("/updateServcode")
    @ApiOperation("根据服务修改服务长编码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "esbServiceId", value = "esbseviceid"),
            @ApiImplicitParam(name = "oldServcode", value = "旧长编码"),
            @ApiImplicitParam(name = "newServcode", value = "新长编码")
    })
    @SystemLog(title = "ESB服务基本信息", action = LogActionEnum.SELECT)
    public ResultData updateServcode(String esbServiceId, String oldServcode, String newServcode) {
        return new ResultData(esbInboundMainService.updateServcode(esbServiceId, oldServcode, newServcode));
    }

}
