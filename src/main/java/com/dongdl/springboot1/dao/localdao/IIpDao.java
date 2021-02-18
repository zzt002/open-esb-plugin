package com.dongdl.springboot1.dao.localdao;

import com.dongdl.springboot1.bean.IpBean;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/26 14:18 UTC+8
 * @description
 **/
@Repository
public interface IIpDao {

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/26 14:15 UTC+8
     * @description
     * @param ip
     * @return
     */
    @Select("")
    IpBean getOneByIp(@Param("ip")String ip);

    /**
     *
     * @param ip
     * @return
     */
    @Delete("")
    Integer delIp(@Param("ip")String ip);

    /**
     *
     * @param ip
     * @return
     */
    @Insert("")
    Integer saveIp(@Param("ip")String ip);

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/26 14:51 UTC+8
     * @description
     * @return
     */
    @Select("")
    List<IpBean> list();
}
