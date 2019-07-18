package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service("iFileService")
public class IFileServiceImpl implements IFileService {
    private Logger logger = LoggerFactory.getLogger(IFileServiceImpl.class);
    public  String upload(MultipartFile file,String path){
        String fileName =  file.getOriginalFilename();
        //扩展名
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
        logger.info("开始文件上传,文件名是:{},上传路径是:{},新文件名是{}",fileName,path,uploadFileName);
        File filedir  = new File(path);
        if(!filedir.exists()){
            filedir.setWritable(true);
            filedir.mkdirs();
        }
        File targetFile = new File(path,uploadFileName);

        try {
            //文件上传成功了
            file.transferTo(targetFile);
            //将targetFile 上传到ftp服务器
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            // 成功上传之后,将upload文件夹下的删除
            targetFile.delete();
        } catch (IOException e) {
           logger.error("上传文件异常：",e);
           return null;
        }
        return targetFile.getName();
    }
}
