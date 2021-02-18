package com.dongdl.springboot1.Task;

import com.dongdl.springboot1.bean.SystemLogBean;
import com.dongdl.springboot1.bean.TaskConfigBean;
import com.dongdl.springboot1.common.Enums.LogActionEnum;
import com.dongdl.springboot1.dao.localdao.ITaskConfigDao;
import com.dongdl.springboot1.service.IMysqlDumpService;
import com.dongdl.springboot1.service.ISystemLogService;
import com.dongdl.springboot1.util.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author <a mailto:dongdongliang13@hotmail.com>zzt002</a>
 * @date 2020/9/23 17:31 GMT+8
 * @description
 */
@Component
@Slf4j
@Profile("prod")
public class MysqlSchedule {

    @Autowired
    private IMysqlDumpService mysqlDumpService;
    @Autowired
    private ISystemLogService systemLogService;
    @Autowired
    private ITaskConfigDao taskConfigDao;

    @Value("${zzt.dump.save-log:false}")
    private boolean saveLog;

    @Scheduled(cron = "0 0 0 * * ?")
    public void task() {
        TaskConfigBean taskConfigBean = taskConfigDao.getOneById("2");
        if (taskConfigBean != null && "1".equals(taskConfigBean.getOpen())) {
            int ret = -1;
            String message = null;
            try {
                ret = mysqlDumpService.backup();
            } catch (Exception e) {
                message = e.getMessage();
                log.error("数据库备份定时失败：{}", message);
            }
            if (saveLog) {
                saveTaskLog(taskConfigBean.getName(), LogActionEnum.BACKUP_MYSQL.getName(), ret == 0 ? 1 : 0, message);
            }
        }
    }

    @PostConstruct
    public void startRun() {
        task();
    }

    private void saveTaskLog(String title, String action, int operationStatus, String message) {
        // 数据库系统日志
        SystemLogBean systemLogBean = new SystemLogBean();
        systemLogBean.setTitle(title);
        systemLogBean.setIp(IPUtils.getLocalIp());
        systemLogBean.setAction(action);
        systemLogBean.setOperationStatus(operationStatus);
        systemLogBean.setMessage(message);
        systemLogService.saveOneByMq(systemLogBean);
    }
}
