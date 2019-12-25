package cn.cambridge.hexohero.web.service;

import cn.cambridge.hexohero.basic.config.DirectoryConfig;
import cn.cambridge.hexohero.basic.util.CommonResultUtil;
import cn.cambridge.hexohero.basic.util.DirectoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 文章草稿管理相关业务
 * @author PengJQ
 * @date 2019-12-24
 */
@Service
public class DraftService {
    private static Logger logger = LoggerFactory.getLogger(DraftService.class);
    private static String separator = java.io.File.separator;

    private static DirectoryConfig directoryConfig;

    @Autowired
    public void setDirectoryConfig(DirectoryConfig directoryConfig) { DraftService.directoryConfig = directoryConfig; }

    /**
     * 查询草稿箱列表
     * @return 草稿箱文件列表
     */
    public Map<String, Object> queryDraftList() {
        String draftDir = directoryConfig.getDraft();
        Map<String, Object> resultMap = DirectoryUtil.getDirectory(draftDir);
        return CommonResultUtil.returnTrue(resultMap);
    }

}
