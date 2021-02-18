package com.dongdl.springboot1.controller;

import com.dongdl.springboot1.bean.EsbSystemServiceBean;
import com.dongdl.springboot1.common.Enums.LogActionEnum;
import com.dongdl.springboot1.common.ResultData;
import com.dongdl.springboot1.config.annotation.SystemLog;
import com.dongdl.springboot1.service.IEsbSystemServiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/7 22:39 UTC+8
 * @description 
 */
@RestController
@RequestMapping("/esbSystemService")
@Api(tags = "消费者部门配置")
public class EsbSystemServiceController {

    @Autowired
    private IEsbSystemServiceService esbSystemServiceService;

//    @PostMapping("/saveOneDefault")
    public ResultData saveOneDefault(EsbSystemServiceBean esbSystemServiceBean) {
        return new ResultData(esbSystemServiceService.saveOne(esbSystemServiceBean));
    }

    @GetMapping("/getOneBySystemIdAndServcode")
    @ApiOperation("根据system_id和servcode查看消费者部门配置")
    @SystemLog(title = "消费者部门配置", action = LogActionEnum.SELECT)
    public ResultData getOneBySystemIdAndServcode(String systemId, String servcode) {
        return new ResultData(esbSystemServiceService.getOneBySystemIdAndServcode(systemId, servcode));
    }

}
