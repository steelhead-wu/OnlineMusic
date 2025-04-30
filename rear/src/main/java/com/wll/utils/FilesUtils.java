package com.wll.utils;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.Objects;

/**
 * @time 2025/2/3 19:56 周一
 */
public class FilesUtils {

    public static boolean isExist(String file) {
        return Files.exists(Paths.get(file));
    }


    /**
     * move the file to the position of target.
     * if file exists, the Exception is thrown
     *
     * @param source relative path, a file
     * @param target relative path, it is a folder to store the source
     * @throws IOException
     */
    public static void move(String source, String target) throws IOException {


        // move the position of singer avatar

        Path src_path = Paths.get(source);
        Path tar_path = Paths.get(target);

        if (!Files.exists(tar_path)) {
            Files.createDirectories(tar_path);
        }

        Files.move(src_path, tar_path.resolve(src_path.getFileName()));
    }


    public static String readFile(String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line;
            while (true) {
                line = bufferedReader.readLine();
                if (Objects.isNull(line)) break;
                sb.append(line);
                sb.append('\n');
            }
        }

        return sb.toString();
    }
}
