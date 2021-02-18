package com.dongdl.springboot1.RegisterTest;

import com.dongdl.springboot1.util.HttpUtil;
import org.junit.jupiter.api.Test;

public class HttpUtilTest {


    @Test
    public void TimeoutTest() {
        String url = "http://localhost:9977/test/test?seconds=99999999";
        String result = HttpUtil.doGet(url, 10000, 60000);
//        String state = JSONObject.parseObject(result).getString("state");
        System.out.println(result);
    }

    @Test
    public void TimeoutTest0() {
        String url = "http://localhost:9977/test/test?seconds=99999999";
        String result = HttpUtil.doGet(url, 0, 0);
//        String state = JSONObject.parseObject(result).getString("state");
        System.out.println(result);
    }

    @Test
    public void timeoutTest() {
        String url = "http://localhost:8086/test/redis2?num=5";
        String response = null;
        try {
            response = HttpUtil.doGet(url);
        } catch (Exception e) {
            ;
        }
        System.out.println(response);
    }
}
