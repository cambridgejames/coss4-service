package cn.cambridge.hexohero.basic.bean;

import lombok.Data;

import java.util.List;

/**
 * 角色实体类
 * @author PengJQ
 * @date 2019-12-26
 */
@Data
public class Role {
    private String id;
    private String roleName;
    private List<String> permissions; // 角色对应的权限ID集合
}
