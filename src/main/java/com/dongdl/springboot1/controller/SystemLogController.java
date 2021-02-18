package com.dongdl.springboot1.controller;

import com.dongdl.springboot1.bean.SystemLogBean;
import com.dongdl.springboot1.common.Constants;
import com.dongdl.springboot1.common.ResultData;
import com.dongdl.springboot1.service.ISystemLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/18 14:47 UTC+8
 *
 * @description
 **/
@RestController
@RequestMapping("/log")
@Api(tags = "日志查询")
public class SystemLogController {
    @Autowired
    private ISystemLogService systemLogService;

    @GetMapping("/get")
    public ResultData getOne(Integer id) {
        return new ResultData(systemLogService.findOneById(id));
    }

    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", example = "1", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "列表项数", example = "20", dataType = "int"),
            @ApiImplicitParam(name = "order", value = "排序：1-倒序 2-正序", example = "1", dataType = "int")
    })
    @ApiOperation("日志列表")
    public ResultData list(Integer pageNum, Integer pageSize, Integer order, SystemLogBean logBean) {
        pageNum = pageNum == null || pageNum == 0 ? Constants.INT_ONE : pageNum;
        pageSize = pageSize == null ? 20 : pageSize;
        String orderStr  = order == null || order == 1 ? "desc" : "asc";
        String or = "create_date " + orderStr + ",id " + orderStr;
        PageHelper.startPage(pageNum, pageSize, or);
        PageInfo pageInfo = new PageInfo<>(systemLogService.list(logBean));
        return new ResultData(pageInfo);
    }
}
