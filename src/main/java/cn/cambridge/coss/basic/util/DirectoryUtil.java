package cn.cambridge.coss.basic.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DirectoryUtil {

    public static Map<String, Object> getDirectory(String filePath) {
        File dir = new File(filePath);
        return listDir(dir);
    }

    private static Map<String, Object> listDir(File dir) {
        Map<String, Object> resultMap = new HashMap<>();
        File[] files = dir.listFiles();
        if (files != null) {
            for(File f : files){
                if(f.isDirectory()){
                    resultMap.put(f.getName(), listDir(f));
                }
                else {
                    resultMap.put(f.getName(), f.length());
                }
            }
        }
        return resultMap;
    }
}
