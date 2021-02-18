package com.dongdl.springboot1.receiver;

import com.dongdl.springboot1.bean.EsbReloadFailBean;
import com.dongdl.springboot1.bean.SystemLogBean;
import com.dongdl.springboot1.common.Constants;
import com.dongdl.springboot1.common.Enums.LogActionEnum;
import com.dongdl.springboot1.config.RabbitMqConfig;
import com.dongdl.springboot1.service.IEsbReloadFailService;
import com.dongdl.springboot1.service.ISystemLogService;
import com.dongdl.springboot1.service.impl.ThirdPartyServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/13 15:50 UTC+8
 * @description
 **/
@Component
public class EsbReloadReceiver {

    @Value("${spring.application.name:esb-plugin-test}")
    String applicationName;

    private static final Logger LOG = LoggerFactory.getLogger(EsbReloadReceiver.class);

    @Autowired
    private ISystemLogService systemLogService;
    @Autowired
    private IEsbReloadFailService esbReloadFailService;

    @RabbitHandler
    @RabbitListener(queues = RabbitMqConfig.APPLICATION_NAME +".queueReload")
    public void reloadAjax(String url) {
        try {
            reload(url);
        } catch (Exception e) {
            LOG.error("接口重载异常：" + e.getMessage());
        }
    }

    private void reload(String url) {
        int i = 0;
        boolean flag = false;
        String errorMessage = null;
        do {
            if (i > 0) {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    errorMessage = e.getMessage();
                }
            }
            try {
                flag = ThirdPartyServiceImpl.reloadAjax(url);
            } catch (Exception e) {
                LOG.error("接口重载失败：" + e.getMessage());
                errorMessage = e.getMessage();
            }
            i++;
        } while(i < 3 && !flag);

        saveSysLog(url, flag, errorMessage);
    }

    private void saveSysLog(String url, boolean flag, String errorMessage) {
        String message = url;
        if (flag) {
            message += "已重载";
        } else {
            message += "重载失败";
            // 存储重载失败信息
            saveFailInfo(url, errorMessage);
        }

        // 数据库系统日志
        SystemLogBean systemLogBean = new SystemLogBean();
        systemLogBean.setTitle("RabbitMq重载接口");
        systemLogBean.setAction(LogActionEnum.RELOAD.getName());
        systemLogBean.setParam(url);
        systemLogBean.setOperationStatus(flag ? Constants.INT_ONE : Constants.INT_ZERO);
        systemLogBean.setMessage(errorMessage);
        systemLogService.saveOne(systemLogBean);

        LOG.info(message);
    }

    private void saveFailInfo(String url, String message) {
        EsbReloadFailBean esbReloadFailBean = new EsbReloadFailBean();
        esbReloadFailBean.setErrorMessage(message);
        esbReloadFailBean.setReloadUrl(url);
        esbReloadFailService.saveOrUpdate(esbReloadFailBean);
    }

}
