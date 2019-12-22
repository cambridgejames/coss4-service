package cn.cambridge.hexohero.basic.util;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取目录及文件信息
 * @author PengJQ
 * @date 2019-12-18
 */
@Component
public class DirectoryUtil {

    /**
     * 获取指定目录下的全部目录及子目录信息
     * @param filePath 指定目录的绝对路径
     * @return 目录信息
     */
    public static Map<String, Object> getDirectory(String filePath) {
        File rootDir = new File(filePath);
        if(!rootDir.isDirectory()) {
            return new HashMap<>();
        }
        return listDirectory(rootDir);
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

    /**
     * 清空指定目录
     * @param filePath 指定要清空的目录
     * @return 是否清空成功
     */
    public static boolean clearDirectory(String filePath) {
        File rootDir = new File(filePath);
        if(!rootDir.isDirectory()) {
            return false;
        }
        File[] files = rootDir.listFiles(); // 获取当前目录下的文件列表
        if (files != null) {
            for(File file : files){
                if (!deleteFile(file)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 删除指定文件或目录
     * @param file 要删除的文件或目录
     * @return 是否删除成功
     */
    public static boolean deleteFile(File file) {
        if(file == null || !file.exists()) {
            return false;
        }
        if(file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    deleteFile(f);
                }
            }
        }
        return file.delete();
    }
}
