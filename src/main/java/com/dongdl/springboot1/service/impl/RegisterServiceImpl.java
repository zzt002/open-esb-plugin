package com.dongdl.springboot1.service.impl;

import com.dongdl.springboot1.bean.RegisterBean;
import com.dongdl.springboot1.bean.SystemLogBean;
import com.dongdl.springboot1.common.Constants;
import com.dongdl.springboot1.common.Enums.LogActionEnum;
import com.dongdl.springboot1.common.MessageException;
import com.dongdl.springboot1.config.RabbitMqConfig;
import com.dongdl.springboot1.dao.common1A4dao.IRegisterDao;
import com.dongdl.springboot1.dao.idao.IRegisterVpn2Dao;
import com.dongdl.springboot1.dao.vpn2testdao.IRegisterVpn2TestDao;
import com.dongdl.springboot1.service.IEsbServiceConsumerService;
import com.dongdl.springboot1.service.IRegisterService;
import com.dongdl.springboot1.service.ISystemLogService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/23 10:11 UTC+8
 * @description
 **/
@Service("iRegisterService")
public class RegisterServiceImpl implements IRegisterService {

    private static final Logger LOG = LoggerFactory.getLogger(RegisterServiceImpl.class);

    @Autowired
    IEsbServiceConsumerService esbServiceConsumerService;
    @Autowired
    ISystemLogService systemLogService;
    @Autowired
    private IRegisterVpn2Dao registerVpn2Dao;
    @Autowired
    private IRegisterVpn2TestDao registerVpn2TestDao;
    @Autowired
    private AmqpTemplate rabbitTemplata;

    public synchronized RegisterBean register(RegisterBean entity, IRegisterDao iRegisterDao) {

        // 1.原子服务基础信息
        Integer atomServiceSeq = null;
        do {
            atomServiceSeq = iRegisterDao.getAtomServiceSeq();
        } while (iRegisterDao.getOneAtomService(atomServiceSeq) != null);
        LOG.info(entity.getPortName() + "[atomServiceSeq]:" + atomServiceSeq);
        if (iRegisterDao.saveAtomService(atomServiceSeq, entity.getAtomCode()) == Constants.INT_ZERO) {
            throw new MessageException("Save Fail:原子服务基础信息");
        }
        // 2.原子服务参数信息
        if (iRegisterDao.saveAtoParamConf(entity.getAtomCode()) == Constants.INT_ZERO) {
            throw new MessageException("Save Fail:原子服务参数信息");
        }
        // 3.原子服务日志输出配置
        if (iRegisterDao.saveAtoLog(entity.getAtomCode()) == Constants.INT_ZERO) {
            throw new MessageException("Save Fail:原子服务日志输出配置");
        }
        // 4.原子服务HTTP适配信息
        if (iRegisterDao.saveAdapterHttp(entity.getHttpType(), entity.getAtomCode()) == Constants.INT_ZERO) {
            throw new MessageException("Save Fail:原子服务HTTP适配信息");
        }
        // 5.原子服务HTTP适配信息参数列表
        if (iRegisterDao.saveAdapterHttpParam(entity.getAtomCode()) == Constants.INT_ZERO) {
            throw new MessageException("Save Fail:原子服务HTTP适配信息参数列表");
        }
        // 6.原子服务部署信息subapp
        if (iRegisterDao.saveServiceDeployConfOfSubapp(entity.getAtomCode()) == Constants.INT_ZERO) {
            throw new MessageException("Save Fail:原子服务部署信息subapp");
        }
        // 7.服务组件基本信息
        if (iRegisterDao.saveWorkflowAttr(entity.getAtomCode()) == Constants.INT_ZERO) {
            throw new MessageException("Save Fail:服务组件基本信息");
        }
        // 8.服务组件sdokey
        if (iRegisterDao.saveWorkflowSdokey(entity.getAtomCode()) == Constants.INT_ZERO) {
            throw new MessageException("Save Fail:服务组件sdokey");
        }
        // 9.服务组件workflow
        if (iRegisterDao.saveWorkflow(entity.getAtomCode()) == Constants.INT_ZERO) {
            throw new MessageException("Save Fail:服务组件workflow");
        }
        // 10.服务组件action
        if (iRegisterDao.saveAction(entity.getSaveLog(), entity.getAtomCode()) == Constants.INT_ZERO) {
            throw new MessageException("Save Fail:服务组件action");
        }
        // 11.服务组件action_invoke
        if (iRegisterDao.saveActionInvoke(entity.getAtomCode()) == Constants.INT_ZERO) {
            throw new MessageException("Save Fail:服务组件action_invoke");
        }
        // 12.服务组件sca_atom_sdokey
        if (iRegisterDao.saveScaAtomSdokey(entity.getAtomCode()) == Constants.INT_ZERO) {
            throw new MessageException("Save Fail:服务组件sca_atom_sdokey");
        }
        // 13.服务基本信息
        Integer inboundMainSeq = null;
        do {
            inboundMainSeq = iRegisterDao.getInboundMainSeq();
        } while (iRegisterDao.getOneInboundMainId(inboundMainSeq) != null);
        LOG.info(entity.getPortName() + "[inboundMainSeq]:" + inboundMainSeq);
        if (iRegisterDao.saveInboundMain(inboundMainSeq, entity.getAtomCode()) == Constants.INT_ZERO) {
            throw new MessageException("Save Fail:服务基本信息");
        }
        // 14.服务http信息
        if (iRegisterDao.saveInboundHttp(entity.getHttpType(), entity.getAtomCode()) == Constants.INT_ZERO) {
            throw new MessageException("Save Fail:服务http信息");
        }
        // 15.服务部署信息app
        if (iRegisterDao.saveServiceDeployConfOfApp(entity.getAtomCode()) == Constants.INT_ZERO) {
            throw new MessageException("Save Fail:服务部署信息app");
        }
        // 18.服务目录树
        if (iRegisterDao.saveServiceTree(entity.getBelongId(), entity.getAtomCode()) == Constants.INT_ZERO) {
            throw new MessageException("Save Fail:服务目录树");
        }
        // 19.服务和原子服务对应关系
        if (iRegisterDao.saveServiceAtomService(entity.getAtomCode()) == Constants.INT_ZERO) {
            throw new MessageException("Save Fail:服务和原子服务对应关系");
        }

        // 20.配置已标记
        if (iRegisterDao.updatePostConf(entity.getAtomCode()) == Constants.INT_ZERO) {
            throw new MessageException("Save Fail:配置已标记");
        }

        if (entity.getVpnType() == 2) {
            // ip权限
            try {
                esbServiceConsumerService.openIps(entity.getIpStr(), entity.getServcode(), Constants.INT_ONE);
            } catch (Exception ignored) {
                ;
            }
            // vpn2 正式环境 表esb_atomservice和esb_inbound_main 序列 同步 测试环境
            rabbitTemplata.convertAndSend(RabbitMqConfig.APPLICATION_NAME +".synSeq", null, "");
        }

        return entity;
    }

    @Override
    public Map publishConfigured(Integer httpType, String saveLog, Integer belongId, String ipStr, Integer limit,
                                 IRegisterDao iRegisterDao) {
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
            if (iRegisterDao.getOneTreeById(belongId) == null) {
                throw new MessageException("ERROR:not exist (belongId:" + belongId + ")");
            }
        }
        // todo：批量注册 服务目录二级菜单校验

        limit = limit == null ? 1 : limit;

        List<String> successList = Lists.newArrayList();
        List<String> failList = Lists.newArrayList();
        Map<String, Object> map = Maps.newHashMap();
        List<RegisterBean> list = iRegisterDao.getPostConfList();
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
                bean.valid();

                SystemLogBean systemLogBean = new SystemLogBean();
                systemLogBean.setTitle("批量注册接口");
                systemLogBean.setAction(LogActionEnum.ADD.getName());
                systemLogBean.setParam(bean.toString());
                String message = null;
                try {
                    if (iRegisterDao.getCountAtomServiceByCodeOrName(bean.getAtomCode(), bean.getPortName()) > 0) {
                        throw new MessageException("EXIST: ATOM_CODE(" + bean.getAtomCode() + "),SERVCODE(" + bean.getServcode() + ")");
                    }
                    if (iRegisterDao.getCountInboundMainByServcode(bean.getServcode()) > 0) {
                        throw new MessageException("EXIST: SERVCODE(" + bean.getServcode() + ")");
                    }
                    ((RegisterServiceImpl) AopContext.currentProxy()).register(bean, iRegisterDao);
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
        map.put("success", successList.toString());
        map.put("fail", failList.toString());
        return map;
    }

    @Override
    public RegisterBean publishUnConfig(RegisterBean entity, IRegisterDao iRegisterDao) {
        entity.valid();
        // 服务目录二级菜单校验
        Integer belongId = iRegisterDao.getBelongIdByBelongName(entity.getBelongName());
        if (null == belongId) {
            throw new MessageException("ERROR:not exist (服务目录2级菜单：" + entity.getBelongName() + ")");
        } else {
            entity.setBelongId(belongId);
        }
        // 校验原子服务接口是否已注册
        if (iRegisterDao.getCountAtomServiceByCodeOrName(entity.getAtomCode(), entity.getPortName()) > 0) {
            throw new MessageException("EXIST: ATOM_CODE(" + entity.getAtomCode() + "),SERVCODE(" + entity.getServcode() + ")");
        }
        //
        if (iRegisterDao.getCountInboundMainByServcode(entity.getServcode()) > 0) {
            throw new MessageException("EXIST: SERVCODE(" + entity.getServcode() + ")");
        }

        if (iRegisterDao.savePostConf(entity) == Constants.INT_ZERO) {
            throw new MessageException("SAVE FAIL:HTTP_CONF 配置");
        }
        register(entity, iRegisterDao);

        return entity;
    }

    @Override
    public List<Map> listTreeDep(String name, IRegisterDao iRegisterDao) {
        return iRegisterDao.listTreeDep("%" + name + "%");
    }

    @Override
    public String delRegisterByAtomCode(String atomCode, IRegisterDao iRegisterDao) {

        // 1.原子服务基础信息数据校验
        Integer atomId = null;
        try {
            atomId = iRegisterDao.getAtomServiceIdByServiceCode(atomCode);
        } catch (TooManyResultsException e) {
            throw new MessageException("存在重复的id:[esb_atomservice:id] " + atomCode);
        }
        if (atomId == null) {
            throw new MessageException("NOT Exist：" + atomCode);
        }
        // 13.服务基本信息数据校验
        Integer esbServiceId = null;
        try {
            esbServiceId = iRegisterDao.getEsbServiceIdByScaid(atomCode);
        } catch (TooManyResultsException e) {
            throw new MessageException("存在重复的id:[esb_inbound_main:esbserviceid] " + atomCode);
        }
        String atomCodeReg = "^" + atomCode + "0?[[:digit:]]$";

        // 19.删除服务和原子服务对应关系
        iRegisterDao.delServiceAtomServiceByEsbserviceidAndAtomId(esbServiceId, atomId);
        // 18.删除服务目录树
        iRegisterDao.delServiceTreeByCode(esbServiceId);
        // 删除原子服务部署信息
        iRegisterDao.delServiceDeployConf(atomId, esbServiceId);
        // 14.删除服务http信息
        iRegisterDao.delInboundHttpByEsbserviceid(esbServiceId);
        // 13.删除服务基本信息
        iRegisterDao.delInboundMainByEsbserviceid(esbServiceId);
        // 12.删除服务组件sca_atom_sdokey
        iRegisterDao.delScaAtomSdokeyByActionid(atomCodeReg);
        // 11.删除服务组件action_invoke
        iRegisterDao.delActionInvokeByServiceId(atomId);
        // 10.删除服务组件action
        iRegisterDao.delActionByActionid(atomCodeReg);
        // 9.删除服务组件workflow
        iRegisterDao.delWorkflowByScaid(atomCode);
        // 8.删除服务组件sdokey
        iRegisterDao.delWorkflowSdokeyByScaid(atomCode);
        // 7.删除服务组件基本信息
        iRegisterDao.delWorkflowAttrByScaid(atomCode);
        // 5.删除原子服务HTTP适配信息参数列表
        iRegisterDao.delAdapterHttpParamByServiceId(atomId);
        // 4.删除原子服务HTTP适配信息
        iRegisterDao.delAdapterHttpById(atomId);
        // 3.删除原子服务日志输出配置
        iRegisterDao.delAtoLogByServiceId(atomId);
        // 2.删除原子服务参数信息
        iRegisterDao.delAtoParamConfByServiceId(atomId);
        // 1.删除原子服务基础信息
        iRegisterDao.delAtomServiceById(atomId);

        // 重置http_conf
        iRegisterDao.resetHttpConfByAtomCode(atomCode);

        return atomCode;
    }

    @Override
    public Map delListConfigured(IRegisterDao iRegisterDao) {
        List<String> successList = Lists.newArrayList();
        List<String> failList = Lists.newArrayList();
        Map<String, Object> map = Maps.newHashMap();
        List<RegisterBean> list = iRegisterDao.getPostConfDelList();
        if (list != null && list.size() > 0) {
            for (RegisterBean bean : list) {
                SystemLogBean systemLogBean = new SystemLogBean();
                systemLogBean.setTitle("批量删除接口");
                systemLogBean.setAction(LogActionEnum.DELETE.getName());
                systemLogBean.setParam(bean.toString());
                String message = null;
                try {
                    ((RegisterServiceImpl) AopContext.currentProxy()).delRegisterByAtomCode(bean.getAtomCode(), iRegisterDao);
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
        map.put("success", successList.toString());
        map.put("fail", failList.toString());
        return map;
    }

    @Override
    public void synSeq() {
        // 正式环境seq_atomservice序列
        Integer vpn2AtomSeq = registerVpn2Dao.getAtomServiceSeq();

        Integer vpn2TestAtomSeq = 0;
        do {
            // 测试环境seq_atomservice序列
            vpn2TestAtomSeq = registerVpn2TestDao.getAtomServiceSeq();
        } while (vpn2AtomSeq > vpn2TestAtomSeq);
        LOG.info("更新当前测试环境序列seq_esb_atomservice为:" + vpn2TestAtomSeq);

        // 正式环境seq_inbound_main序列
        Integer vpn2InboundMainSeq = registerVpn2Dao.getInboundMainSeq();

        Integer vpn2TestInboundMainSeq = 0;
        do {
            // 测试环境seq_inbound_main序列
            vpn2TestInboundMainSeq = registerVpn2TestDao.getInboundMainSeq();
        } while (vpn2InboundMainSeq > vpn2TestInboundMainSeq);
        LOG.info("更新当前测试环境序列seq_esb_inbound_main为:" + vpn2TestInboundMainSeq);
    }

    @Override
    public String getBelongIdByBelongName(String provideDept) {
        return null;
    }
}
