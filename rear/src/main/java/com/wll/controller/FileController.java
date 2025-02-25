package com.wll.controller;

import com.wll.enums.ResourcesPath;
import com.wll.utils.R;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @time 2025/2/3 15:06 周一
 */
@RestController
@RequestMapping("/files")
public class FileController {

    @PostMapping("/upload")
    public synchronized R upload(@RequestParam("blob") MultipartFile multipartFile,
                                 @RequestParam("Picture-Repo-Type") int pictureRepoType,
                                 @RequestParam("User-ID") String userID,
                                 HttpServletRequest request) {
        long timeMillis = System.currentTimeMillis();
        String fileFullName = "";
        try {
            Path filepath = Paths.get(ResourcesPath.values()[pictureRepoType].toString() + "\\" + userID);
            if (!Files.exists(filepath)) {
                Files.createDirectories(filepath);
            }
            fileFullName = filepath + "\\" + timeMillis + "_" + multipartFile.getOriginalFilename();
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(fileFullName));
            outputStream.write(multipartFile.getBytes());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String scheme = request.getScheme(); // 获取协议（http 或 https）
        String serverName = request.getServerName(); // 获取服务器地址
        int serverPort = request.getServerPort(); // 获取服务器端口
        String serverAddress = scheme + "://" + serverName + ":" + serverPort;


        return R.successWithLink(timeMillis, serverAddress + fileFullName.substring(fileFullName.indexOf("\\asset")));
    }


    @GetMapping("/{timestamp}")
    public synchronized R download(@PathVariable("timestamp") String timestamp, HttpServletResponse response) {
        return R.success();
    }
}
