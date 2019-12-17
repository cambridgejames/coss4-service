package cn.cambridge.coss.basic.util;

import java.util.HashMap;
import java.util.Map;

public class CommonResultUtil {
    private static String msgTrue = "请求成功";
    private static String msgFalse = "请求失败";

    public static enum MessageCode {
        NO_SUCH_FILE(100, "找不到对应的文件"), OTHER_ERROR(1000, msgFalse);
        private Integer code;
        private String msg;
        MessageCode(Integer code, String msg) { this.code = code; this.msg = msg; }
        public Integer getCode() { return this.code; }
        public String getMsg() { return this.msg; }
    }

    public static Map<String, Object> returnTrue() {
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("code", 0);
        returnMap.put("msg", msgTrue);
        return returnMap;
    }

    public static Map<String, Object> returnTrue(String msg) {
        Map<String, Object> returnMap = returnTrue();
        returnMap.put("data", msg);
        return returnMap;
    }

    public static Map<String, Object> returnTrue(Map<String, Object> msg) {
        Map<String, Object> returnMap = returnTrue();
        returnMap.put("data", msg);
        return returnMap;
    }

    public static Map<String, Object> returnFalse(MessageCode code) {
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("code", code.getCode());
        returnMap.put("msg", code.getMsg());
        return returnMap;
    }
}
