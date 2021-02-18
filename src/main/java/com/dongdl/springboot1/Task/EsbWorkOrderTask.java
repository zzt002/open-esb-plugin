package com.dongdl.springboot1.Task;

import com.dongdl.springboot1.bean.SystemLogBean;
import com.dongdl.springboot1.bean.TaskConfigBean;
import com.dongdl.springboot1.common.Constants;
import com.dongdl.springboot1.common.Enums.LogActionEnum;
import com.dongdl.springboot1.dao.localdao.ITaskConfigDao;
import com.dongdl.springboot1.service.IEsbResourcePassService;
import com.dongdl.springboot1.service.ISystemLogService;
import com.dongdl.springboot1.util.IPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/11 0:45 UTC+8
 * @description
 */
@Component
@Profile("prod")
public class EsbWorkOrderTask {

    @Autowired
    private IEsbResourcePassService esbResourcePassService;
    @Autowired
    private ITaskConfigDao taskConfigDao;
    @Autowired
    private ISystemLogService systemLogService;

    @Scheduled(cron = "0 0 11,15,17 ? * MON-FRI")
    public void task1() {
        // 查找: 序号为1 && 最近定时执行时间大于30分
        int result = 0;
        try{
            // 数据库update乐观锁 解决分布式定时任务
            result = taskConfigDao.updateExecTimeByIdAndTimeSub("1", "30");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result == 1) {
            TaskConfigBean taskConfigBean = taskConfigDao.getOneById("1");
            if (taskConfigBean != null && "1".equals(taskConfigBean.getOpen())) {
                saveTaskLog(taskConfigBean.getName());
                Integer range = taskConfigBean.getLimit();
                range = range == null ? 1 : range;
                esbResourcePassService.openIpAuto(30, range);
            }
        }
    }

    private void saveTaskLog(String title) {
        // 数据库系统日志
        SystemLogBean systemLogBean = new SystemLogBean();
        systemLogBean.setTitle(title);
        systemLogBean.setIp(IPUtils.getLocalIp());
        systemLogBean.setAction(LogActionEnum.OpenAu.getName());
        systemLogBean.setOperationStatus(Constants.INT_ONE);
        systemLogService.saveOneByMq(systemLogBean);
    }
}
