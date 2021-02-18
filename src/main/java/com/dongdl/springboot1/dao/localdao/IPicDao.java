package com.dongdl.springboot1.dao.localdao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.dongdl.springboot1.bean.PicBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/7 22:47 UTC+8
 * @description
 */
@Repository
@Mapper
public interface IPicDao extends BaseMapper<PicBean> {

    @Insert("")
    Integer savePic(@Param("fileName") String fileName, @Param("base64") String base64);

    @Select("")
    Map getPic(@Param("id") Integer id);

    @Select("")
    List<Map> getPics();

    @Select("")
    Integer getPicByName(@Param("fileName") String originalFilename);

    @Select("")
    List<PicBean> listPic(@Param(Constants.WRAPPER) QueryWrapper queryWrapper);
}
