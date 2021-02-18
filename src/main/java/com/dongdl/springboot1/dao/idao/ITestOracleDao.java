package com.dongdl.springboot1.dao.idao;

import com.dongdl.springboot1.bean.TestBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/7 22:47 UTC+8
 * @description
 */
@Repository
@Mapper
public interface ITestOracleDao {
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

}
