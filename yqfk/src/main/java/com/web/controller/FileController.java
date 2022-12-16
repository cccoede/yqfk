package com.web.controller;



import com.web.utils.FileUtil;
import com.web.utils.Permission;
import com.web.utils.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

/**
 * 文件上传
 */

@RestController
@RequestMapping("/file")
public class FileController {

    //将yml配置文件的路径赋值给fileUploadPath
    @Value("${files.upload.path}")
    private String fileUploadPath;


    /**
     * 图片上传接口
     */
    @PostMapping("/upload/img")
    public Result upload(@RequestParam("file") MultipartFile file) throws IOException {

        String url = FileUtil.uploads(fileUploadPath,file);
        Result result = new Result();
        result.put("url", url);
        return result;
    }

    //文件上传
    @PostMapping("/upload/file")
    public Result file(@RequestParam("file") MultipartFile file) throws IOException {

        String url = FileUtil.uploadFile(fileUploadPath,file);
        Result result = new Result();
        result.put("url", url);
        result.put("name", file.getOriginalFilename());
        return result;
    }

}



