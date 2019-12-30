package cn.cambridge.hexohero.common.service;

import cn.cambridge.hexohero.basic.bean.User;
import cn.cambridge.hexohero.basic.config.LimitConfig;
import cn.cambridge.hexohero.basic.util.CommonResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author PengJQ
 * @date 2019-12-17
 */
@Service
@Slf4j
public class CommonService {

    LimitConfig limitConfig;

    @Autowired
    public void setLimitConfig(LimitConfig limitConfig) { this.limitConfig = limitConfig; }

    public Map<String, Object> helloWorld() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        String roleId = (String) subject.getSession().getAttribute("roleId");
        String welcome = "您好，" + user.getNickname() + "！您正在以" + limitConfig.selectRoleById(roleId) + "角色登录系统";
        return CommonResultUtil.returnTrue(welcome);
    }

}
