package com.dongdl.springboot1.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dongdl.springboot1.bean.EsbReloadFailBean;

import java.util.List;

public interface IEsbReloadFailService extends IService<EsbReloadFailBean> {

    /**
     *
     * @param id
     * @return
     */
    String reload(Integer id);

    List reloadAll();

    /**
     * 查找by 重载地址reload_url
     *
     * @param url
     * @return
     */
    EsbReloadFailBean getOneByUrl(String url);
}
