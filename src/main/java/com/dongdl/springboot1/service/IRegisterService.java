package com.dongdl.springboot1.service;

import com.dongdl.springboot1.bean.RegisterBean;
import com.dongdl.springboot1.dao.common1A4dao.IRegisterDao;

import java.util.List;
import java.util.Map;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/23 10:10 UTC+8
 * @description
 **/
public interface IRegisterService {

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/23 15:20 UTC+8
     * @description
     * @param httpType
     * @param saveLog
     * @param belongId
     * @param ipStr
     * @param limit
     * @param iRegisterDao
     * @return
     */
    Map publishConfigured(Integer httpType, String saveLog, Integer belongId, String ipStr, Integer limit, IRegisterDao iRegisterDao);

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/23 15:21 UTC+8
     * @description
     * @param entity
     * @param iRegisterDao
     * @return
     */
    RegisterBean publishUnConfig(RegisterBean entity, IRegisterDao iRegisterDao);

    /**
     * @description
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/24 23:21 UTC+8
     * @param atomCode
     * @param iRegisterDao
     * @return
     */
    String delRegisterByAtomCode(String atomCode, IRegisterDao iRegisterDao);

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/26 21:40 UTC+8
     * @description
     * @param name
     * @param iRegisterDao
     * @return
     */
    List<Map> listTreeDep(String name, IRegisterDao iRegisterDao);

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/31 15:11 UTC+8
     * @description
     * @param iRegisterDao
     * @return
     */
    Map delListConfigured(IRegisterDao iRegisterDao);

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/4/1 10:24 UTC+8
     * @description 同步正式 和 测试 序列
     */
    void synSeq();

    String getBelongIdByBelongName(String provideDept);
}
