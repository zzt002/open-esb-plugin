package com.dongdl.springboot1.controller;

import com.dongdl.springboot1.bean.TaskConfigBean;
import com.dongdl.springboot1.common.Constants;
import com.dongdl.springboot1.common.Enums.LogActionEnum;
import com.dongdl.springboot1.common.ResultData;
import com.dongdl.springboot1.config.annotation.SystemLog;
import com.dongdl.springboot1.service.ITaskConfigService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/11 16:12 UTC+8
 * @description
 **/
@RestController
@Api(tags = "定时任务配置")
@RequestMapping("/taskConfig")
public class TaskConfigController {

    @Autowired
    private ITaskConfigService taskConfigService;

    @GetMapping("/list")
    @SystemLog(title = "定时任务", action = LogActionEnum.SELECT)
    @ApiOperation("定时任务配置列表")
    public ResultData list(Integer pageNum, Integer pageSize, Integer order) {
        pageNum = pageNum == null || pageNum == 0 ? Constants.INT_ONE : pageNum;
        pageSize = pageSize == null ? 20 : pageSize;
        String or = "id " + ( order == null || order == 0 ? "asc" : "desc" );
        PageHelper.startPage(pageNum, pageSize, or);
        PageInfo pageInfo = new PageInfo<>(taskConfigService.list());
        return new ResultData(pageInfo);
    }

    @PostMapping("/update")
    @SystemLog(title = "定时任务", action = LogActionEnum.UPDATE)
    @ApiOperation("修改定时任务")
    public ResultData update(TaskConfigBean taskConfigBean) {
        return new ResultData(taskConfigService.update(taskConfigBean));
    }

    @PostMapping("/change/{id}")
    @SystemLog(title = "开关定时任务", action = LogActionEnum.UPDATE)
    @ApiOperation("开/关定时任务")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResultData change(@PathVariable String id) {
        return new ResultData(taskConfigService.change(id));
    }

    @PostMapping("/save")
    @SystemLog(title = "定时任务", action = LogActionEnum.ADD)
    @ApiOperation("新增定时任务配置")
    public ResultData saveOne(TaskConfigBean taskConfigBean) {
        return new ResultData(taskConfigService.saveOne(taskConfigBean));
    }
}
