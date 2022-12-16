package com.web.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

public class FileUtil {

    public static String uploads(String fileUploadPath,MultipartFile file) throws IOException {

        //文件后缀
        String fileSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.')+1);

        //文件名
        String fileName = UUID.randomUUID().toString().replace("-","")+"."+fileSuffix;

        //如果文件夹不存在，先创建文件夹
        File files = new File(fileUploadPath);
        if(!files.exists()){
            files.mkdirs();
        }

        //文件写入
        File descFile = new File(fileUploadPath,fileName);
        //把上传的文件传输到该文件夹
        file.transferTo(descFile);

        //文件url
        String url = "uploads/"+fileName;

        return url;

    }


    public static String uploadFile(String fileUploadPath,MultipartFile file) throws IOException {
        //如果文件夹不存在，先创建文件夹
        File files = new File(fileUploadPath);
        if(!files.exists()){
            files.mkdirs();
        }

        //上传文件全称
        String uploadfilename = file.getOriginalFilename();

        //文件后缀名
        String hzm = uploadfilename.substring(uploadfilename.lastIndexOf("."), uploadfilename.length());

        //上传文件名称
        String filename =  uploadfilename.substring(uploadfilename.lastIndexOf("\\")+1,uploadfilename.length());

        String newfilename = filename.replaceAll(hzm, "");

        //存服务器的文件名称
        String storagename = "";

        storagename= newfilename + hzm;

        File descFile = new File(fileUploadPath + storagename);

        int countc = 1;

        while(true){
            if(descFile.exists()){
                Random r = new Random();
                int cc = r.nextInt(10*countc);
                storagename = newfilename + "("+cc+")"+hzm;

                descFile = new File(fileUploadPath + storagename);

                countc = countc*10;

                continue;
            }

            break;
        }
        //把上传的文件传输到该文件夹
        file.transferTo(descFile);

        //文件url
        String url = "uploads/"+storagename;

        return url;

    }



}
