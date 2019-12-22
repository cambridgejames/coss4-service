package cn.cambridge.hexohero.basic.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
@ConfigurationProperties(prefix = "directory")
public class DirectoryConfig {
    private String root;
    private String recycle;

    public void setRoot(String root) { this.root = preprocess(root); }

    public String getRoot() { return root; }

    public void setRecycle(String recycle) { this.recycle = preprocess(recycle); }

    public String getRecycle() { return recycle; }

    /**
     * 目录路径预处理
     * @param raw 配置文件中输入的路径
     * @return 处理后的路径
     */
    private static String preprocess(String raw) {
        if(raw == null || "".equals(raw) || !new File(raw).exists()) {
            raw = System.getProperty("user.name"); // 如果配置为空或目录不存在，则默认目录为用户主目录
        }
        String endOfPath = String.valueOf(raw.charAt(raw.length() - 1));
        // 如果配置的最后一个路径是路径分隔符，则删除该分隔符
        if(System.getProperty("file.separator").equals(endOfPath)) {
            return raw.substring(0, raw.length() - 1);
        } else {
            return raw;
        }
    }
}
