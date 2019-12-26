package cn.cambridge.hexohero.basic.shiro;

import cn.cambridge.hexohero.basic.bean.Permission;
import cn.cambridge.hexohero.basic.bean.Role;
import cn.cambridge.hexohero.basic.bean.User;
import cn.cambridge.hexohero.basic.config.LimitConfig;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
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

    private LimitConfig limitConfig;

    @Autowired
    public void setUserConfig(LimitConfig limitConfig) { this.limitConfig = limitConfig; }

    /**
     * 授权方法
     * @param principalCollection 主体信息
     * @return 授权结果
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 获取用户的角色列表
        for(Role role : limitConfig.selectRolesByIds(user.getRoles())) {
            simpleAuthorizationInfo.addRole(role.getRoleName());    // 为用户添加角色
            // 获取角色的权限列表
            for (Permission permission : limitConfig.selectPermissionsByIds(role.getPermissions())) {
                simpleAuthorizationInfo.addStringPermission(permission.getPermissionName());    // 为用户添加权限
            }
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 认证方法
     * @param authenticationToken 主体认证信息
     * @return 认证结果
     * @throws AuthenticationException 认证失败
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userId = (String) authenticationToken.getPrincipal();  // 获取主体用户名
        User user = limitConfig.selectUserById(userId);
        if(user == null) {
            throw new UnknownAccountException();
        }
        return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
    }
}
