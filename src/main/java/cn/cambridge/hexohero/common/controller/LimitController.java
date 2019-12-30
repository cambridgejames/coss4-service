package cn.cambridge.hexohero.common.controller;

import cn.cambridge.hexohero.basic.util.CommonResultUtil;
import cn.cambridge.hexohero.common.service.LimitService;
import cn.cambridge.hexohero.common.vo.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/limit")
public class LimitController {

    private LimitService limitService;

    @Autowired
    public void setLimitService(LimitService limitService) { this.limitService = limitService; }

    /**
     * 用户登录
     * @param user 用户信息
     * @return 登录状态（成功/失败）
     */
    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> login(@RequestBody UserDTO user) {
        if(StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty((user.getPassword()))) {
            return CommonResultUtil.returnFalse(CommonResultUtil.MessageCode.PARAMETERS_NOT_ENOUGH);
        }
        return limitService.login(user);
    }

    /**
     * 用户退出
     * @return 退出成功
     */
    @PostMapping("/logout")
    @ResponseBody
    public Map<String, Object> logout() { return limitService.logout(); }

    /**
     * 切换角色
     * @param user 要切换乘的角色ID
     * @return 切换结果（成功/失败）
     */
    @PostMapping("/runAs")
    @ResponseBody
    public Map<String, Object> runAs(@RequestBody UserDTO user) {
        if(StringUtils.isEmpty(user.getRoleId())) {
            return CommonResultUtil.returnFalse(CommonResultUtil.MessageCode.PARAMETERS_NOT_ENOUGH);
        }
        return limitService.runAs(user);
    }

}
