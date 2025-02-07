package com.wll.controller;

import com.wll.utils.R;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @time 2025/2/3 15:06 周一
 */
@RestController
@RequestMapping("/files")
public class FileController {
    private static final String USER_DIRECTORY = System.getProperty("user.dir");
    private static final String MIDDLE_PATH = "\\src\\main\\resources\\static\\asset\\img\\avatarImages\\";


    @PostMapping("/upload")
    public synchronized R upload(@RequestParam(name = "user-avatar") MultipartFile multipartFile) {

        String filePrefix = USER_DIRECTORY + MIDDLE_PATH;
        Path filePath = Paths.get(filePrefix);
        long timeMillis = System.currentTimeMillis();
        String filename = filePrefix + timeMillis + "_" + multipartFile.getOriginalFilename();
        try (OutputStream outputStream = new FileOutputStream(filename)) {
            if (!Files.exists(filePath)) {
                Files.createDirectories(filePath);
            }
            outputStream.write(multipartFile.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(timeMillis);
    }


    @GetMapping("/{timestamp}")
    public synchronized R download(@PathVariable("timestamp") String timestamp, HttpServletResponse response) {
        return R.success();
    }
}
