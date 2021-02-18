package com.dongdl.springboot1.service.impl;

import com.dongdl.springboot1.bean.EsbInboundMainBean;
import com.dongdl.springboot1.common.MessageException;
import com.dongdl.springboot1.dao.idao.IEsbInboundMainDao;
import com.dongdl.springboot1.service.*;
import com.dongdl.springboot1.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EsbInboundMainServiceImpl implements IEsbInboundMainService {

    @Autowired
    private IEsbInboundMainDao esbInboundMainDao;
    @Autowired
    private IThirdPartyService thirdPartyService;
    @Autowired
    private IEsbSystemServiceService esbSystemServiceService;
    @Autowired
    private IEsbServiceConsumerService esbServiceConsumerService;

    @Override
    public EsbInboundMainBean getOneByServname(String servname) {
        return esbInboundMainDao.getOneByServname(servname);
    }

    @Override
    public EsbInboundMainBean getOneByServcode(String servcode) {
        return esbInboundMainDao.getOneByServcode(servcode);
    }

    @Override
    public Object reloadByService(String service, Integer backstage) {
        EsbInboundMainBean esbInboundMainBean = null;
        if (service.matches("^33\\.1111.*")) {
            esbInboundMainBean = getOneByServcode(service);
        } else {
            esbInboundMainBean = getOneByServname(service);
        }
        if (esbInboundMainBean == null || esbInboundMainBean.getEsbServiceId() == null) {
            throw new MessageException("重载失败，服务不存在：" + service);
        }
        return thirdPartyService.reloadEsb(esbInboundMainBean.getEsbServiceId(), esbInboundMainBean.getServiceCode(),
                backstage);
    }

    @Override
    @Transactional(transactionManager = "data1TransactionManager")
    public String updateServcode(String esbServiceId, String oldServcode, String newServcode) {

        // 长编码  基本数据校验
        if (StringUtil.isEmpty(newServcode)) {
            throw new MessageException("长编码不能为空");
        }
        newServcode = newServcode.trim();
        if (newServcode.contains(" ")) {
            throw new MessageException("长编码不能包含空格");
        }

        // 修改 esb_inbound_main 服务基本信息表
        if (esbInboundMainDao.updateServcode(esbServiceId, oldServcode, newServcode) == 0 ) {
            throw new MessageException(String.format("未有服务信息【esbserviceid:%s, servcode:%s】", esbServiceId, oldServcode));
        }
        // 修改 esb_system_service 部门权限表
        esbSystemServiceService.updateServcode(oldServcode, newServcode);
        // 修改 esb_service_consumer ip权限表
        esbServiceConsumerService.updateServcode(oldServcode, newServcode);

        return String.format("修改完成，长编码已由%s改为%s", oldServcode, newServcode);
    }

    @Override
    public EsbInboundMainBean getOneByServcodeAndServname(String servcode, String servname) {
        return esbInboundMainDao.getOneByServcodeAndServname(servcode, servname);
    }
}
