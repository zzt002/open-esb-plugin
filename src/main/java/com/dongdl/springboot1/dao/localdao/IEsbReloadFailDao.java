package com.dongdl.springboot1.dao.localdao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dongdl.springboot1.bean.EsbReloadFailBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface IEsbReloadFailDao extends BaseMapper<EsbReloadFailBean>{

    @Select("")
    EsbReloadFailBean getOneByUrl(@Param("url")String url);
}
