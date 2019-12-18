package cn.cambridge.coss.common.service;

import cn.cambridge.coss.basic.config.DirectoryConfig;
import cn.cambridge.coss.basic.util.CommonResultUtil;
import cn.cambridge.coss.basic.util.DirectoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author PengJQ
 * @date 2019-12-17
 */
@Service
public class CommonService {
    private Logger logger = LoggerFactory.getLogger(CommonService.class);

    private DirectoryConfig directoryConfig;

    @Autowired
    public void setDirectoryConfig(DirectoryConfig directoryConfig) {
        this.directoryConfig = directoryConfig;
    }

    /**
     * 获取指定目录的目录结构
     * @return 结果
     */
    public Map<String, Object> queryDirectory() {
        String dirRoot = directoryConfig.getRoot();
        if(dirRoot == null || "".equals(dirRoot)) {
            logger.warn(dirRoot + ": " + CommonResultUtil.MessageCode.NO_SUCH_FILE.getMsg());
            return CommonResultUtil.returnFalse(CommonResultUtil.MessageCode.NO_SUCH_FILE);
        }
        return CommonResultUtil.returnTrue(DirectoryUtil.getDirectory(dirRoot));
    }
}
