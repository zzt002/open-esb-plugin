package com.dongdl.springboot1.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a mailto:dongdongliang13@hotmail.com>5xia02</a>
 * @date 2020/7/29 11:03 GMT+8
 * @description
 */
public class StringUtil {

    public static boolean isEmpty(String var1) {
        return null == var1 || var1.trim().length() == 0;
    }

    public static boolean notEmpty(String var1) {
        return null != var1 && var1.trim().length() > 0;
    }

    /**
     * 电子政务办下面
     * 是否省接口编码
     *
     * @param servCode
     * @return
     */
    public static boolean isPro(String servCode) {
        return servCode.matches("^33\\.1111\\.zj\\..+\\.SynReq$");
    }

    /**
     * 电子政务办下面
     * 是否市接口编码
     *
     * @param servCode
     * @return
     */
    public static boolean isCity(String servCode) {
        return servCode.matches("^33\\.1111\\.zjhz\\..+\\.SynReq$");
    }

    /**
     * 正则规则匹配
     * 从长编码截取段编码
     *
     * @param servCode
     * @return
     */
    public static String getScaidByReg(String servCode) {
        Matcher matcher = Pattern.compile("[^.]+(?=\\.SynReq)").matcher(servCode);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
}
