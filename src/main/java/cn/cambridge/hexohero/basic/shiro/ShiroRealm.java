package cn.cambridge.hexohero.basic.shiro;

import cn.cambridge.hexohero.basic.config.UserConfig;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Shiro权限验证类
 * @author PengJQ
 * @date 2019-12-25
 */
@Component
public class ShiroRealm extends AuthorizingRealm {

    private UserConfig userConfig;

    @Autowired
    public void setUserConfig(UserConfig userConfig) { this.userConfig = userConfig; }

    /**
     * 授权方法
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 认证方法
     * @param authenticationToken 主体认证信息
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();  // 获取主体用户名
        String password = new String((char[]) authenticationToken.getCredentials()); // 获取主体密码
        if(!userConfig.getUsername().equals(username) || !userConfig.getPassword().equals(password)) {
            throw new UnknownAccountException();
        }
        return new SimpleAuthenticationInfo(username, password, getName()); // 如果身份认证验证成功，返回一个AuthenticationInfo实现
    }
}
