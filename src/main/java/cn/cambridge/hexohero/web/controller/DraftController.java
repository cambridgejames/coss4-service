package cn.cambridge.hexohero.web.controller;

import cn.cambridge.hexohero.web.service.DraftService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 文章草稿管理相关控制层
 * @author PengJQ
 * @date 2019-12-24
 */
@RestController
@RequestMapping("/draft")
public class DraftController {

    private DraftService draftService;

    @Autowired
    public void setDraftService(DraftService draftService) { this.draftService = draftService; }

    /**
     * 查询草稿箱文件列表
     * @return 草稿箱文件列表
     */
    @GetMapping("/queryDraftList")
    @RequiresPermissions(value = "articleEdit")
    @ResponseBody
    public Map<String, Object> queryDraftList() { return draftService.queryDraftList(); }
}
