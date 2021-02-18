package com.dongdl.springboot1.service;

import com.dongdl.springboot1.bean.EsbResourcePassBean;

import java.util.List;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/11 12:45 UTC+8
 * @description
 **/
public interface IEsbResourcePassService {

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/11 12:48 UTC+8
     * @description
     * @param listCount
     * @param type
     * @return
     */
    List<EsbResourcePassBean> list(Integer listCount, Integer type);

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/11 12:49 UTC+8
     * @description 
     * @param implNum
     * @param implRecord
     * @param sts
     * @return
     */
    Object configByImplNum(String implNum, String implRecord, String sts);

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/11 15:15 UTC+8
     * @description
     * @param implNum
     * @param doBank
     * @return
     */
    String openIpByImplNum(String implNum, Integer doBank);

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/11 15:19 UTC+8
     * @description
     * @param implNum
     * @return
     */
    EsbResourcePassBean getOneByImplNum(String implNum);

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/11 16:07 UTC+8
     * @description 自动开启
     * @param listNum
     */
    List openIpAuto(Integer listNum, Integer range);

    /**
     * @description
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/11 22:48 UTC+8
     * @param implNum
     * @return
     */
    Object cancelByImplNum(String implNum);

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/13 14:08 UTC+8
     * @description
     * @param applyCode
     * @return
     */
    List<EsbResourcePassBean> listByApplyCode(String applyCode);
}
