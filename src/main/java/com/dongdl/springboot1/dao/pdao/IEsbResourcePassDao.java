package com.dongdl.springboot1.dao.pdao;

import com.dongdl.springboot1.bean.EsbResourcePassBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEsbResourcePassDao {

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/11 12:48 UTC+8
     * @description
     * @param listCount
     * @return
     */
    List<EsbResourcePassBean> list(@Param("bean")EsbResourcePassBean esbResourcePassBean, @Param("listCount")Integer
            listCount);

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/13 14:07 UTC+8
     * @description
     * @param applyCode
     * @return
     */
    List<EsbResourcePassBean> listByApplyCode(@Param("applyCode")String applyCode);


    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/11 15:19 UTC+8
     * @description
     * @param implNum
     * @return
     */
    EsbResourcePassBean getOneByImplNum(@Param("implNum")String implNum);

    /**
     * @description
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/11 23:11 UTC+8
     * @param esbResourcePassBean
     * @return
     */
    int configByImplNum(EsbResourcePassBean esbResourcePassBean);

    /**
     * @description
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/11 23:11 UTC+8
     * @param implNum
     * @param sts
     * @return
     */
    int cancelByImplNum(@Param("implNum")String implNum, @Param("sts")String sts);
}
