package com.dongdl.springboot1.service.impl;

import com.dongdl.springboot1.bean.IpBean;
import com.dongdl.springboot1.common.MessageException;
import com.dongdl.springboot1.dao.localdao.IIpDao;
import com.dongdl.springboot1.service.IIpService;
import com.dongdl.springboot1.util.IPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.net.util.IPAddressUtil;

import java.util.List;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/26 14:17 UTC+8
 * @description
 **/
@Service
public class IpServiceImpl implements IIpService {

    @Autowired
    private IIpDao ipDao;

    @Override
    public IpBean getOneByIp(String ip) {
        return ipDao.getOneByIp(ip);
    }

    @Override
    public Integer confIp(String ip, Integer status) {
        Integer result = null;
        if (status == null || status == 1) {
            if (ipDao.getOneByIp(ip) == null) {
                if (!IPUtils.isIP4(ip)) {
                    throw new MessageException("非法的IP4地址：" + ip);
                }
                result = ipDao.saveIp(ip);
            } else {
                result = 1;
            }
        } else {
            result = ipDao.delIp(ip);
        }
        return result;
    }

    @Override
    public List<IpBean> list() {
        return ipDao.list();
    }
}
