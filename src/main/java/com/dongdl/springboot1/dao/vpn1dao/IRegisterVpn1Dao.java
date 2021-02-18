package com.dongdl.springboot1.dao.vpn1dao;

import com.dongdl.springboot1.dao.common1A4dao.IRegisterDao;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/23 9:58 UTC+8
 * @description
 **/
@Repository
public interface IRegisterVpn1Dao extends IRegisterDao {

    @Override
    @Insert("")
    Integer saveServiceDeployConfOfSubapp(@Param("atomCode")String atomCode);

    @Override
    @Insert("")
    Integer saveServiceDeployConfOfApp(@Param("atomCode")String atomCode);
}
