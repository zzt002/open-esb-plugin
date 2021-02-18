package com.dongdl.springboot1.service;

import com.dongdl.springboot1.bean.TaskConfigBean;

import java.util.List;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/11 16:16 UTC+8
 * @description
 **/
public interface ITaskConfigService {

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/11 16:17 UTC+8
     * @description
     * @return
     */
    List<TaskConfigBean> list();

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/11 16:17 UTC+8
     * @description
     * @param taskConfigBean
     * @return
     */
    Object update(TaskConfigBean taskConfigBean);

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/11 16:17 UTC+8
     * @description
     * @param taskConfigBean
     * @return
     */
    Object saveOne(TaskConfigBean taskConfigBean);

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/11 16:17 UTC+8
     * @description
     * @param id
     * @return 开关定时任务
     */
    Object change(String id);

}
