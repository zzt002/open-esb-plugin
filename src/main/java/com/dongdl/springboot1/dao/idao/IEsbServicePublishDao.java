package com.dongdl.springboot1.dao.idao;

import com.dongdl.springboot1.bean.EsbServicePublishBean;
import org.springframework.stereotype.Repository;

/**
 * @author <a mailto:dongdongliang13@hotmail.com>zzt002</a>
 * @date 2021/1/20 21:12 UTC+8
 * @description 
 **/
@Repository
public interface IEsbServicePublishDao {

    /**
     *
     * @param esbServicePublishBean
     * @return
     */
    int saveOne(EsbServicePublishBean esbServicePublishBean);
}
