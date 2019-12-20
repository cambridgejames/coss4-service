package cn.cambridge.hexohero.basic.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "directory")
public class DirectoryConfig {
    private String root;

    public void setRoot(String root) {
        if(root == null || "".equals(root)) {
            root = System.getProperty("user.name"); // 如果配置为空，则默认目录为用户主目录
        }
        String endOfPath = String.valueOf(root.charAt(root.length() - 1));
        // 如果配置的最后一个路径是路径分隔符，则删除该分隔符
        if(System.getProperty("file.separator").equals(endOfPath)) {
            this.root = root.substring(0, root.length() - 1);
        } else {
            this.root = root;
        }
    }

    public String getRoot() {
        return root;
    }
}
