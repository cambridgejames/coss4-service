package cn.cambridge.coss.common.service;

import cn.cambridge.coss.basic.util.CommonResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author PengJQ
 * @date 2019-12-17
 */
@Service
public class CommonService {
    private Logger logger = LoggerFactory.getLogger(CommonService.class);

    public Map<String, Object> helloWorld() {
        logger.info("Hello World!");
        return CommonResultUtil.returnTrue("Hello World!");
    }
}
