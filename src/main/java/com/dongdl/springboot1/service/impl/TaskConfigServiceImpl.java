package com.dongdl.springboot1.service.impl;

import com.dongdl.springboot1.bean.TaskConfigBean;
import com.dongdl.springboot1.dao.localdao.ITaskConfigDao;
import com.dongdl.springboot1.service.ITaskConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/11 16:17 UTC+8
 * @description
 **/
@Service
public class TaskConfigServiceImpl implements ITaskConfigService {

    @Autowired
    private ITaskConfigDao taskConfigDao;

    @Override
    public List<TaskConfigBean> list() {
        return taskConfigDao.list();
    }

    @Override
    public Object change(String id) {
        TaskConfigBean taskConfigBean = taskConfigDao.getOneById(id);
        taskConfigBean.setOpen("1".equals(taskConfigBean.getOpen()) ? "0" : "1");
        return taskConfigDao.change(taskConfigBean);
    }

    @Override
    public Object saveOne(TaskConfigBean taskConfigBean) {
        return taskConfigDao.saveOne(taskConfigBean);
    }

    @Override
    public Object update(TaskConfigBean taskConfigBean) {
        return taskConfigDao.update(taskConfigBean);
    }

}
