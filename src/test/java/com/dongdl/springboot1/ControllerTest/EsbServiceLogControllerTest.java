package com.dongdl.springboot1.ControllerTest;

import com.dongdl.springboot1.service.IEsbServiceLogService;
import com.dongdl.springboot1.service.IEsbServiceParamService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class EsbServiceLogControllerTest {

    @Autowired
    IEsbServiceLogService esbServiceLogService;

    @Test
    public void test() {
        String publishId = "";
        esbServiceLogService.saveBath(publishId);
    }
}
