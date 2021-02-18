package com.dongdl.springboot1.service.impl;

import com.dongdl.springboot1.bean.EsbIpAddrBean;
import com.dongdl.springboot1.bean.EsbResourcePassBean;
import com.dongdl.springboot1.bean.EsbSourceInfoBean;
import com.dongdl.springboot1.bean.SystemLogBean;
import com.dongdl.springboot1.common.Constants;
import com.dongdl.springboot1.common.MessageException;
import com.dongdl.springboot1.dao.pdao.IEsbResourcePassDao;
import com.dongdl.springboot1.service.*;
import com.dongdl.springboot1.util.DateUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/11 12:49 UTC+8
 * @description
 **/
@Service
public class EsbResourcePassServiceImpl implements IEsbResourcePassService {

    private static final Logger LOG = LoggerFactory.getLogger(EsbResourcePassServiceImpl.class);
    private static final String PASS_STS_Y = "Y";
    private static final String PASS_STS_U = "U";

    @Autowired
    private IEsbResourcePassDao esbResourcePassDao;
    @Autowired
    private IEsbSourceInfoService esbSourceInfoService;
    @Autowired
    private IEsbIpAddrService esbIpAddrService;
    @Autowired
    private IEsbServiceConsumerService esbServiceConsumerService;
    @Autowired
    private ISystemLogService systemLogService;

    @Override
    public List<EsbResourcePassBean> list(Integer listCount, Integer type) {
        EsbResourcePassBean esbResourcePassBean = new EsbResourcePassBean();
        esbResourcePassBean.setResourceType("B");
        esbResourcePassBean.setSts(PASS_STS_U);
        List<EsbResourcePassBean> resultList = esbResourcePassDao.list(esbResourcePassBean, listCount == null ? 1 :
                listCount);
        if (type == 1 && !resultList.isEmpty()) {
            resultList = resultList.stream()
                    .filter(entity -> entity.getImplRecord() != null && entity.getImplRecord().contains("未添加消费者IP"))
                    .collect(Collectors.toList());
        }
        return resultList;
    }

    @Override
    public List<EsbResourcePassBean> listByApplyCode(String applyCode) {
        return esbResourcePassDao.listByApplyCode(applyCode);
    }

    @Override
    public Object configByImplNum(String implNum, String implRecord, String sts) {
        EsbResourcePassBean esbResourcePassBean = new EsbResourcePassBean();
        esbResourcePassBean.setImplNum(implNum);
        if (implRecord != null) {
            esbResourcePassBean.setImplRecord(implRecord);
        }
        esbResourcePassBean.setSts(sts);
        esbResourcePassBean.setModifyUserId("2");
        esbResourcePassBean.setModifyDate(DateUtil.getCurrentDate());
        return esbResourcePassDao.configByImplNum(esbResourcePassBean);
    }

    @Override
    public Object cancelByImplNum(String implNum) {
        return esbResourcePassDao.cancelByImplNum(implNum,"U");
    }

    @Override
    public EsbResourcePassBean getOneByImplNum(String implNum) {
        return esbResourcePassDao.getOneByImplNum(implNum);
    }

    @Override
    @Transactional(transactionManager = "data3TransactionManager")
    public String openIpByImplNum(String implNum, Integer doBank) {
        EsbSourceInfoBean esbSourceInfoBean = null;
        List<EsbIpAddrBean> esbIpAddrBeanList = null;
        String implRecord = null;
        String message = null;
        if (null == doBank|| doBank == 0) {
            // 默认为0
            doBank = 0;
        } else {
            doBank = 1;
        }

        // 工单详情
        EsbResourcePassBean esbResourcePassBean = getOneByImplNum(implNum);
        if (esbResourcePassBean != null) {
            String sts = esbResourcePassBean.getSts();
            String resourceType = esbResourcePassBean.getResourceType();
            if (sts != null && "u".equals(sts.toLowerCase()) && resourceType != null && "b".equals(resourceType
                    .toLowerCase())) {
                // 服务资源
                String resId = esbResourcePassBean.getResId();
                if (resId != null) {
                    esbSourceInfoBean = esbSourceInfoService.getOneById(resId);
                    // 银行资源处理
                    if (doBank == 0) {
                        if (esbSourceInfoBean.getSourceName().contains("银行")) {
                            message = "工单" + implNum + "未配置,未配置原因[银行资源]：" + esbSourceInfoBean.getSourceName();
                            throw new MessageException(message);
                        }
                    }
                }

                // ip资源
                String customerId = esbResourcePassBean.getCustomerId();
                if (customerId != null) {
                    esbIpAddrBeanList = esbIpAddrService.list(customerId);
                }

                if (esbSourceInfoBean != null && esbIpAddrBeanList != null && esbIpAddrBeanList.size() > 0) {
                    String servcode = esbSourceInfoBean.getSourceCode();
                    StringBuilder ipStr = new StringBuilder();
                    for (EsbIpAddrBean ipBean : esbIpAddrBeanList) {
                        ipStr.append(",").append(ipBean.getIpaddr());
                    }

                    implRecord = "IP[" + ipStr.substring(1) + "]已加入白名单，接口权限已开通";
                    if (implRecord.length() > 200) {
                        implRecord = implRecord.replaceAll("\\[.*]", "");
                    }

                    configByImplNum(implNum, implRecord, PASS_STS_Y);
                    message = "工单" + implNum + "已配置," + implRecord;

                    esbServiceConsumerService.openIps(ipStr.substring(1), servcode, 1);

                    LOG.info(message);
                }
            }
        }
        return message;
    }

    @Override
    public List openIpAuto(Integer listNum, Integer range) {
        range = range == null ? 7 : range;
        List<EsbResourcePassBean>  esbResourcePassBeanList = list(listNum, 0);
        List<String> resultList = Lists.newArrayList();
        String message = null;
        if (esbResourcePassBeanList != null && esbResourcePassBeanList.size() > 0) {
            for (EsbResourcePassBean entity : esbResourcePassBeanList) {
                String implNum = entity.getImplNum();
                String implDateStr = implNum.substring(1, 9);
                if (Math.abs(DateUtil.subDayFromToday(implDateStr)) > range) {
                    continue;
                }
                SystemLogBean systemLogBean = new SystemLogBean();
                systemLogBean.setTitle("实施工单权限");
                systemLogBean.setAction("批量自动开权限");
                systemLogBean.setParam(implNum);
                try {
                    message = ((EsbResourcePassServiceImpl) AopContext.currentProxy()).openIpByImplNum(entity
                            .getImplNum(), 0);
                    systemLogBean.setOperationStatus(Constants.INT_ONE);
                } catch (Exception e) {
                    message = "工单" + entity.getImplNum() + "未配置，未配置原因：" + e.getMessage();
                    try {
                        configByImplNum(implNum, message, PASS_STS_U);
                    } catch (Exception ignore) {
                        ;
                    }
                    LOG.info(message);
                    systemLogBean.setOperationStatus(Constants.INT_ZERO);
                } finally {
                    resultList.add(message);
                    // 存日志
                    systemLogBean.setMessage(message);
                    systemLogService.saveOneByMq(systemLogBean);
                }
            }
        }
        return resultList;
    }


}
