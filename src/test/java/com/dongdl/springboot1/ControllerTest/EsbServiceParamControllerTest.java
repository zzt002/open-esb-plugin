package com.dongdl.springboot1.ControllerTest;

import com.dongdl.springboot1.service.IEsbServiceParamService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class EsbServiceParamControllerTest {

    @Autowired
    IEsbServiceParamService esbServiceParamService;

    @Test
    public void test() {
        String publishId = "";
        String inParamJson = "";
        String outParamJson = "";
        esbServiceParamService.saveBath(publishId, inParamJson, outParamJson);
    }
}
