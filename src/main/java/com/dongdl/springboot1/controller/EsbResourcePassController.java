package com.dongdl.springboot1.controller;

import com.dongdl.springboot1.common.Enums.LogActionEnum;
import com.dongdl.springboot1.common.ResultData;
import com.dongdl.springboot1.config.annotation.SystemLog;
import com.dongdl.springboot1.service.IEsbResourcePassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/11 12:41 UTC+8
 * @description
 **/
@RestController
@Api(tags = "ESB工单")
@RequestMapping("/resourcePass")
public class EsbResourcePassController {

    @Autowired
    private IEsbResourcePassService esbResourcePassService;

    @GetMapping("/listOfUnset{listCount}")
    @ApiOperation("未实施的工单列表")
    @SystemLog(title = "ESB实施工单", action = LogActionEnum.SELECT)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "listCount", value = "项数", example = "1", dataType = "int"),
            @ApiImplicitParam(name = "type", value = "0-所有 1-未添加消费者IP类型", example = "0", dataType = "int"),
    })
    public ResultData list(@PathVariable Integer listCount, Integer type) {
        return new ResultData(esbResourcePassService.list(listCount, type));
    }

    @GetMapping("/listByApplyCode/{applyCode}")
    @ApiOperation("根据订单号查询工单列表")
    @SystemLog(title = "ESB实施工单", action = LogActionEnum.SELECT)
    public ResultData listByApplyCode(@PathVariable String applyCode) {
        return new ResultData(esbResourcePassService.listByApplyCode(applyCode));
    }

    @GetMapping("/get/implNum/{implNum}")
    @ApiOperation("根据工单号查询工单")
    @SystemLog(title = "ESB实施工单", action = LogActionEnum.SELECT)
    public ResultData getOneByImplNum(@PathVariable String implNum) {
        return new ResultData(esbResourcePassService.getOneByImplNum(implNum));
    }

    @PostMapping("/config/{implNum}")
    @ApiOperation("根据工单号配置工单")
    @SystemLog(title = "ESB实施工单", action = LogActionEnum.UPDATE)
    public ResultData configByImplNum(@PathVariable String implNum, String implRecord) {
        return new ResultData(esbResourcePassService.configByImplNum(implNum, implRecord, "Y"));
    }

    @PostMapping("/cancel/{implNum}")
    @ApiOperation("根据工单号取消配置工单")
    @SystemLog(title = "ESB实施工单", action = LogActionEnum.CANCEL)
    public ResultData cancelByImplNum(@PathVariable String implNum) {
        return new ResultData(esbResourcePassService.cancelByImplNum(implNum));
    }

    @PostMapping("/openIpByImplNum/{implNum}")
    @ApiOperation("根据工单号开启IP权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "implNum", value = "工单号"),
            @ApiImplicitParam(name = "doBank", value = "处理银行资源 0-不处理 1-处理", example = "0", dataType = "int"),
    })
    @SystemLog(title = "ESB实施工单开权限", action = LogActionEnum.OpenAu)
    public ResultData openIpByImplNum(@PathVariable String implNum, Integer doBank) {
        return new ResultData(esbResourcePassService.openIpByImplNum(implNum, doBank));
    }

    @PostMapping("/openIpAuto{listNum}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "listNum", value = "项数", example = "1", dataType = "int"),
            @ApiImplicitParam(name = "range", value = "默认7天内", example = "7", dataType = "int"),
    })
    @ApiOperation("自动开启n天内的工单")
    @SystemLog(title = "ESB实施工单批量开权限", action = LogActionEnum.OpenAu)
    public ResultData openIpAuto(@PathVariable Integer listNum, Integer range) {
        return new ResultData(esbResourcePassService.openIpAuto(listNum, range));
    }
}
