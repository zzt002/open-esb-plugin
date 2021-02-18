package com.dongdl.springboot1.service;

import com.dongdl.springboot1.bean.EsbSystemServiceBean;
import org.apache.ibatis.annotations.Param;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/7 22:40 UTC+8
 * @description
 */
public interface IEsbSystemServiceService {
    /**
     * @param esbSystemServiceBean
     * @return
     * @description
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/8 2:19 UTC+8
     */
    int saveOne(EsbSystemServiceBean esbSystemServiceBean);

    /**
     * @param systemId
     * @param servcode
     * @return
     * @description
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/8 2:19 UTC+8
     */
    EsbSystemServiceBean getOneBySystemIdAndServcode(String systemId, String servcode);

    /**
     *
     * @param oldServCode
     * @param newServCode
     * @return
     */
    int updateServcode(String oldServCode, String newServCode);
}
