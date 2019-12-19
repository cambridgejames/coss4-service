package cn.cambridge.coss.common.service;

import cn.cambridge.coss.basic.util.CommonResultUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonServiceTest {

    @Autowired
    private CommonService commonService;

    @Test(timeout = 1000)
    public void helloWorld() {
        Assert.assertEquals(CommonResultUtil.returnTrue("Hello World!"), commonService.helloWorld());
    }
}