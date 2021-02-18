package com.dongdl.springboot1.dao.idao;

import com.dongdl.springboot1.bean.EsbServiceParamBean;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author <a mailto:dongdongliang13@hotmail.com>zzt002</a>
 * @date 2021/1/20 21:12 UTC+8
 * @description
 **/
@Repository
public interface IEsbServiceParamDao {

    /**
     *
     * @param list
     * @return
     */
    int saveBath(List<EsbServiceParamBean> list);

}
