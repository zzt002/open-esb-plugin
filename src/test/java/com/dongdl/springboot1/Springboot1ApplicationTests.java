package com.dongdl.springboot1;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class Springboot1ApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(5555);
    }



    @Test
    public void bak() throws IOException, InterruptedException {
        String cmd = "cmd /c mysqldump -uroot -p123456 -B test55 > d:\\ddl\\bak\\dump\\s_test55.sql";
        Process p = Runtime.getRuntime().exec(cmd);
        System.out.println(p.waitFor());
        System.out.println(JSONObject.toJSONString(p));
    }

    @Test
    public void set() throws IOException, InterruptedException {
        String cmd = "cmd /c mysql -uroot -p123456 < d:\\ddl\\bak\\dump\\s_test55.sql";
        Process p = Runtime.getRuntime().exec(cmd);
        System.out.println(p.waitFor());
        System.out.println(JSONObject.toJSONString(p));
    }

}
