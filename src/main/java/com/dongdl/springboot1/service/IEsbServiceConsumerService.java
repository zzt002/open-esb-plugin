package com.dongdl.springboot1.service;

import com.dongdl.springboot1.bean.EsbServiceConsumerBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/8 1:35 UTC+8
 * @description
 */
public interface IEsbServiceConsumerService {

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/8 1:35 UTC+8
     * @description
     */
    Object saveOne(EsbServiceConsumerBean esbServiceConsumerBean);

    /**
     * @description
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/8 1:54 UTC+8
     * @param id
     * @param status
     * @return
     */
    Object updateOneById(String id, Integer status);

    /**
     * @description
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/8 1:59 UTC+8
     * @param ipId
     * @param servcode
     * @return
     */
    EsbServiceConsumerBean getOneByIpIdAndServcode(String ipId, String servcode);

    /**
     * @description  批量开启ip权限
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/8 2:27 UTC+8
     * @param ipStr
     * @param serviceStr
     * @return 返回信息
     */
    Map openIps(String ipStr, String serviceStr, Integer status);

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/4/8 9:27 UTC+8
     * @description
     * @param ip
     * @return
     */
    List<EsbServiceConsumerBean> listHistoryIp(String ip);

    /**
     * @author <a href="mailto:dongdongliang13@hotmail.com">zzt002</a>
     * @date 2020/6/29 10:50 GMT+8
     * @description
     * @param file
     * @param status
     * @return
     */
    Map openIpsByExcel(MultipartFile file, Integer status);

    /**
     * @author <a href="mailto:dongdongliang13@hotmail.com">zzt002</a>
     * @date 2020/6/29 16:56 GMT+8
     * @description 
     * @param response
     */
    void getExcel(HttpServletResponse response);

    /**
     *
     * @param oldServcode
     * @param newServcode
     * @return
     */
    int updateServcode(String oldServcode, String newServcode);
}
