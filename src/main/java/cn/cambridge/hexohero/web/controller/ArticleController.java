package cn.cambridge.hexohero.web.controller;

import cn.cambridge.hexohero.basic.util.CommonResultUtil;
import cn.cambridge.hexohero.web.service.ArticleService;
import cn.cambridge.hexohero.web.vo.ArticleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
    public void setArticleService(ArticleService articleService) { this.articleService = articleService; }

    /**
     * 获取目录结构
     * @return 目录结构
     */
    @GetMapping("directory")
    @ResponseBody
    public Map<String, Object> getDirectory() { return articleService.queryDirectory(); }

    /**
     * 获取单个文件信息
     * @param article 文件信息
     * @return 文件信息
     */
    @GetMapping("/queryArticle")
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
    @PostMapping("/editArticle")
    @ResponseBody
    public Map<String, Object> editArticle(@RequestBody ArticleDTO article) {
        if(ObjectUtils.isEmpty(article.getArticlePath()) || StringUtils.isEmpty(article.getArticleContext())) {
            return CommonResultUtil.returnFalse(CommonResultUtil.MessageCode.PARAMETERS_NOT_ENOUGH);
        }
        return articleService.editArticle(article);
    }

    /**
     * 新增文章
     * @param article 文件信息（路径）
     * @return 指定根目录的全部目录结构
     */
    @PostMapping("/addArticle")
    @ResponseBody
    public Map<String, Object> addArticle(@RequestBody ArticleDTO article) {
        if(ObjectUtils.isEmpty(article.getArticlePath())) {
            return CommonResultUtil.returnFalse(CommonResultUtil.MessageCode.PARAMETERS_NOT_ENOUGH);
        }
        if(ObjectUtils.isEmpty(article.getCascaded())) {
            article.setCascaded(true);  // 新增文章时级联默认为true，即自动新建不存在的路径
        }
        return articleService.addArticle(article);
    }


}
