package com.dongdl.springboot1.Task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author dongdl@citycloud.com.cn
 * @date 2020/4/21 13:49 UTC+8
 * @description
 **/
//@Component
public class Axis2Task {

    private static final Logger LOG = LoggerFactory.getLogger(Axis2Task.class);
    private static final String DIRECTORY_PATH = "/dev/app/apache-tomcat-7.0.77/temp/";
    private static final String FILE_REG = "^axis2-tmp-.*$";

    @Scheduled(cron = "0 0 0 1/7 * ?")
    public static void delTask() {
        try {
            delAxisTempFile(DIRECTORY_PATH, FILE_REG);
        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage());
        }
    }

    /**
     * @author dongdongliang13@hotmail.com
     * @date 2020/4/21 14:05 UTC+8
     * @description
     * @param directoryPath
     * @param fileReg
     * @throws FileNotFoundException
     */
    private static void delAxisTempFile(String directoryPath, String fileReg) throws FileNotFoundException {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new FileNotFoundException(directoryPath);
        }
        File[] files = directory.listFiles();
        int i = 0;
        if (files != null) {
            for (File f : files) {
                if (f.getName().matches(fileReg)) {
                    if (f.isFile()) {
                        if (f.delete())
                            i++;
                    } else {
                        delAxisTempFile(f.getAbsolutePath(), ".*");
                        f.delete();
                    }
                }
            }
        }
        if (i > 0) {
            LOG.info(String.format("成功删除%s个Axis2临时文件", i));
        }
    }

    public static void main(String[] args) {
        delTask();
    }
}
