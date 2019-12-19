package cn.cambridge.coss.basic.util;

import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * 公共返回类
 * @author PengJQ
 * @date 2019-12-18
 */
public class CommonResultUtil {
    private static String msgTrue = "请求成功";
    private static String msgFalse = "请求失败";

    /**
     * 错误代码及对应的错误信息
     *
     * 对错误代码的部分说明：
     *  1*** - 用户及权限系统相关错误，如登录超时、密码错误等
     *  2*** - 请求相关错误，如参数不足、参数类型不合法等
     *  3*** - 文件系统相关错误，如找不到文件等
     *  4*** - 数据库系统相关错误，如找不到查询结果等
     *  5*** - 其他类型错误，如服务器内部错误等
     *
     * @author PengJQ
     * @date 2019-12-18
     */
    public enum MessageCode {
        PARAMETERS_NOT_ENOUGH(2000, "传入的参数不足"),
        NO_SUCH_FILE(3000, "找不到对应的文件"),
        FILE_READ_ERROR(3100, "文件读取时出错"),
        FILE_WRITE_ERROR(3100, "文件写入时出错"),
        SERVER_INTERNAL_ERROR(5500, "服务器内部错误"),
        OTHER_ERROR(5900, msgFalse);

        private Integer code;
        private String msg;

        MessageCode(@NonNull Integer code, @NonNull String msg) {
            this.code = code;
            this.msg = msg;
        }

        public Integer getCode() { return this.code; }
        public String getMsg() { return this.msg; }
    }

    /**
     * 返回默认的成功请求信息
     * @return 默认的成功请求信息
     */
    public static Map<String, Object> returnTrue() {
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("code", 0);
        returnMap.put("msg", msgTrue);
        return returnMap;
    }

    /**
     * 返回带有自定义的Object型数据的成功请求信息
     * @param msg 自定义的Object型数据
     * @return 成功请求信息，传入的msg将被放置在data字段中
     */
    public static Map<String, Object> returnTrue(Object msg) {
        Map<String, Object> returnMap = returnTrue();
        returnMap.put("data", msg);
        return returnMap;
    }

    /**
     * 返回错误信息
     * @param code 错误代码
     * @return 错误信息，包含错误代码和错误提示
     */
    public static Map<String, Object> returnFalse(MessageCode code) {
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("code", code.getCode());
        returnMap.put("msg", code.getMsg());
        return returnMap;
    }
}
