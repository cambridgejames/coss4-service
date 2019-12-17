package cn.cambridge.coss.common.service;

import cn.cambridge.coss.basic.config.DirectoryConfig;
import cn.cambridge.coss.basic.util.CommonResultUtil;
import cn.cambridge.coss.basic.util.DirectoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author PengJQ
 * @date 2019-12-17
 */
@Service
public class CommonService {

    @Autowired
    private DirectoryConfig directoryConfig;

    /**
     * 获取指定目录的目录结构
     * @return 结果
     */
    public Map<String, Object> getDirectory() {
        String dirRoot = directoryConfig.getRoot();
        if(dirRoot == null || "".equals(dirRoot)) {
            return CommonResultUtil.returnFalse(CommonResultUtil.MessageCode.NO_SUCH_FILE);
        }

        return CommonResultUtil.returnTrue(DirectoryUtil.getDirectory(dirRoot));
    }
}
