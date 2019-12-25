package cn.cambridge.hexohero.common.service;

import cn.cambridge.hexohero.basic.config.UserConfig;
import cn.cambridge.hexohero.basic.util.CommonResultUtil;
import cn.cambridge.hexohero.common.vo.UserDTO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LoginService {
    private Logger logger = LoggerFactory.getLogger(LoginService.class);

    private UserConfig userConfig;

    @Autowired
    public void setUserConfig(UserConfig userConfig) { this.userConfig = userConfig; }

    /**
     * 用户登录
     * @param user 用户名和密码信息
     * @return 登录状态（成功/失败）
     */
    public Map<String, Object> login(UserDTO user) {
        if(!userConfig.getUsername().equals(user.getUsername()) || !userConfig.getPassword().equals(user.getPassword())) {
            return CommonResultUtil.returnFalse(CommonResultUtil.MessageCode.USERNAME_OR_PASSWORD_NOT_TRUE);
        }
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        Subject subject = SecurityUtils.getSubject();
        subject.getSession().setTimeout(1440000L);  // 设置session过期时间为4h
        subject.login(token);
        if (subject.isAuthenticated()) {
            logger.info("User '" + user.getUsername() + "' successfully logged in.");
            return CommonResultUtil.returnTrue("登陆成功");
        }else{
            return CommonResultUtil.returnFalse(CommonResultUtil.MessageCode.USERNAME_OR_PASSWORD_NOT_TRUE);
        }
    }

    /**
     * 用户退出
     * @return 退出状态（成功/失败）
     */
    public Map<String, Object> logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return CommonResultUtil.returnTrue("退出成功");
    }

}
