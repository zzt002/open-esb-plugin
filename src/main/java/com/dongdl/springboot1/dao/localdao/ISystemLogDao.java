package com.dongdl.springboot1.dao.localdao;

import com.dongdl.springboot1.bean.SystemLogBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/11 16:10 UTC+8
 * @description
 **/
@Repository
public interface ISystemLogDao {

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/18 14:47 UTC+8
     * @description
     * @param id
     * @return
     */
    @Select("")
    SystemLogBean findOneById(@Param("id")Integer id);

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/18 14:47 UTC+8
     * @description
     * @return
     */
//    @Results(id = "systemLogBeanMap", value = {
//            @Result(property = "createDate", column = "create_date"),
//            @Result(property = "modifyDate", column = "modify"),
//            @Result(property = "", column = ""),
//            @Result(property = "", column = ""),
//            @Result(property = "", column = ""),
//            @Result(property = "", column = "")
//    })
    @Select("")
    List<SystemLogBean> list(SystemLogBean logBean);

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/18 14:53 UTC+8
     * @description
     * @param systemLog
     */
    @Insert("")
    int saveOne(SystemLogBean systemLog);
}
