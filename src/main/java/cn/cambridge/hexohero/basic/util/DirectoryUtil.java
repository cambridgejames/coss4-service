package cn.cambridge.hexohero.basic.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取目录及文件信息
 * @author PengJQ
 * @date 2019-12-18
 */
public class DirectoryUtil {

    /**
     * 获取指定目录下的全部目录及子目录信息
     * @param filePath 指定目录的绝对路径
     * @return 目录信息
     */
    public static Map<String, Object> getDirectory(String filePath) {
        File file = new File(filePath);
        return listDirectory(file);
    }

    /**
     * 获取指定目录下的文件信息
     * @param rootDir 根目录
     * @return 文件信息
     */
    private static Map<String, Object> listDirectory(File rootDir) {
        Map<String, Object> resultMap = new HashMap<>();
        File[] files = rootDir.listFiles(); // 获取当前目录下的文件列表
        if (files != null) {
            for(File file : files){
                if(file.isDirectory()){
                    resultMap.put(file.getName(), listDirectory(file));
                }
                else {
                    resultMap.put(file.getName(), file.length());
                }
            }
        }
        return resultMap;
    }
}
