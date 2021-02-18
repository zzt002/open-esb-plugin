package com.dongdl.springboot1.service.impl;

import com.dongdl.springboot1.bean.RegisterBean;
import com.dongdl.springboot1.bean.SystemLogBean;
import com.dongdl.springboot1.common.Constants;
import com.dongdl.springboot1.common.Enums.LogActionEnum;
import com.dongdl.springboot1.common.MessageException;
import com.dongdl.springboot1.dao.common1A4dao.IRegisterDao;
import com.dongdl.springboot1.dao.vpn1dao.IRegisterVpn1Dao;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/23 10:11 UTC+8
 * @description
 **/
@Service("registerVpn1ServiceImpl")
public class RegisterVpn1ServiceImpl extends RegisterServiceImpl {

    @Autowired
    IRegisterVpn1Dao iRegisterDao;

    @Override
    @Transactional(transactionManager = "data4TransactionManager")
    public RegisterBean register(RegisterBean entity, IRegisterDao iRegisterDao) {
        iRegisterDao = null;
        return super.register(entity, this.iRegisterDao);
    }

    @Override
    public Map publishConfigured(Integer httpType, String saveLog, Integer belongId, String ipStr, Integer limit, IRegisterDao iRegisterDao) {
        iRegisterDao = null;
        if (httpType == null || httpType != Constants.INT_ONE && httpType != Constants.INT_TWO) {
            throw new MessageException("Wrong: httpType");
        }
        if (saveLog == null || !Constants.STRING_ONE.equals(saveLog) && !Constants.STRING_ZERO.equals(saveLog)) {
            throw new MessageException("Wrong: saveLog");
        }
        if (belongId == null) {
            throw new MessageException("Wrong: belongId");
        } else {
            // 部门校验
            if (this.iRegisterDao.getOneTreeById(belongId) == null) {
                throw new MessageException("ERROR:not exist (belongId:" + belongId + ")");
            }
        }
        limit = limit == null ? 1 :limit;

        List<String> successList = Lists.newArrayList();
        List<String> failList = Lists.newArrayList();
        Map<String, Object> map = Maps.newHashMap();
        List<RegisterBean> list = this.iRegisterDao.getPostConfList();
        int i = 0;
        if (list != null && list.size() > 0) {
            for (RegisterBean bean : list) {
                i++;
                if (i > limit) {
                    break;
                }
                bean.setHttpType(httpType);
                bean.setSaveLog(saveLog);
                bean.setBelongId(belongId);
                bean.setIpStr(ipStr);
                bean.setVpnType(Constants.INT_ONE);
                bean.valid();

                SystemLogBean systemLogBean = new SystemLogBean();
                systemLogBean.setTitle("vpn1批量注册接口");
                systemLogBean.setAction(LogActionEnum.ADD.getName());
                systemLogBean.setParam(bean.toString());
                String message = null;
                try {
                    if (this.iRegisterDao.getCountAtomServiceByCodeOrName(bean.getAtomCode(), bean.getPortName()) > 0) {
                        throw new MessageException("EXIST: ATOM_CODE(" + bean.getAtomCode() + "),PORT_NAME(" + bean
                                .getPortName() + ")");
                    }
                    if (this.iRegisterDao.getCountInboundMainByServcode(bean.getServcode()) > 0) {
                        throw new MessageException("EXIST: SERVCODE(" + bean.getServcode() + ")");
                    }
                    ((RegisterVpn1ServiceImpl) AopContext.currentProxy()).register(bean, this.iRegisterDao);
                    successList.add(bean.getPortName());
                    systemLogBean.setOperationStatus(Constants.INT_ONE);
                } catch (Exception e) {
                    failList.add(bean.getPortName());
                    systemLogBean.setOperationStatus(Constants.INT_ZERO);
                    message = e.getMessage();
                } finally {
                    systemLogBean.setMessage(message);
                    systemLogService.saveOneByMq(systemLogBean);
                }
            }
        }
        map.put( "success", successList.toString());
        map.put( "fail", failList.toString());
        return map;
    }

    @Override
    @Transactional(transactionManager = "data4TransactionManager")
    public RegisterBean publishUnConfig(RegisterBean entity, IRegisterDao iRegisterDao) {
        iRegisterDao = null;
        return super.publishUnConfig(entity, this.iRegisterDao);
    }

    @Override
    @Transactional(transactionManager = "data4TransactionManager")
    public String delRegisterByAtomCode(String atomCode, IRegisterDao iRegisterDao) {
        iRegisterDao = null;
        super.delRegisterByAtomCode(atomCode, this.iRegisterDao);
        // 物理删除conf配置表
        this.iRegisterDao.delPostConfByAtomCode(atomCode);
        return atomCode;
    }

    @Override
    public List<Map> listTreeDep(String name, IRegisterDao iRegisterDao) {
        iRegisterDao = null;
        return super.listTreeDep(name, this.iRegisterDao);
    }

    @Override
    public Map delListConfigured(IRegisterDao iRegisterDao) {
        iRegisterDao = null;
        List<String> successList = Lists.newArrayList();
        List<String> failList = Lists.newArrayList();
        Map<String, Object> map = Maps.newHashMap();
        List<RegisterBean> list = this.iRegisterDao.getPostConfDelList();
        if (list != null && list.size() > 0) {
            for (RegisterBean bean : list) {
                SystemLogBean systemLogBean = new SystemLogBean();
                systemLogBean.setTitle("vpn1批量删除接口");
                systemLogBean.setAction(LogActionEnum.DELETE.getName());
                systemLogBean.setParam(bean.toString());
                String message = null;
                try {
                    ((RegisterVpn1ServiceImpl) AopContext.currentProxy()).delRegisterByAtomCode(bean.getAtomCode(),
                            this.iRegisterDao);
                    successList.add(bean.getPortName());
                    systemLogBean.setOperationStatus(Constants.INT_ONE);
                } catch (Exception e) {
                    failList.add(bean.getPortName());
                    systemLogBean.setOperationStatus(Constants.INT_ZERO);
                    message = e.getMessage();
                } finally {
                    systemLogBean.setMessage(message);
                    systemLogService.saveOneByMq(systemLogBean);
                }
            }
        }
        map.put( "success", successList.toString());
        map.put( "fail", failList.toString());
        return map;
    }
}
