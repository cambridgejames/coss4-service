package cn.cambridge.hexohero.basic.config;

import cn.cambridge.hexohero.basic.util.ArticleRecycleUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * 目录配置读取类
 * @author PengJQ
 * @date 2019-12-24
 */
@Configuration
@ConfigurationProperties(prefix = "directory")
public class DirectoryConfig {
    private Logger logger = LoggerFactory.getLogger(ArticleRecycleUtil.class);

    private String homePath = System.getProperty("user.home");
    private String separator = java.io.File.separator;
    private String projectName = "hexohero";

    private String root;
    private String recycle;
    private String draft;

    public void setRoot(String root) {
        if(root == null || "".equals(root) || !new File(root).exists()) {
            logger.warn("The configuration item 'directory.root: " + root + "' is invalid or does not exist.");
            this.root = homePath;
            // 如果配置为空或目录不存在，则默认在用户主目录下新建/hexohero/public
            String extPath = separator + projectName + separator + "public";
            File dir = new File(this.root + extPath);
            if(!(!dir.exists() && !dir.mkdirs())) {
                this.root += extPath;
            }
            logger.info("The configuration item 'directory.root' has been initialized to '" + this.root + "' by default.");
        } else {
            this.root = preprocess(root);
        }
    }

    public void setRecycle(String recycle) {
        if(recycle == null || "".equals(recycle) || !new File(recycle).exists()) {
            logger.warn("The configuration item 'directory.recycle: " + recycle + "' is invalid or does not exist.");
            this.recycle = homePath;
            // 如果配置为空或目录不存在，则默认在用户主目录下新建/hexohero/recycle
            String extPath = separator + projectName + separator + "recycle";
            File dir = new File(this.recycle + extPath);
            if(!(!dir.exists() && !dir.mkdirs())) {
                this.recycle += extPath;
            }
            logger.info("The configuration item 'directory.recycle' has been initialized to '" + this.recycle + "' by default.");
        } else {
            this.recycle = preprocess(recycle);
        }
    }

    public void setDraft(String draft) {
        if(draft == null || "".equals(draft) || !new File(draft).exists()) {
            logger.warn("The configuration item 'directory.draft: " + draft + "' is invalid or does not exist.");
            this.draft = homePath;
            // 如果配置为空或目录不存在，则默认在用户主目录下新建/hexohero/temp
            String extPath = separator + projectName + separator + "draft";
            File dir = new File(this.draft + extPath);
            if(!(!dir.exists() && !dir.mkdirs())) {
                this.draft += extPath;
            }
            logger.info("The configuration item 'directory.temp' has been initialized to '" + this.draft + "' by default.");
        } else {
            this.draft = preprocess(draft);
        }
    }

    public String getRoot() { return this.root; }
    public String getRecycle() { return this.recycle; }
    public String getDraft() { return this.draft; }

    /**
     * 目录路径预处理
     * @param raw 配置文件中输入的路径
     * @return 处理后的路径
     */
    private static String preprocess(String raw) {
        String endOfPath = String.valueOf(raw.charAt(raw.length() - 1));
        // 如果配置的最后一个路径是路径分隔符，则删除该分隔符
        if(System.getProperty("file.separator").equals(endOfPath)) {
            return raw.substring(0, raw.length() - 1);
        } else {
            return raw;
        }
    }
}
