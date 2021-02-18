package com.dongdl.springboot1.dao.localdao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.dongdl.springboot1.bean.BaseBean;
import com.dongdl.springboot1.bean.TestBean;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/7 22:47 UTC+8
 * @description
 */
@Repository
@Mapper
public interface ITestMysqlDao extends BaseMapper<TestBean> {
    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/7 22:58 UTC+8
     * @description 
     */
    ArrayList<TestBean> list();

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/7 23:03 UTC+8
     * @description
     */
    int saveOne(TestBean testBean);

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/7 23:27 UTC+8
     * @description
     */
//    @Results(id = "TestBeanMap2",value = {
//            @Result(property = "id", column = "id"),
//            @Result(property = "ip", column = "ip"),
//            @Result(property = "name", column = "content")
//    })
    @Select("")
    ArrayList<TestBean> listAll();

    @Select("")
    Integer getSeq();

    /**
     * @description
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/8 2:47 UTC+8
     * @param ip
     * @return
     */
    TestBean getOneByIp(String ip);

    @Select("")
    Integer getOneById(Integer seq);

    int saveBath(List<TestBean> list);
}
