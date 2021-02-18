package com.dongdl.springboot1.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dongdl.springboot1.bean.EsbReloadFailBean;
import com.dongdl.springboot1.dao.localdao.IEsbReloadFailDao;
import com.dongdl.springboot1.service.IEsbReloadFailService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EsbReloadFailServiceImpl extends ServiceImpl<IEsbReloadFailDao, EsbReloadFailBean> implements IEsbReloadFailService {

    @Autowired
    private IEsbReloadFailDao esbReloadFailDao;

    @Override
    public String reload(Integer id) {
        EsbReloadFailBean esbReloadFailBean = esbReloadFailDao.selectById(id);
        if (esbReloadFailBean != null) {
            boolean flag =false;
            String message = null;
            try {
                flag = ThirdPartyServiceImpl.reloadAjax(esbReloadFailBean.getReloadUrl());
            } catch (Exception e) {
                message = e.getMessage();
            }
            if (flag) {
                // 重载成功删除
                esbReloadFailDao.deleteById(id);
                return esbReloadFailBean.getReloadUrl() + "已重载";
            } else {
                // 重载失败修改
                esbReloadFailBean.setRetryCount(esbReloadFailBean.getRetryCount() + 1);
                esbReloadFailBean.setErrorMessage(message);
                esbReloadFailDao.updateById(esbReloadFailBean);
                return esbReloadFailBean.getReloadUrl() + "重载失败";
            }
        }
        return null;
    }

    @Override
    public List reloadAll() {
        List<EsbReloadFailBean> lists = esbReloadFailDao.selectList(null);
        List resultList = Lists.newArrayList();
        lists.forEach(bean -> {
            resultList.add(reload(bean.getId()));
        });
        return resultList;
    }

    @Override
    public EsbReloadFailBean getOneByUrl(String url) {
        return esbReloadFailDao.getOneByUrl(url);
    }

    @Override
    public boolean saveOrUpdate(EsbReloadFailBean entity) {
        EsbReloadFailBean bean = esbReloadFailDao.getOneByUrl(entity.getReloadUrl());
        if (bean != null) {
            bean.setRetryCount(bean.getRetryCount() + 1);
            bean.setErrorMessage(entity.getErrorMessage());
        } else {
            bean = entity;
        }
        return super.saveOrUpdate(bean);
    }
}
