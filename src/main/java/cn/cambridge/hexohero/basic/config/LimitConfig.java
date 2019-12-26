package cn.cambridge.hexohero.basic.config;

import cn.cambridge.hexohero.basic.bean.Permission;
import cn.cambridge.hexohero.basic.bean.Role;
import cn.cambridge.hexohero.basic.bean.User;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户角色权限配置类
 * @author PengJQ
 * @date 2019-12-26
 */
@Configuration
@ConfigurationProperties(prefix = "limit")
@Data
public class LimitConfig {
    private Map<String, User> userMap;
    private Map<String, Role> roleMap;
    private Map<String, Permission> permissionMap;

    public User selectUserById(String id) {
        User user = this.userMap.get(id);
        if(user != null) {
            user.setId(id);
        }
        return user;
    }

    public Role selectRoleById(String id) {
        Role role = this.roleMap.get(id);
        if(role != null) {
            role.setId(id);
        }
        return role;
    }

    public Permission selectPermissionById(String id) {
        Permission permission = this.permissionMap.get(id);
        if(permission != null) {
            permission.setId(id);
        }
        return permission;
    }

    public List<Role> selectRolesByIds(List<String> ids) {
        List<Role> resultList = new ArrayList<>();
        if(ids != null) {
            for(String id : ids) {
                Role role = selectRoleById(id);
                if(role != null) {
                    resultList.add(role);
                }
            }
        }
        return resultList;
    }

    public List<Permission> selectPermissionsByIds(List<String> ids) {
        List<Permission> resultList = new ArrayList<>();
        if(ids != null) {
            for(String id : ids) {
                Permission permission = selectPermissionById(id);
                if(permission != null) {
                    resultList.add(permission);
                }
            }
        }
        return resultList;
    }

    @Override
    public String toString() {
        return "[LimitConfig: {\n" +
                "\tuserMap: " + userMap.toString() + ",\n" +
                "\troleMap: " + roleMap.toString() + ",\n" +
                "\tpermissionMap: " + permissionMap.toString() + "\n" +
                "}]";
    }
}
