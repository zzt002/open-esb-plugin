package com.dongdl.springboot1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dongdl.springboot1.bean.EsbDeptBean;
import com.dongdl.springboot1.dao.idao.IEsbDeptDao;
import com.dongdl.springboot1.service.IEsbDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EsbDeptServiceImpl extends ServiceImpl<IEsbDeptDao, EsbDeptBean> implements IEsbDeptService {

    @Autowired
    private IEsbDeptDao esbDeptDao;

    @Override
    public EsbDeptBean getOneByName(String name) {
        QueryWrapper<EsbDeptBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("NAME", name).select("CODE", "NAME");
        return esbDeptDao.selectOne(queryWrapper);
    }
}
