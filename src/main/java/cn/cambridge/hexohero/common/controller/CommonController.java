package cn.cambridge.hexohero.common.controller;

import cn.cambridge.hexohero.basic.util.CommonResultUtil;
import cn.cambridge.hexohero.common.service.CommonService;
import cn.cambridge.hexohero.common.vo.UserDTO;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("common")
public class CommonController {

    private CommonService commonService;

    @Autowired
    public void setCommonService(CommonService commonService) {
        this.commonService = commonService;
    }

    @GetMapping("/helloWorld")
    @ResponseBody
    public Map<String, Object> helloWorld() {
        UserDTO user = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        if(user == null) {
            return CommonResultUtil.returnFalse(CommonResultUtil.MessageCode.TOKEN_NOT_FOUND);
        }
        return commonService.helloWorld(user.getUsername());
    }

}
