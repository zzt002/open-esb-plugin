package com.dongdl.springboot1.util;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author <a mailto:dongdongliang13@hotmail.com>zzt002</a>
 * @date 2020/9/23 14:05 GMT+8
 * @description
 */
@Data
@Slf4j
public class MysqlDumpUtil {

    private String user;
    private String password;
    private String host;
    private String port = "3306";
    private String db;
    private String bakPath;
    private Integer maxHistory = 0;

    private static final String ENV_WINDOWS = "cmd /c ";
    private static final String ENV_LINUX = "/bin/sh -c ";
    private static final String PRE_DUMP_BAK_FROM = "mysqldump -h%s -P%s -u%s -p%s -R %s > %s";
    private static final String PRE_DUMP_BAK_TO = "mysql -h%s -P%s -u%s -p%s %s < %s";
    private static final String MYSQL_MAX_ALLOWED_PACKET = "mysql -h%s -P%s -u%s -p%s -e \"set global max_allowed_packet=%s\"";
//    private static final String PRE_GZIP_DUMP_BAK_FROM = "mysqldump -h%s -P%s -u%s -p%s -R %s | gzip > %s";
//    private static final String PRE_GZIP_DUMP_BAK_TO = "gzip -d < %s | mysql -h%s -P%s -u%s -p%s %s";
    private static final String GZIP = "gzip %s";
    private static final String GUNZIP = "gzip -dk %s";

    private static final String FILE_PATH_NAME = "%s%s_%s.sql";
    private static final String FILE_NAME_REG = "^%s_\\d+\\.sql\\.gz$";


    /**
     * 备份
     *
     * 名称规则：
     *     sql文件：db_时间戳(秒).sql
     *     gzip文件：SQL文件名.gz
     *
     * @return 0-成功
     */
    public int backup() {
        String filePath = String.format(FILE_PATH_NAME, bakPath, db, System.currentTimeMillis() / 1000);
        String exec = String.format(getEnv() + PRE_DUMP_BAK_FROM, host, port, user, password, db, filePath);
        int ret = -1;
        // mysqldump 备份
        File file = new File(bakPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        ret = runtimeExec(exec);
        if (ret == 0) {
            // gzip 压缩，gzip会自动删除源文件
            ret =  runtimeExec(String.format(GZIP, filePath));
            if (ret == 0) {
                // 删除历史
                asyncDelHistory();
            }
        }
        asyncDelFile(filePath);
        return ret;
    }

    /**
     * 还原
     *
     * @param gzfile 备份的.gz文件
     * @return 0-成功
     */
    public int revert(String gzfile) {
        File f = new File(gzfile);
        int ret = -1;
        if (f.exists()) {
            //
            String filePath = gzfile.substring(0, gzfile.length() - 3);
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
            // gzip 解压并保留源文件
            ret = runtimeExec(String.format(GUNZIP, gzfile));
            if (ret == 0) {
                if (file.exists()) {
                    long length = file.length();
                    // 配置max_allowed_packet
                    if (confMaxAllowedPacket(length) == 0) {
                        String exec = String.format(getEnv() + PRE_DUMP_BAK_TO, host, port, user, password, db, file);
                        ret = runtimeExec(exec);
                    }
                }
            }
            asyncDelFile(filePath);
        }
        return ret;
    }

    public void delHistory() {
        File bakDir = new File(bakPath);
        if (bakDir.isDirectory()) {
            File[] files = bakDir.listFiles();
            if (files != null) {
                List<File> tempFileList = Lists.newArrayList();
                for (File f : files) {
                    String fileName = f.getName();
                    if (fileName.matches(String.format(FILE_NAME_REG, db))) {
                        tempFileList.add(f);
                    }
                }
                if (!tempFileList.isEmpty()) {
                    Collections.reverse(tempFileList);
                    for (int i = maxHistory; i < tempFileList.size(); i++) {
                        tempFileList.get(i).delete();
                    }
                }
            }
        }
    }

    private void asyncDelHistory() {
        if (maxHistory == 0) {
            return;
        }
        CompletableFuture.runAsync(this::delHistory);
    }

    private int confMaxAllowedPacket(long fileLength) {
        return runtimeExec(String.format(MYSQL_MAX_ALLOWED_PACKET, host, port, user, password, fileLength));
    }

    private static void asyncDelFile(String file) {
        CompletableFuture.runAsync(() -> {
            File file1 = new File(file);
            file1.delete();
        });
    }

    private static String getEnv() {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("windows")) {
            return ENV_WINDOWS;
        } else if (osName.contains("linux")) {
            return ENV_LINUX;
        }
        return ENV_WINDOWS;
    }

    /**
     * @param cmd
     * @return '0'-success '-1'-exception '!0'-fail
     */
    private static int runtimeExec(String cmd) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmd);
            readStream(process.getInputStream(), false);
            readStream(process.getErrorStream(), true);
            return process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return -1;
    }

    private static void readStream(InputStream is, boolean printlog) {
        CompletableFuture.runAsync(() -> {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String var = null;
                while ((var = br.readLine()) != null) {
                    if (printlog) {
                        log.error(var);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
