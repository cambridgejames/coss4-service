package cn.cambridge.hexohero.basic.bean;

import lombok.Data;

import java.util.List;

/**
 * 用户实体类
 * @author PengJQ
 * @date 2019-12-26
 */
@Data
public class User {
    private String id;
    private String nickname;
    private String password;
    private List<String> roles;    // 用户具有的角色ID集合
}
