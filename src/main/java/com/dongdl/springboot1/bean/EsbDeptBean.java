package com.dongdl.springboot1.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a mailto:dongdl@citycloud.com.cn>dongdl</a>
 * @date 2021/1/27 13:07 UTC+8
 * @description
 **/

@Data
@TableName("S_DEPT_YH")
@AllArgsConstructor
@NoArgsConstructor
public class EsbDeptBean {

    private String code;
    private String name;

}
