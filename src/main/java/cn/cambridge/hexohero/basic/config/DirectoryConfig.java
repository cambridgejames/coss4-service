package cn.cambridge.hexohero.basic.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "directory")
@Data
public class DirectoryConfig {
    private String root;
}
