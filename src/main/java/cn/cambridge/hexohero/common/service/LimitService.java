package cn.cambridge.hexohero.common.service;

import cn.cambridge.hexohero.basic.bean.Role;
import cn.cambridge.hexohero.basic.bean.User;
import cn.cambridge.hexohero.basic.config.LimitConfig;
import cn.cambridge.hexohero.basic.util.CommonResultUtil;
import cn.cambridge.hexohero.common.vo.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class LimitService {

    LimitConfig limitConfig;

    @Autowired
    public void setLimitConfig(LimitConfig limitConfig) { this.limitConfig = limitConfig; }

    /**
     * 用户登录
     * @param user 用户名和密码信息
     * @return 登录状态（成功/失败）
     */
    public Map<String, Object> login(UserDTO user) {
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (Exception e) {
            return CommonResultUtil.returnFalse(CommonResultUtil.MessageCode.USERNAME_OR_PASSWORD_NOT_TRUE);
        }
        if (subject.isAuthenticated()) {
            String roleId = (String) subject.getSession().getAttribute("roleId");
            String currentRoleId = roleId == null ? ((User) SecurityUtils.getSubject().getPrincipal()).getRoles().get(0) : roleId;
            Role role = limitConfig.selectRoleById(currentRoleId);
            log.info("User '" + user.getUsername() + "' successfully logged in with role '" + role.getRoleName() + "'.");
            return CommonResultUtil.returnTrue(role.getDescription());
        } else {
            return CommonResultUtil.returnFalse(CommonResultUtil.MessageCode.LOGIN_FAILED);
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

    /**
     * 切换角色
     * @param userDTO 要切换乘的角色ID
     * @return 切换结果（成功/失败）
     */
    public Map<String, Object> runAs(UserDTO userDTO) {
        String roleId = userDTO.getRoleId();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user.getRoles().contains(roleId)) {
            SecurityUtils.getSubject().getSession().setAttribute("roleId", roleId);
            return CommonResultUtil.returnTrue(limitConfig.selectRoleById(roleId).getDescription());
        } else {
            return CommonResultUtil.returnFalse(CommonResultUtil.MessageCode.PERMISSION_DENIED);
        }
    }

}
