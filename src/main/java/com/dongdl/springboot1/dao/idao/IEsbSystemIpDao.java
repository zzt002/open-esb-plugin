package com.dongdl.springboot1.dao.idao;

import com.dongdl.springboot1.bean.EsbSystemIpBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/7 22:47 UTC+8
 * @description
 */
@Mapper
@Repository
public interface IEsbSystemIpDao {
    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/7 22:58 UTC+8
     * @description 
     */
    EsbSystemIpBean getOneByIp(@Param("ip")String ip);
}
