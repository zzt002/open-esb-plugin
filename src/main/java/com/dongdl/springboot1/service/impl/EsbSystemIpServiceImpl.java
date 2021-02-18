package com.dongdl.springboot1.service.impl;

import com.dongdl.springboot1.bean.EsbSystemIpBean;
import com.dongdl.springboot1.common.MessageException;
import com.dongdl.springboot1.dao.idao.IEsbSystemIpDao;
import com.dongdl.springboot1.service.IEsbSystemIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.net.util.IPAddressUtil;

@Service
public class EsbSystemIpServiceImpl implements IEsbSystemIpService {

    @Autowired
    private IEsbSystemIpDao esbSystemIpServiceDao;

    @Override
    public EsbSystemIpBean getOneByIp(String ip) {
        if (ip == null || !ip.matches("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$") || !IPAddressUtil.isIPv4LiteralAddress(ip)) {
            throw new MessageException("ip异常：" + ip);
        }
        return esbSystemIpServiceDao.getOneByIp(ip);
    }

}
