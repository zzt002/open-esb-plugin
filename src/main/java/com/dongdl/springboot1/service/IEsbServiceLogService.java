package com.dongdl.springboot1.service;

/**
 * @author <a mailto:dongdongliang13@hotmail.com>zzt002</a>
 * @date 2021/1/20 21:13 UTC+8
 * @description 
 **/
public interface IEsbServiceLogService {

    /**
     * 批量存储
     *
     * @param publishId
     * @return
     */
    Integer saveBath(String publishId);
}
