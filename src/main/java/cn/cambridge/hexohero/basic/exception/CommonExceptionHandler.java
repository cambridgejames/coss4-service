package cn.cambridge.hexohero.basic.exception;

import cn.cambridge.hexohero.basic.util.CommonResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 全局异常捕获处理类
 * @author Cambridge_James
 * @date 2019-12-26
 */
@Slf4j
@ControllerAdvice
public class CommonExceptionHandler {

    /**
     * 捕获Shiro用户认证和授权异常
     * @param authorizationException 用户认证和授权异常
     * @return 用户权限不足
     */
    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    public Map<String, Object> authorizationExceptionHandler(AuthorizationException authorizationException) {
        log.info(authorizationException.toString());
        return CommonResultUtil.returnFalse(CommonResultUtil.MessageCode.PERMISSION_DENIED);
    }

}
