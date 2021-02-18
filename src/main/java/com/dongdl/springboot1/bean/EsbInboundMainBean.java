package com.dongdl.springboot1.bean;
/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/7 22:36 UTC+8
 * @description 原子服务
 */
public class EsbInboundMainBean {

    private String esbServiceId;
    private String serviceCode;
    private String serviceName;

    public String getEsbServiceId() {
        return esbServiceId;
    }

    public void setEsbServiceId(String esbServiceId) {
        this.esbServiceId = esbServiceId;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
