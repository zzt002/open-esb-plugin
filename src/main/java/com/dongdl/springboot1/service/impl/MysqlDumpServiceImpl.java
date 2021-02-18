package com.dongdl.springboot1.service.impl;

import com.dongdl.springboot1.service.IMysqlDumpService;
import com.dongdl.springboot1.util.MysqlDumpUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * @author <a mailto:dongdongliang13@hotmail.com>zzt002</a>
 * @date 2020/9/23 14:05 GMT+8
 * @description
 */
@Service
@ConfigurationProperties(prefix = "zzt.dump")
public class MysqlDumpServiceImpl extends MysqlDumpUtil implements IMysqlDumpService {
}
