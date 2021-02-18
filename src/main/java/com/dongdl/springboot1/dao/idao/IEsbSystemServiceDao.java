package com.dongdl.springboot1.dao.idao;

import com.dongdl.springboot1.bean.EsbSystemServiceBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/7 22:39 UTC+8
 * @description 
 */
@Mapper
@Repository
public interface IEsbSystemServiceDao {
    /**
     * @description
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/8 2:19 UTC+8
     * @param esbSystemServiceBean
     * @return
     */
    int saveOne(EsbSystemServiceBean esbSystemServiceBean);

    /**
     * @description
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/8 2:19 UTC+8
     * @param systemId
     * @param servcode
     * @return
     */
    EsbSystemServiceBean getOneBySystemIdAndServcode(@Param("systemId") String systemId, @Param("servcode")String
            servcode);

    /**
     *
     * @param oldServCode
     * @param newServCode
     * @return
     */
    int updateServcode(@Param("oldServcode")String oldServCode, @Param("newServcode")String newServCode);
}
