package com.dongdl.springboot1.receiver;

import com.dongdl.springboot1.config.RabbitMqConfig;
import com.dongdl.springboot1.service.IRegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/4/1 10:22 UTC+8
 * @description
 **/
@Component
public class SynSequenceReceiver {

    private static final Logger LOG = LoggerFactory.getLogger(SynSequenceReceiver.class);

    @Resource(name = "iRegisterService")
    private IRegisterService registerService;

    @RabbitListener(queues = RabbitMqConfig.APPLICATION_NAME +".queueSynSeq")
    public void synSeq() {
        try {
//            registerService.synSeq();
        } catch (Exception e) {
            LOG.error("序列同步异常：" + e.getMessage());
        }
    }
}
