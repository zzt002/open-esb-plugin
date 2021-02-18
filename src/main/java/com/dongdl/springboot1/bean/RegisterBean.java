package com.dongdl.springboot1.bean;

import com.alibaba.fastjson.JSONObject;
import com.dongdl.springboot1.common.Constants;
import com.dongdl.springboot1.common.MessageException;
import com.dongdl.springboot1.util.StringUtil;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/23 9:45 UTC+8
 * @description
 **/
public class RegisterBean {

    /**
     * the type of http request
     * 1 for get, 2 for post
     */
    @ApiModelProperty(name = "httpType", value = "1-get 2-post", example = "2")
    private Integer httpType;
    /**
     * 1 for save, 0 for not save
     */
    @ApiModelProperty(name = "saveLog", value = "日志保存 0-not save 1-save")
    private String saveLog;
    /**
     * the id for depart belong to 表`esb_service_tree`
     */
    @ApiModelProperty(name = "belongId", value = "esb_service_tree的id", example = "1")
    private Integer belongId;
    /**
     * 服务目录分组
     */
    @ApiModelProperty(name = "belongName", value = "服务目录分组列表名", example = "e.g.,服务测试组")
    private String belongName;
    @ApiModelProperty(name = "atomCode", value = "服务编码短")
    private String atomCode;
    @ApiModelProperty(name = "portName", value = "服务名称")
    private String portName;
    @ApiModelProperty(name = "portAddr", value = "原始请求地址")
    private String portAddr;
    @ApiModelProperty(name = "portType", value = "类型 e.g.,省 市")
    private String portType;
    @ApiModelProperty(name = "servcode", value = "服务编码长 33.开头")
    private String servcode;
    @ApiModelProperty(name = "ipStr",  value = "逗号隔开的ip白名单")
    private String ipStr;
    @ApiModelProperty(name = "vpnType",  value = "vpn类型 1-vpn1 2-vpn2", example = "2")
    private Integer vpnType;
    @ApiModelProperty(hidden = true, example = "0")
    private Integer delMark;

    public String getBelongName() {
        return belongName;
    }

    public void setBelongName(String belongName) {
        this.belongName = belongName;
    }

    public Integer getDelMark() {
        return delMark;
    }

    public void setDelMark(Integer delMark) {
        this.delMark = delMark;
    }

    public Integer getVpnType() {
        return vpnType;
    }

    public void setVpnType(Integer vpnType) {
        this.vpnType = vpnType;
    }

    public String getIpStr() {
        return ipStr;
    }

    public void setIpStr(String ipStr) {
        this.ipStr = ipStr;
    }

    public Integer getBelongId() {
        return belongId;
    }

    public void setBelongId(Integer belongId) {
        this.belongId = belongId;
    }

    public String getAtomCode() {
        return atomCode;
    }

    public void setAtomCode(String atomCode) {
        this.atomCode = atomCode;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public String getPortAddr() {
        return portAddr;
    }

    public void setPortAddr(String portAddr) {
        this.portAddr = portAddr;
    }

    public String getPortType() {
        return portType;
    }

    public void setPortType(String portType) {
        this.portType = portType;
    }

    public String getServcode() {
        return servcode;
    }

    public void setServcode(String servcode) {
        this.servcode = servcode;
    }

    public Integer getHttpType() {
        return httpType;
    }

    public String getSaveLog() {
        return saveLog;
    }

    public void setSaveLog(String saveLog) {
        this.saveLog = saveLog;
    }

    public void setHttpType(Integer httpType) {
        this.httpType = httpType;
    }

    public void valid() {
        if (httpType == null || httpType != Constants.INT_ONE && httpType != Constants.INT_TWO) {
            throw new MessageException("Wrong: httpType");
        }
        if (StringUtil.isEmpty(saveLog) || !Constants.STRING_ONE.equals(saveLog) && !Constants.STRING_ZERO.equals(saveLog)) {
            throw new MessageException("Wrong: saveLog");
        }
        if (belongId == null) {
            throw new MessageException("Wrong: belongId");
        }
        if (StringUtil.isEmpty(portName)) {
            throw new MessageException("Wrong: portName");
        }
        portName = portName.trim();
        if (StringUtil.isEmpty(portAddr)) {
            throw new MessageException("Wrong: portAddr");
        }
        portAddr = portAddr.trim();
        atomCode = atomCode.trim();
        if (StringUtil.isEmpty(atomCode)) {
            throw new MessageException("Wrong: atomCode");
        } else if (atomCode.contains(" ")) {
            throw new MessageException(String.format("ERROR：短编码[%s]包含空格", atomCode));
        }
        if (StringUtil.isEmpty(servcode)) {
            throw new MessageException("Wrong: servcode");
        }
        servcode = servcode.trim();
        if (servcode.length() > 50) {
            throw new MessageException("长度限制50个字符：" + servcode.length());
        } else if (servcode.contains(" ")) {
            throw new MessageException(String.format("ERROR：长编码[%s]包含空格", servcode));
        }
        if (StringUtil.isEmpty(portType)) {
            portType = "市";
        } else if ("市".equals(portType) && !StringUtil.isCity(servcode) ||
                "省".equals(portType) && !StringUtil.isPro(servcode)) {
            throw new MessageException("接口长编码不符合规范：市 - 33.1111.zjhz.*.SynReq; 省 - 33.1111.zj.*.SynReq");
        }  else {
            throw new MessageException("Wrong portType: " + portType);
        }
        if (vpnType == null) {
            vpnType = 2;
        } else if (vpnType != 2) {
            vpnType = 1;
        }

    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
