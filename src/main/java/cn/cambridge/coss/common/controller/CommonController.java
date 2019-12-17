package cn.cambridge.coss.common.controller;

import cn.cambridge.coss.basic.util.CommonResultUtil;
import cn.cambridge.coss.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("common")
public class CommonController {

    @Autowired
    private CommonService commonService;

    @GetMapping("/hello")
    @ResponseBody
    public Map<String, Object> hello() {
        return CommonResultUtil.returnFalse(CommonResultUtil.MessageCode.OTHER_ERROR);
    }

    /**
     * 获取目录结构
     * @return
     */
    @GetMapping("directory")
    @ResponseBody
    public Map<String, Object> getDirectory() { return commonService.getDirectory(); }

}
