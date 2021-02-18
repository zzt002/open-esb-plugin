package com.dongdl.springboot1.StringUtilTest;

import com.dongdl.springboot1.common.MessageException;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegTest {

    @Test
    public void regTest() {
        String str = "33.1111.zjhz.smzx.basicguotucltrlinfo.SynReq";

        String reg = "[^.]+(?=\\.SynReq)";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            System.out.println(matcher.group());
        }
    }

    @Test
    public void servCode() {
        String portType = "市";
        String servcode = "33.1111.zjhz.123.SynReq";
        if ("市".equals(portType) && !servcode.matches("^33\\.1111\\.zjhz\\..+\\.SynReq$") ||
                "省".equals(portType) && !servcode.matches("^33\\.1111\\.zj\\..+\\.SynReq$")) {
            throw new MessageException("接口长编码不符合规范：市 - 33.1111.zjhz.*.SynReq; 省 - 33.1111.zj.*.SynReq");
        }
    }
}
