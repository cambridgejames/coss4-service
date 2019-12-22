package cn.cambridge.hexohero.web.service;

import cn.cambridge.hexohero.basic.config.DirectoryConfig;
import cn.cambridge.hexohero.basic.util.ArticleRecycleUtil;
import cn.cambridge.hexohero.basic.util.CommonResultUtil;
import cn.cambridge.hexohero.basic.util.DirectoryUtil;
import cn.cambridge.hexohero.common.service.CommonService;
import cn.cambridge.hexohero.web.vo.ArticleDTO;
import cn.cambridge.hexohero.web.vo.ArticleRecycleDTO;
import cn.cambridge.hexohero.web.vo.ArticleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/**
 * 文章编辑相关业务
 * @author PengJQ
 * @date 2019-12-18
 */
@Service
public class ArticleService {
    private static Logger logger = LoggerFactory.getLogger(CommonService.class);
    private static String separator = java.io.File.separator;

    private static DirectoryConfig directoryConfig;

    @Autowired
    public void setDirectoryConfig(DirectoryConfig directoryConfig) { ArticleService.directoryConfig = directoryConfig; }

    /**
     * 获取指定目录的目录结构
     * @return 结果
     */
    public Map<String, Object> queryDirectory() {
        String dirRoot = directoryConfig.getRoot();
        if(dirRoot == null || "".equals(dirRoot)) {
            logger.warn(dirRoot + ": " + CommonResultUtil.MessageCode.NO_SUCH_FILE);
            return CommonResultUtil.returnFalse(CommonResultUtil.MessageCode.NO_SUCH_FILE);
        }
        return CommonResultUtil.returnTrue(DirectoryUtil.getDirectory(dirRoot));
    }

    /**
     * 获取指定文章的内容
     * @param article 文章信息
     * @return 文章内容
     */
    public Map<String, Object> queryArticle(ArticleDTO article) {
        String realPath = directoryConfig.getRoot() + separator + String.join(separator, article.getArticlePath());
        File articleNew = new File(realPath);
        if(!articleNew.exists() || !articleNew.isFile()) {
            // 如果文件不存在，或该文件路径指向的不是一个文件，则返回找不到文件
            return CommonResultUtil.returnFalse(CommonResultUtil.MessageCode.NO_SUCH_FILE);
        }
        byte[] fileContent = new byte[((Long) articleNew.length()).intValue()];
        try {
            FileInputStream in = new FileInputStream(articleNew);
            int nextIndex = in.read(fileContent);
            String context = new String(fileContent, StandardCharsets.UTF_8);
            in.close();
            logger.info("Success reading file: '" + realPath + "'\n" +
                    "<== \tNext index: " + nextIndex);
            // 新建用于返回值的对象
            ArticleVO articleVO = new ArticleVO();
            articleVO.setArticlePath(article.getArticlePath());
            articleVO.setArticleContext(context);
            articleVO.setArticleSize(articleNew.length());
            articleVO.setArticleFileName(article.getArticlePath()[article.getArticlePath().length - 1]);
            articleVO.setUpdateTime(new Date(articleNew.lastModified()));
            return CommonResultUtil.returnTrue(articleVO);
        } catch (IOException ioe) {
            logger.error(ioe.toString(), ioe);
            return CommonResultUtil.returnFalse(CommonResultUtil.MessageCode.FILE_READ_ERROR);
        }
    }

    /**
     * 更新文章内容
     * @param article 文章信息
     * @return 操作结果（成功/失败）
     */
    public Map<String, Object> editArticle(ArticleDTO article) {
        String realPath = directoryConfig.getRoot() + separator + String.join(separator, article.getArticlePath());
        File articleNew = new File(realPath);
        if(!articleNew.exists() || !articleNew.isFile()) {
            // 如果文件不存在，或该文件路径指向的不是一个文件，则返回找不到文件
            return CommonResultUtil.returnFalse(CommonResultUtil.MessageCode.NO_SUCH_FILE);
        }
        try {
            // 以UTF-8编码和覆盖方式写入文件
            FileOutputStream fileOutputStream = new FileOutputStream(articleNew);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(article.getArticleContext());
            bufferedWriter.close();
            outputStreamWriter.close();
            fileOutputStream.close();
            logger.info("Success writing to file: '" + realPath);
            return CommonResultUtil.returnTrue();
        } catch (IOException ioe) {
            logger.error(ioe.toString(), ioe);
            return CommonResultUtil.returnFalse(CommonResultUtil.MessageCode.FILE_WRITE_ERROR);
        }
    }

    /**
     * 新增文章
     * @param article 文件信息（路径）
     * @return 指定根目录的全部目录结构
     */
    public Map<String, Object> addArticle(ArticleDTO article) {
        String realPath = directoryConfig.getRoot() + separator + String.join(separator, article.getArticlePath());
        File articleNew = new File(realPath);
        if(articleNew.exists()) {
            return CommonResultUtil.returnFalse(CommonResultUtil.MessageCode.FILE_ALREADY_EXIST);
        }
        File dir = articleNew.getParentFile();
        if (!dir.exists() && (!article.getCascaded() || !dir.mkdirs())) {
            // 文件路径不存在且设置级联新建为true且文件路径创建失败时，或文件路径不存在且设置级联新建为false时执行
            return CommonResultUtil.returnFalse(CommonResultUtil.MessageCode.FILE_WRITE_ERROR);
        }
        try {
            if (articleNew.createNewFile()) {
                logger.info("New file created in path: '" + realPath);
                return this.queryDirectory();
            } else {
                return CommonResultUtil.returnFalse(CommonResultUtil.MessageCode.FILE_WRITE_ERROR);
            }
        } catch (IOException ioe) {
            logger.error(ioe.toString(), ioe);
            return CommonResultUtil.returnFalse(CommonResultUtil.MessageCode.FILE_WRITE_ERROR);
        }
    }

    /**
     * 将指定文件移入回收站
     * @param article 文件信息
     * @return 文件在回收站中的ID
     */
    public Map<String, Object> removeArticle(ArticleDTO article) {
        String realPath = directoryConfig.getRoot() + separator + String.join(separator, article.getArticlePath());
        File articleNew = new File(realPath);
        if(!articleNew.exists()) {
            // 如果文件不存在则返回找不到文件
            return CommonResultUtil.returnFalse(CommonResultUtil.MessageCode.NO_SUCH_FILE);
        }
        try {
            ArticleRecycleUtil.removeArticle(article.getArticlePath());
            return this.queryDirectory();
        } catch (IOException ioe) {
            logger.error(ioe.toString(), ioe);
            return CommonResultUtil.returnFalse(CommonResultUtil.MessageCode.FILE_WRITE_ERROR);
        }
    }

    /**
     * 获取回收站的文件列表
     * @return 回收站中的文件列表
     */
    public Map<String, Object> queryRecycleList() {
        return CommonResultUtil.returnTrue(ArticleRecycleUtil.queryRecycleList());
    }

    /**
     * 将单个文件从回收站中还原
     * @param article 文件信息
     * @return 还原结果（成功/失败）
     */
    public Map<String, Object> restoreArticle(ArticleRecycleDTO article) {
        try {
            Map<String, Object> result = ArticleRecycleUtil.restoreArticle(article.getArticleId());
            return (int) result.get("code") == 0 ? this.queryDirectory() : result;
        } catch (IOException ioe) {
            logger.error(ioe.toString(), ioe);
            return CommonResultUtil.returnFalse(CommonResultUtil.MessageCode.FILE_WRITE_ERROR);
        }
    }

    /**
     * 清空回收站
     * @return 操作结果（成功/失败）
     */
    public Map<String, Object> clearRecycle() {
        try {
            if(ArticleRecycleUtil.clearRecycle()) {
                return CommonResultUtil.returnTrue();
            } else {
                return CommonResultUtil.returnFalse(CommonResultUtil.MessageCode.FILE_WRITE_ERROR);
            }
        } catch (IOException ioe) {
            logger.error(ioe.toString(), ioe);
            return CommonResultUtil.returnFalse(CommonResultUtil.MessageCode.FILE_WRITE_ERROR);
        }
    }
}
