package com.dongdl.springboot1.dao.pdao;

import com.dongdl.springboot1.bean.EsbIpAddrBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEsbIpAddrDao {

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/11 14:11 UTC+8
     * @description
     * @param myApp
     * @return
     */
    List<EsbIpAddrBean> list(@Param("myApp")String myApp);
}
