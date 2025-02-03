package com.wll.utils;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @time 2025/2/3 19:56 周一
 */
public class FilesUtils {

    public static boolean isExist(String file){
        return Files.exists(Paths.get(file));
    }
}
