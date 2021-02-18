package com.dongdl.springboot1.dao.localdao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dongdl.springboot1.bean.UserBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface IUserDao extends BaseMapper<UserBean> {


    @Select("<script>select m.* " +
                    "from menu m " +
                    "inner join role_menu_relationship r on r.menu_id=m.id " +
                    "where r.role_id=#{roleId}" +
            "</script>")
    List<Map> listMenu(@Param("roleId") Integer roleId);
}
