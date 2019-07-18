package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
    //图片上传ftp
    String upload(MultipartFile file, String path);

}
