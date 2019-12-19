package cn.cambridge.coss.web.service;

import cn.cambridge.coss.basic.config.DirectoryConfig;
import cn.cambridge.coss.basic.util.CommonResultUtil;
import cn.cambridge.coss.common.service.CommonService;
import cn.cambridge.coss.web.vo.ArticleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
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

    private DirectoryConfig directoryConfig;

    @Autowired
    public void setDirectoryConfig(DirectoryConfig directoryConfig) { this.directoryConfig = directoryConfig; }

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
            article.setArticleContext(context);
            return CommonResultUtil.returnTrue(article);
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
}
