package com.dongdl.springboot1.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dongdl.springboot1.bean.PicBean;
import com.dongdl.springboot1.common.Enums.LogActionEnum;
import com.dongdl.springboot1.common.ResultData;
import com.dongdl.springboot1.config.annotation.SystemLog;
import com.dongdl.springboot1.service.IPicService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/7 22:39 UTC+8
 * @description
 */
//@RestController
//@Api
//@RequestMapping("/pic")
public class PicController {

    @Autowired
    private IPicService picService;

    @PostMapping("/savePics")
    public ResultData savePics(MultipartFile[] files){
        return new ResultData(picService.savePics(files));
    }

    @PostMapping("/save")
    public ResultData savePic(MultipartFile file){
        return new ResultData(picService.savePic(file));
    }

    @GetMapping("/download{id}")
    @ApiImplicitParam(name = "id", value = "-1 for all")
    @SystemLog(title = "download pic", action = LogActionEnum.FILE)
    public ResultData getPic(@PathVariable Integer id){
        return new ResultData(picService.getPic(id));
    }

    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", example = "1", dataType="int"),
            @ApiImplicitParam(name = "pageSize", example = "20", dataType="int"),
            @ApiImplicitParam(name = "orderType", value = "1-desc 2-asc", example = "1", dataType="int"),
            @ApiImplicitParam(name = "showBase", value = "0-no base64 1-show base64", example = "0", dataType="int"),
    })
    public ResultData listPic(Integer pageNum, Integer pageSize, Integer orderType, Integer id, String name, Integer showBase) {
        showBase = showBase == null ? 0 : showBase;
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 20 : pageSize;
        PageHelper.startPage(pageNum, pageSize, String.format("id %s", orderType == null || orderType == 1 ? "desc" : "asc"));
        QueryWrapper<PicBean> queryWrapper = new QueryWrapper<PicBean>();
        if (showBase == 0) {
            queryWrapper.select("id", "name", "create_time");
        }
        if (id != null) {
            queryWrapper.eq("id", id);
        }
        if (name != null) {
            queryWrapper.like("name", String.format("%s", name));
        }
        return new ResultData(new PageInfo<>(picService.selectList(queryWrapper)));
    }
}
