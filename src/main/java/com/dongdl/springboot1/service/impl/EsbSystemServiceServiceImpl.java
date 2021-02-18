package com.dongdl.springboot1.service.impl;

import com.dongdl.springboot1.bean.EsbSystemServiceBean;
import com.dongdl.springboot1.dao.idao.IEsbSystemServiceDao;
import com.dongdl.springboot1.service.IEsbSystemServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EsbSystemServiceServiceImpl implements IEsbSystemServiceService {

    private final static String PRIORITY = "1";
    private final static String DEFAULT_VERSION = "1.0";
    private final static String MAX_CLIENT = "20";
    private final static String IS_VERIFY = "0";

    @Autowired
    private IEsbSystemServiceDao esbSystemServiceDao;

    @Override
    public int saveOne(EsbSystemServiceBean esbSystemServiceBean) {
        esbSystemServiceBean.setPriority(PRIORITY);
        esbSystemServiceBean.setDefaultversion(DEFAULT_VERSION);
        esbSystemServiceBean.setMaxclient(MAX_CLIENT);
        esbSystemServiceBean.setIsverify(IS_VERIFY);
        return esbSystemServiceDao.saveOne(esbSystemServiceBean);
    }

    @Override
    public EsbSystemServiceBean getOneBySystemIdAndServcode(String systemId, String servcode) {
        return esbSystemServiceDao.getOneBySystemIdAndServcode(systemId, servcode);
    }

    @Override
    public int updateServcode(String oldServCode, String newServCode) {
        return esbSystemServiceDao.updateServcode(oldServCode, newServCode);
    }

}
