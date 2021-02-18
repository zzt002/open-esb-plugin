package com.dongdl.springboot1.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dongdl.springboot1.bean.EsbReloadFailBean;
import com.dongdl.springboot1.common.Enums.LogActionEnum;
import com.dongdl.springboot1.common.ResultData;
import com.dongdl.springboot1.config.annotation.SystemLog;
import com.dongdl.springboot1.service.IEsbReloadFailService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/7/8 20:35 UTC+8
 * @description
 */
@RestController
@RequestMapping("/reload/fail")
@Api(tags = "接口重载失败列表")
public class EsbReloadFailController {

    @Autowired
    IEsbReloadFailService esbReloadFailService;

    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", example = "1", dataType="int"),
            @ApiImplicitParam(name = "pageSize", example = "20", dataType="int")
    })
    @SystemLog(title = "重载失败列表", action = LogActionEnum.SELECT)
    public ResultData list(Integer pageNum, Integer pageSize) {
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 20 : pageSize;
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<EsbReloadFailBean> queryWrapper = new QueryWrapper<EsbReloadFailBean>();

        return new ResultData(new PageInfo<>(esbReloadFailService.list(queryWrapper)));
    }

    @PostMapping("/reload/{id}")
    @SystemLog(title = "重载失败接口", action = LogActionEnum.RELOAD_FAIL)
    public ResultData reload(@PathVariable Integer id) {
        return new ResultData(esbReloadFailService.reload(id));
    }

    @PostMapping("/reload/all")
    @SystemLog(title = "重载所有失败接口", action = LogActionEnum.RELOAD_ALL_FAIL)
    public ResultData reloadAll() {
        return new ResultData(esbReloadFailService.reloadAll());
    }

}
