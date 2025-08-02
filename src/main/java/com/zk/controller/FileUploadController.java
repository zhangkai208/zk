package com.zk.controller;

import com.zk.pojo.Result;
import com.zk.utils.AliOssUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @program: big-event
 * @description:
 * @author: 张恺
 * @create: 2025-07-17 14:05
 * @version: 1.0
 **/
@RestController
public class FileUploadController {
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String filename = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
        //把文件存到本地
        /*file.transferTo(new File("C:\\Users\\张恺\\Desktop\\big-event\\file\\" + filename));*/
        String url = AliOssUtils.main(filename,file.getInputStream());
        return Result.success(url);
    }

}