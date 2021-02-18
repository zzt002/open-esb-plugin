package com.dongdl.springboot1.dao.idao;

import com.dongdl.springboot1.bean.EsbServiceConsumerBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/8 1:37 UTC+8
 * @description
 */
@Repository
@Mapper
public interface IEsbServiceConsumerDao {
    /**
     * @description
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/8 1:42 UTC+8
     * @param esbServiceConsumerBean
     * @return
     */
    int saveOne(EsbServiceConsumerBean esbServiceConsumerBean);

    /**
     * @description
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/8 1:52 UTC+8
     * @param id
     * @param status
     * @return
     */
    int updateOneById(@Param("id")String id, @Param("status")String status);

    /**
     * @description
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/8 1:59 UTC+8
     * @param ipId
     * @param servcode
     * @return
     */
    EsbServiceConsumerBean getOneByIpIdAndServcode(@Param("ipId")String ipId, @Param("servcode")String servcode);

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/4/8 9:27 UTC+8
     * @description
     * @param ipId
     * @return
     */
    @ResultMap("EsbServiceConsumerBeanMap")
    @Select("")
    List<EsbServiceConsumerBean> listHistoryIpByIpId(@Param("ipId")String ipId);

    /**
     *
     * @param oldServcode
     * @param newServcode
     * @return
     */
    int updateServcode(@Param("oldServcode")String oldServcode, @Param("newServcode")String newServcode);
}
