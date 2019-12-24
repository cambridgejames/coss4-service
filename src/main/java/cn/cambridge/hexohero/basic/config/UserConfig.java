package cn.cambridge.hexohero.basic.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "user")
@Data
public class UserConfig {
    private String username;
    private String password;
}
