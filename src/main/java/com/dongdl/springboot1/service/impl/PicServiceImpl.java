package com.dongdl.springboot1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dongdl.springboot1.bean.PicBean;
import com.dongdl.springboot1.common.Constants;
import com.dongdl.springboot1.common.MessageException;
import com.dongdl.springboot1.dao.localdao.IPicDao;
import com.dongdl.springboot1.service.IPicService;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/7 22:55 UTC+8
 * @description
 */
@Service
public class PicServiceImpl implements IPicService {

    @Autowired
    private IPicDao picDao;

    @Override
    public ArrayList<String> getPic(Integer id) {
        ArrayList<String> nameList = Lists.newArrayList();
        String name = null;
        if (id == -1) {
//            PageHelper.startPage(1, 1);
            PageInfo pageinfo = new PageInfo<>(picDao.getPics());
            List<Map> list = pageinfo.getList();
            if (list != null && list.size() > 0) {
                for (Map m : list) {
                    name = writePic(m);
                    nameList.add(name);
                }
            }
        } else {
            Map map = picDao.getPic(id);
            name = writePic(map);
            nameList.add(name);
        }
        return nameList;
    }

    private String writePic(Map map) {
        String path = File.separator + "pic" + File.separator;
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String name = null;
        if (map != null && map.size() > 0) {
            name = String.valueOf(map.get("name"));
            byte[] bytes = Base64Utils.decodeFromString(String.valueOf(map.get("base64")));
            ImageIcon ii = new ImageIcon(bytes);
            BufferedImage bi = new BufferedImage(ii.getIconWidth(), ii.getIconHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics graphics = bi.createGraphics();
            graphics.fillRect(0, 0, ii.getIconWidth(), ii.getIconHeight());
            graphics.drawImage(ii.getImage(), 0, 0, null);
            graphics.dispose();
            try {
                ImageIO.write(bi, "jpg", new File(path + name));
            } catch (IOException e) {
                e.printStackTrace();
                throw new MessageException(e.getMessage());
            }
        }
        return name;
    }

    @Override
    public HashMap<String, Object> savePics(MultipartFile[] files) {
        HashMap<String, Object> map = Maps.newHashMap();
        ArrayList<String> successList = Lists.newArrayList();
        ArrayList<String> failList = Lists.newArrayList();
        String fileName = null;
        for (MultipartFile file : files) {
            fileName = file.getOriginalFilename();
            try {
                savePic(file);
                successList.add(fileName);
            } catch (Exception e) {
                failList.add(fileName);
                continue;
            }
        }
        map.put("success", successList);
        map.put("fail", failList);
        return map;
    }

    @Override
    public String savePic(MultipartFile file) {
        if (picDao.getPicByName(file.getOriginalFilename()) != null) {
            throw new MessageException(String.format("Pic existed : %s", file.getOriginalFilename()));
        }

        try {
            if (picDao.savePic(file.getOriginalFilename(), Base64Utils.encodeToString(file.getBytes())) != Constants.INT_ONE) {
                throw new MessageException("save pic fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MessageException(String.format("save pic fail:%s", e.getMessage()));
        }
        return file.getOriginalFilename();
    }

    @Override
    public List<PicBean> listPic(QueryWrapper queryWrapper) {
        return picDao.listPic(queryWrapper);
    }

    @Override
    public List<PicBean> selectList(QueryWrapper<PicBean> queryWrapper) {
        return picDao.selectList(queryWrapper);
    }
}