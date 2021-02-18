package com.dongdl.springboot1.service;

import com.dongdl.springboot1.bean.EsbInboundMainBean;

import java.util.ArrayList;
/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/7 22:40 UTC+8
 * @description 
 */
public interface IEsbInboundMainService {
    /**
     * @description
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/8 2:09 UTC+8
     * @param servname
     * @return
     */
    EsbInboundMainBean getOneByServname(String servname);

    /**
     * @description
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/8 2:09 UTC+8
     * @param servcode
     * @return
     */
    EsbInboundMainBean getOneByServcode(String servcode);

    /**
     *
     * @param servcode
     * @param servname
     * @return
     */
    EsbInboundMainBean getOneByServcodeAndServname(String servcode, String servname);

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/16 12:45 UTC+8
     * @description
     * @param service
     * @param backstage
     * @return
     */
    Object reloadByService(String service, Integer backstage);

    /**
     *
     * @param esbServiceId
     * @param oldServcode
     * @param newServcode
     * @return
     */
    String updateServcode(String esbServiceId, String oldServcode, String newServcode);
}
