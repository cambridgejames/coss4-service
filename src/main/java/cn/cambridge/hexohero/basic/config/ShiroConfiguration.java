package cn.cambridge.hexohero.basic.config;

import cn.cambridge.hexohero.basic.shiro.ShiroRealm;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置类
 * @author PengJQ
 * @date 2019-12-25
 */
@Configuration
public class ShiroConfiguration {

    @Bean
    public DefaultSecurityManager getDefaultSecurityManager(ShiroRealm shiroRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(shiroRealm);
        return defaultWebSecurityManager;
    }

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/");    // 登录页面
        shiroFilterFactoryBean.setSuccessUrl("/");  // 登录成功后要跳转的连接
        shiroFilterFactoryBean.setUnauthorizedUrl("/"); // 错误页面
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("login/login", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

}
