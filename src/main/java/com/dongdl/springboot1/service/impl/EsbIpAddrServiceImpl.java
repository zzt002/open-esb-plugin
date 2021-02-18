package com.dongdl.springboot1.service.impl;

import com.dongdl.springboot1.bean.EsbIpAddrBean;
import com.dongdl.springboot1.dao.pdao.IEsbIpAddrDao;
import com.dongdl.springboot1.service.IEsbIpAddrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EsbIpAddrServiceImpl implements IEsbIpAddrService {

    @Autowired
    private IEsbIpAddrDao esbIpAddrDao;

    @Override
    public List<EsbIpAddrBean> list(String myApp) {
        return esbIpAddrDao.list(myApp);
    }
}
