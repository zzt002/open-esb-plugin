package com.dongdl.springboot1.controller;

import com.dongdl.springboot1.bean.RegisterBean;
import com.dongdl.springboot1.common.Enums.LogActionEnum;
import com.dongdl.springboot1.common.MessageException;
import com.dongdl.springboot1.common.ResultData;
import com.dongdl.springboot1.config.annotation.SystemLog;
import com.dongdl.springboot1.service.IRegisterService;
import com.dongdl.springboot1.util.IPUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/23 10:08 UTC+8
 * @description
 **/
@RestController
@Api(tags = "一键注册接口")
@RequestMapping("/register")
public class RegisterController {

    @Resource(name = "registerVpn1ServiceImpl")
    private IRegisterService registerVpn1ServiceImpl;
    @Resource(name = "registerVpn2ServiceImpl")
    private IRegisterService registerVpn2ServiceImpl;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "部门名称"),
            @ApiImplicitParam(name = "vpnType", value = "vpn环境选择 1-vpn1 2-vpn2", example = "2", dataType = "int")
    })
    @SystemLog(title = "消费者目录部门", action = LogActionEnum.SELECT)
    @PostMapping("/listTreeDep")
    @ApiOperation("消费者二级目录部门检索")
    public ResultData listTreeDep(String name, Integer vpnType) {
        Object result = null;
        if (vpnType == null || vpnType == 2) {
            // 默认vpn2
            result = registerVpn2ServiceImpl.listTreeDep(name, null);
        } else {
            result = registerVpn1ServiceImpl.listTreeDep(name, null);
        }
        return new ResultData(result);
    }

    @PostMapping("/publishConfigured")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "httpType", value = "1-get 2-post", required = true, example = "2", dataType = "int"),
            @ApiImplicitParam(name = "saveLog", value = "日志保存 0-not save 1-save", example = "1"),
            @ApiImplicitParam(name = "belongId", value = "esb_service_tree的id",  required = true, example = "1", dataType = "int"),
            @ApiImplicitParam(name = "limit", value = "限制项数", example = "1", dataType = "int"),
            @ApiImplicitParam(name = "vpnType", value = "vpn环境选择 1-vpn1 2-vpn2", example = "2", dataType = "int"),
            @ApiImplicitParam(name = "ipStr", value = "逗号隔开的ip白名单", example = "10.54.66.168")
    })
    @ApiOperation("已配置CON表 批量注册")
    @SystemLog(title = "批量注册接口", action = LogActionEnum.ADD)
    public ResultData publishConfigured(Integer httpType, String saveLog, Integer belongId, String ipStr, Integer
            limit, Integer vpnType) {
        Map result = null;
        if (vpnType == null || vpnType == 2) {
            // 默认vpn2
            result = registerVpn2ServiceImpl.publishConfigured(httpType, saveLog, belongId, ipStr, limit, null);
        } else {
            result = registerVpn1ServiceImpl.publishConfigured(httpType, saveLog, belongId, ipStr, limit, null);
        }
        return new ResultData(result);
    }

    @PostMapping("/publishUnConfig")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "saveLog", value = "日志保存 0-not save 1-save", example = "1"),
            @ApiImplicitParam(name = "vpnType", value = "vpn环境选择 1-vpn1 2-vpn2", example = "2", dataType = "int"),
            @ApiImplicitParam(name = "ipStr", value = "逗号隔开的ip白名单", example = "10.54.66.168")
    })
    @ApiOperation("未配置CON表 单一注册")
    @SystemLog(title = "一键注册接口", action = LogActionEnum.ADD)
    public ResultData publishUnConfig(RegisterBean entity) {
        RegisterBean result = null;
        if (entity.getVpnType() == null || entity.getVpnType() == 2) {
            // 默认vpn2
            result = registerVpn2ServiceImpl.publishUnConfig(entity, null);
        } else {
            result = registerVpn1ServiceImpl.publishUnConfig(entity, null);
        }
        return new ResultData(result);
    }

    @DeleteMapping("/del")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "atomCode", value = "短编码"),
            @ApiImplicitParam(name = "vpnType", value = "vpn环境选择 1-vpn1 2-vpn2", example = "2", dataType = "int")
    })
    @SystemLog(title = "一键注册接口", action = LogActionEnum.DELETE)
    @ApiOperation("一键删除")
    public ResultData del(String atomCode, Integer vpnType) {
        Object result = null;
        if (vpnType == null || vpnType == 2) {
            // 默认vpn2
            result = registerVpn2ServiceImpl.delRegisterByAtomCode(atomCode, null);
        } else {
            result = registerVpn1ServiceImpl.delRegisterByAtomCode(atomCode, null);
        }
        return new ResultData(result);
    }

    @DeleteMapping("/list/del")
    @ApiImplicitParam(name = "vpnType", value = "vpn环境选择 1-vpn1 2-vpn2", example = "2", dataType = "int")
    @SystemLog(title = "一键注册接口", action = LogActionEnum.DELETE)
    @ApiOperation("批量删除")
    public ResultData delListConfigured(Integer vpnType) {
        Object result = null;
        if (vpnType == null || vpnType == 2) {
            // 默认vpn2
            result = registerVpn2ServiceImpl.delListConfigured(null);
        } else {
            result = registerVpn1ServiceImpl.delListConfigured(null);
        }
        return new ResultData(result);
    }
}
