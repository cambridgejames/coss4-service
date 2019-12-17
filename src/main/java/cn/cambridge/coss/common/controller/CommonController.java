package cn.cambridge.coss.common.controller;

import cn.cambridge.coss.basic.util.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("common")
public class CommonController {

    @GetMapping("/hello")
    @ResponseBody
    public Map<String, Object> hello() {
        return CommonResult.returnFalse(CommonResult.MessageCode.ERROR1);
    }

}
