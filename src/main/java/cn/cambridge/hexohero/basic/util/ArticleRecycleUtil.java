package cn.cambridge.hexohero.basic.util;

import cn.cambridge.hexohero.basic.config.DirectoryConfig;
import cn.cambridge.hexohero.basic.config.RecycleConfig;
import cn.cambridge.hexohero.web.vo.ArticleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Map;

/**
 * 文件回收站功能实现类
 * @author Cambridge_James
 * @date 2019-12-21
 */
@Component
public class ArticleRecycleUtil {
    private static Logger logger = LoggerFactory.getLogger(ArticleRecycleUtil.class);

    private static String separator = java.io.File.separator;

    private static DirectoryConfig directoryConfig;
    private static RecycleConfig recycleConfig;

    @Autowired
    public void setDirectoryConfig(DirectoryConfig directoryConfig) { ArticleRecycleUtil.directoryConfig = directoryConfig; }

    @Autowired
    public void setRecycleConfig(RecycleConfig recycleConfig) { ArticleRecycleUtil.recycleConfig = recycleConfig; }

    /**
     * 将文件移入回收站
     * @param articlePath 待移入回收站的文件路径
     * @throws IOException 文件移动失败
     */
    public static void removeArticle(String[] articlePath) throws IOException {
        Long articleId = getId();
        String fromPath = directoryConfig.getRoot() + separator + String.join(separator, articlePath);
        String articleName = articlePath[articlePath.length - 1];
        String toPath = directoryConfig.getRecycle() + separator + articleName + "@" + articleId + ".recycle";
        Files.move(Paths.get(fromPath), Paths.get(toPath));
        // 下面保存文件信息
        File recycleFile = new File(toPath);
        ArticleVO articleVO = new ArticleVO();
        articleVO.setArticlePath(articlePath);
        articleVO.setArticleSize(recycleFile.length());
        articleVO.setArticleFileName(articleName);
        articleVO.setUpdateTime(new Date());
        Map<Long, Object> articleInformation = recycleConfig.getRecycleMap();
        articleInformation.put(articleId, articleVO);
        recycleConfig.setRecycleMap(articleInformation);
        recycleConfig.updateConfig();
        logger.info("\n==> \tMove file: " + fromPath + " -> " + toPath);
    }

    /**
     * 将单个文件从回收站中还原
     * @param articleId 待还原的文件ID
     * @throws IOException 文件移动失败
     */
    public static Map<String, Object> restoreArticle(Long articleId) throws IOException {
        Map<Long, Object> articleInformation = recycleConfig.getRecycleMap();
        ArticleVO article = (ArticleVO) articleInformation.get(articleId);
        if(article == null) {
            return CommonResultUtil.returnFalse(CommonResultUtil.MessageCode.NO_SUCH_FILE);
        }
        String fromPath = directoryConfig.getRecycle() + separator + article.getArticleFileName() + "@" + articleId + ".recycle";
        String toPath = directoryConfig.getRoot() + separator + String.join(separator, article.getArticlePath());
        File file = new File(toPath);
        File dir = file.getParentFile();
        // 移动文件前先创建文件目录，防止出现NoSuchFileException
        if(!dir.exists() && !dir.mkdirs()) {
            return CommonResultUtil.returnFalse(CommonResultUtil.MessageCode.FILE_WRITE_ERROR);
        }
        if(!restoreFileOrDirectory(fromPath, toPath)) {
            return CommonResultUtil.returnFalse(CommonResultUtil.MessageCode.FILE_WRITE_ERROR);
        }
        // 下面保存文件信息
        articleInformation.remove(articleId);
        recycleConfig.setRecycleMap(articleInformation);
        recycleConfig.updateConfig();
        logger.info("\n==> \tMove file: " + fromPath + " -> " + toPath);
        return CommonResultUtil.returnTrue();
    }

    /**
     * 清空回收站
     * @throws IOException 清空回收站失败
     */
    public static boolean clearRecycle() throws IOException {
        boolean result = DirectoryUtil.clearDirectory(directoryConfig.getRecycle());
        recycleConfig.getRecycleMap().clear();
        recycleConfig.updateConfig();
        return result;
    }

    /**
     * 获取回收站的文件列表
     * @return 回收站中的文件列表
     */
    public static Map<Long, Object> queryRecycleList() {
        return recycleConfig.getRecycleMap();
    }

    /**
     * 获取新的文章ID
     * 由于不能允许ID重复，故对recycleId的读写都要加锁
     * @return 新的文章ID
     */
    private static synchronized long getId() {
        long recycleId = recycleConfig.getRecycleId();
        recycleConfig.setRecycleId(recycleId + 1);
        return recycleId;
    }

    /**
     * 将单个文件或目录从回收站中还原，若存在同名文件则覆盖
     * @param fromPath 文件或目录在回收站中的路径
     * @param toPath 文件或目录还原的目标路径
     * @return 操作结果（成功/失败）
     * @throws IOException 文件或目录移动失败
     */
    private static boolean restoreFileOrDirectory(String fromPath, String toPath) throws IOException {
        File rootDir = new File(fromPath);
        if(!rootDir.isDirectory()) {
            // 如果目标是文件，就直接移动
            Files.move(Paths.get(fromPath), Paths.get(toPath), StandardCopyOption.REPLACE_EXISTING);
            return true;
        }
        File[] files = rootDir.listFiles();
        if(files == null) {
            return false;
        }
        for(File currentFile : files) {
            String fromPathChild = fromPath + separator + currentFile.getName();
            String toPathChild = toPath + separator + currentFile.getName();
            if(currentFile.isDirectory() && new File(toPathChild).exists()) {
                // 如果目标是目录且存在，就递归调用，移动子目录
                if(!restoreFileOrDirectory(fromPathChild, toPathChild)) {
                    return false;
                }
            } else {
                // 如果目标不存在或是文件，就直接移动
                Files.move(Paths.get(fromPathChild), Paths.get(toPathChild), StandardCopyOption.REPLACE_EXISTING);
            }
        }
        return rootDir.delete();
    }
}
