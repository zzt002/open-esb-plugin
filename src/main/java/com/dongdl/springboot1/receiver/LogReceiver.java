package com.dongdl.springboot1.receiver;

import com.dongdl.springboot1.bean.SystemLogBean;
import com.dongdl.springboot1.config.RabbitMqConfig;
import com.dongdl.springboot1.service.ISystemLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/13 15:50 UTC+8
 * @description
 **/
@Component
public class LogReceiver {

    private static final Logger LOG = LoggerFactory.getLogger(LogReceiver.class);

    @Autowired
    private ISystemLogService systemLogService;

    @RabbitHandler
    @RabbitListener(queues = RabbitMqConfig.APPLICATION_NAME +".queueSaveLog")
    public void saveLog(SystemLogBean systemLogBean) {
        try {
            systemLogService.saveOne(systemLogBean);
        } catch (Exception e) {
            LOG.error("日志存储异常：" + e.getMessage());
        }
    }

}
