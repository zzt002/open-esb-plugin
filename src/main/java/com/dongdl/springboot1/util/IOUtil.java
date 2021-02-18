package com.dongdl.springboot1.util;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/4/10 14:59 UTC+8
 * @description
 **/
public class IOUtil {

    /**
     * @param file
     * @param response
     * @throws IOException
     * @author dongdongliang13@hotmail.com
     * @date 2020/4/10 17:19 UTC+8
     * @description
     */
    public static void downloadFile(File file, HttpServletResponse response) {
        try {
            if (!file.exists()) {
                throw new FileNotFoundException(String.format("File not exist : %s", file.getName()));
            }
            response.setHeader("Content-Disposition", "attachment;filename=%s" + new String(file.getName().getBytes("gb2312"), "ISO8859-1"));
            FileInputStream fis = new FileInputStream(file);
//        response.setContentType("application/x-xls");
            OutputStream os = response.getOutputStream();
            byte[] buf = new byte[1024];
            int i = -1;
            while ((i = fis.read(buf)) != -1) {
                os.write(buf, 0, i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param fileName
     * @param filePath
     * @param response
     * @author dongdongliang13@hotmail.com
     * @date 2020/4/10 17:49 UTC+8
     * @description
     */
    public static void downloadFile(String fileName, String filePath, HttpServletResponse response) {
        try {
            response.setHeader("Content-Disposition", "attachment;filename=%s" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
//            InputStream is = ClassLoader.getSystemResourceAsStream(filePath);
            InputStream is = IOUtil.class.getClassLoader().getResourceAsStream(filePath);
            OutputStream os = response.getOutputStream();
            byte[] buf = new byte[1024];
            int i = -1;
            while ((i = is.read(buf)) != -1) {
                os.write(buf, 0, i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
