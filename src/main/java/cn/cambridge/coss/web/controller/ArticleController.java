package cn.cambridge.coss.web.controller;

import cn.cambridge.coss.basic.util.CommonResultUtil;
import cn.cambridge.coss.web.service.ArticleService;
import cn.cambridge.coss.web.vo.ArticleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 文章编辑相关
 * @author PengJQ
 * @date 2019-12-18
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    private ArticleService articleService;

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * 获取单个文件信息
     * @return 文件信息
     */
    @RequestMapping("/queryArticle")
    @ResponseBody
    public Map<String, Object> queryArticle(@RequestBody ArticleDTO article) {
        if(ObjectUtils.isEmpty(article.getArticlePath())) {
            return CommonResultUtil.returnFalse(CommonResultUtil.MessageCode.PARAMETERS_NOT_ENOUGH);
        }
        return articleService.queryArticle(article);
    }

    /**
     * 更新文章内容
     * @param article 文章信息
     * @return 操作结果（成功/失败）
     */
    @RequestMapping("/editArticle")
    @ResponseBody
    public Map<String, Object> editArticle(@RequestBody ArticleDTO article) {
        if(ObjectUtils.isEmpty(article.getArticlePath()) || StringUtils.isEmpty(article.getArticleContext())) {
            return CommonResultUtil.returnFalse(CommonResultUtil.MessageCode.PARAMETERS_NOT_ENOUGH);
        }
        return articleService.editArticle(article);
    }
}
