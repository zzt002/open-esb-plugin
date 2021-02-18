package com.dongdl.springboot1.dao.idao;

import com.dongdl.springboot1.bean.EsbInboundMainBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/7 22:39 UTC+8
 * @description 
 */
@Repository
@Mapper
public interface IEsbInboundMainDao {
    /**
     * @description
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/8 2:09 UTC+8
     * @param servname
     * @return
     */
    EsbInboundMainBean getOneByServname(@Param("servname")String servname);

    /**
     * @description
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/8 2:09 UTC+8
     * @param servcode
     * @return
     */
    EsbInboundMainBean getOneByServcode(@Param("servcode")String servcode);


    /**
     * 修改长编码
     * @param esbServiceId
     * @param oldServcode
     * @param newServcode
     * @return
     */
    int updateServcode(@Param("esbServiceId")String esbServiceId, @Param("oldServcode")String oldServcode, @Param("newServcode")String newServcode);

    /**
     * 通过编码和名称获取
     * @param servcode
     * @param servname
     * @return
     */
    EsbInboundMainBean getOneByServcodeAndServname(@Param("servcode")String servcode, @Param("servname")String servname);
}
