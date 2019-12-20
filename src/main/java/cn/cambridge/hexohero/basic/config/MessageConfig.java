package cn.cambridge.hexohero.basic.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "message")
@Data
public class MessageConfig {
    private String msgTrue;
    private String msgFalse;
}
