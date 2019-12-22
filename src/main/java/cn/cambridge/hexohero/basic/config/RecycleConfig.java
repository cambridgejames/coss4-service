package cn.cambridge.hexohero.basic.config;

import cn.cambridge.hexohero.basic.util.ArticleRecycleUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class RecycleConfig {
    private Logger logger = LoggerFactory.getLogger(ArticleRecycleUtil.class);

    private Long recycleId;  // 文件ID，防止同名文件相互覆盖
    private Map<Long, Object> recycleMap;  // ConcurrentHashMap线程安全且锁粒度较小
    private String configFileName = ".recycleConfig";
    private String separator = java.io.File.separator;

    private DirectoryConfig directoryConfig;

    @Autowired
    @SuppressWarnings("unchecked")
    public RecycleConfig(DirectoryConfig directoryConfig) {
        this.directoryConfig = directoryConfig;
        // 在此处对回收站配置相关的recycleId和recycleMap进行初始化
        File configFile = new File(directoryConfig.getRecycle() + separator + configFileName);
        try {
            FileInputStream fileInputStream = new FileInputStream(configFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            this.recycleId = objectInputStream.readLong();
            this.recycleMap = (ConcurrentHashMap<Long, Object>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            logger.info("Recycle bin configuration file loaded successfully.");
        } catch (FileNotFoundException e) {
            logger.info("Profile '" + configFileName + "' not found.");
            this.recycleId = 0L;
            this.recycleMap = new ConcurrentHashMap<>();
        } catch (Exception e) {
            logger.error(e.toString(), e);
            this.recycleId = 0L;
            this.recycleMap = new ConcurrentHashMap<>();
        }
    }

    public void setRecycleId(Long recycleId) {
        this.recycleId = recycleId;
    }
    public void setRecycleMap(Map<Long, Object> recycleMap) {
        this.recycleMap = recycleMap;
    }

    public Long getRecycleId() {
        return this.recycleId;
    }
    public  Map<Long, Object> getRecycleMap() {
        return this.recycleMap;
    }

    /**
     * 将回收站配置写入配置文件
     * @throws IOException 文件写入出现问题
     */
    public void updateConfig() throws IOException {
        File configFile = new File(directoryConfig.getRecycle() + separator + configFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(configFile);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeLong(recycleId);
        objectOutputStream.writeObject(recycleMap);
        objectOutputStream.close();
        fileOutputStream.close();
        logger.info("Recycle bin configuration file saved successfully.");
    }
}
