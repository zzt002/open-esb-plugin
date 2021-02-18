package com.dongdl.springboot1.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/9 19:30 UTC+8
 * @description
 */
public interface IThirdPartyService {

    /**
     * @param esbServiceIdStr
     * @description esb系统权限开启后的重载功能
     * @author dongdongliang13@hotmail.com
     * @date 2020/3/9 19:31 UTC+8
     * @param esbServiceIdStr
     * @param esbServiceCodeStr
     * @param backstage 后台处理 1:是  其他:否
     */
    String reloadEsb(String esbServiceIdStr, String esbServiceCodeStr, Integer backstage);

    /**
     * @author dongdongliang13@hotmail.com
     * @date  2020-3-10 11:51:06 UTC+8
     * @description 查看ip的归属地
     * @param ip
     * @return
     */
    String getBaiDuIp(String ip);

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/4/10 11:28 UTC+8
     * @description
     * @param vpnType
     * @param file
     * @param backstage
     * @return
     */
    Map reloadByExcel(Integer vpnType, MultipartFile file, Integer backstage);

    void getReloadExcel(HttpServletResponse response);

    /**
     * 返回处理后的健康码excel文件
     *
     * @param response
     * @param file excel
     */
    void healthCodeListExcel(HttpServletResponse response, MultipartFile file);

    void getHealthCodeListExcel(HttpServletResponse response);
}
