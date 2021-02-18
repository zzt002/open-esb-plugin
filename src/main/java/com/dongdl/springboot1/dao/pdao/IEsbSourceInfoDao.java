package com.dongdl.springboot1.dao.pdao;

import com.dongdl.springboot1.bean.EsbSourceInfoBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IEsbSourceInfoDao {

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/11 14:02 UTC+8
     * @description
     * @param id
     * @return
     */
    EsbSourceInfoBean getOneById(@Param("id")String id);

}
