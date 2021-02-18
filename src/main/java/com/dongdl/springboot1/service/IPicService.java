package com.dongdl.springboot1.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dongdl.springboot1.bean.PicBean;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/4/12 2:31 UTC+8
 * @description
 */
public interface IPicService{

    HashMap<String, Object> savePics(MultipartFile[] files);

    String savePic(MultipartFile file);

    ArrayList<String> getPic(Integer id);

    List<PicBean> listPic(QueryWrapper queryWrapper);

    List<PicBean> selectList(QueryWrapper<PicBean> queryWrapper);
}
