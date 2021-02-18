package com.dongdl.springboot1.dao.localdao;

import com.dongdl.springboot1.bean.TaskConfigBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/11 16:10 UTC+8
 * @description
 **/
@Repository
public interface ITaskConfigDao {

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
     * @param id
     * @return
     */
    TaskConfigBean getOneById(@Param("id")String id);

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/11 16:17 UTC+8
     * @description
     * @param taskConfigBean
     * @return
     */
    int update(TaskConfigBean taskConfigBean);

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/11 16:17 UTC+8
     * @description
     * @param taskConfigBean
     * @return 开关定时任务
     */
    int change(TaskConfigBean taskConfigBean);

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/11 16:17 UTC+8
     * @description
     * @param taskConfigBean
     * @return
     */
    int saveOne(TaskConfigBean taskConfigBean);

    int updateExecTimeByIdAndTimeSub(@Param("id")String id, @Param("limit_minute")String limitMinute);
}
