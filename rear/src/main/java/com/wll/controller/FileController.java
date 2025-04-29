package com.wll.controller;

import com.wll.enums.ResourcesPath;
import com.wll.pojo.Transfer;
import com.wll.utils.R;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @time 2025/2/3 15:06 周一
 */
@RestController
@RequestMapping("/files")
public class FileController {

    /**
     * @param multipartFile   uploaded file
     * @param pictureRepoType picture repository type
     * @param ID              repository id
     * @param request         http request
     * @return the location where file is stored
     */
    @PostMapping("/upload")
    public synchronized R upload(@RequestParam("blob") MultipartFile multipartFile,
                                 @RequestParam("Picture-Repo-Type") int pictureRepoType,
                                 @RequestParam("ID") String ID,
                                 HttpServletRequest request) {
        long timeMillis = System.currentTimeMillis();
        String fileFullName = "";
        BufferedOutputStream outputStream = null;
        try {
            Path filepath = Paths.get(String.format("%s\\%s", ResourcesPath.values()[pictureRepoType].toString(), ID));
            if (!Files.exists(filepath)) {
                Files.createDirectories(filepath);
            }
            fileFullName = "%s\\%d_%s".formatted(filepath, timeMillis, multipartFile.getOriginalFilename());
            outputStream = new BufferedOutputStream(new FileOutputStream(fileFullName));
            outputStream.write(multipartFile.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (!Objects.isNull(outputStream)) {
                try {
                    outputStream.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }

        String scheme = request.getScheme(); // 获取协议（http 或 https）
        String serverName = request.getServerName(); // 获取服务器地址
        int serverPort = request.getServerPort(); // 获取服务器端口
        String serverAddress = "%s://%s:%d".formatted(scheme, serverName, serverPort);


        return R.successWithLink(timeMillis, "%s%s".formatted(serverAddress
                , fileFullName.substring(fileFullName.indexOf("\\asset"))));
    }


    @PostMapping(value = "/download")
    public synchronized ResponseEntity<FileSystemResource> download(@RequestBody Transfer transfer) {
//        ClassPathResource resource = new ClassPathResource("static".concat(transfer.getPath()));
        transfer.setPath("%s\\src\\main\\resources\\static%s".formatted(System.getProperty("user.dir"), transfer.getPath()));
        // 返回文件流
        FileSystemResource resource = new FileSystemResource(transfer.getPath());

        if (resource.exists()) {
            // 设置 ContentDisposition
            ContentDisposition contentDisposition = ContentDisposition.builder(transfer.getContentDisposition())
                    .filename(resource.getFilename(), StandardCharsets.UTF_8)
                    .build();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set(HttpHeaders.CONTENT_TYPE, transfer.getMediaType());
            httpHeaders.setContentDisposition(contentDisposition);
            return new ResponseEntity<>(resource, httpHeaders, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }
}
