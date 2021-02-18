package com.dongdl.springboot1.service.impl;

import com.dongdl.springboot1.bean.SystemLogBean;
import com.dongdl.springboot1.common.Constants;
import com.dongdl.springboot1.config.RabbitMqConfig;
import com.dongdl.springboot1.dao.localdao.ISystemLogDao;
import com.dongdl.springboot1.service.ISystemLogService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/18 14:54 UTC+8
 * @description
 **/
@Service
public class SystemLogServiceImpl implements ISystemLogService {

    @Autowired
    private ISystemLogDao systemLogDao;
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public SystemLogBean findOneById(Integer id) {
        return systemLogDao.findOneById(id);
    }

    @Override
    public List<SystemLogBean> list(SystemLogBean logBean) {
        return systemLogDao.list(logBean);
    }

    @Override
    public Object saveOne(SystemLogBean systemLog) {
        systemLog.init();
        return systemLogDao.saveOne(systemLog);
    }

    @Override
    public void saveOneByMq(SystemLogBean systemLog) {
        amqpTemplate.convertAndSend(RabbitMqConfig.APPLICATION_NAME +".saveLog", null, systemLog);
    }
}
