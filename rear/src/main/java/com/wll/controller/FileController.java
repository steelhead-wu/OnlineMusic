package com.wll.controller;

import com.wll.utils.R;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @time 2025/2/3 15:06 周一
 */
@RestController
@RequestMapping("/files")
public class FileController {

    @PostMapping("/upload")
    public synchronized R upload(@RequestParam(name = "user-avatar") MultipartFile multipartFile) {
        System.out.println("ContentType:" + multipartFile.getContentType());
        System.out.println("name:" + multipartFile.getName());
        System.out.println("OriginalFilename:" + multipartFile.getOriginalFilename());
        System.out.println("ContentType:" + multipartFile.getResource());
        return R.success();
    }
}
