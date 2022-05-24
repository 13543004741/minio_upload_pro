package com.example.minio_upload_pro.controller;

import com.example.minio_upload_pro.util.MinioUtils;
import io.minio.*;
import io.minio.errors.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * FileUploaderController
 *
 * @author YuanWang
 * @version 1.0
 * @description 文件上传控制器
 * @date 2022/5/24 14:11
 */
@Api(tags = "minio文件存储")
@RequestMapping("/file")
@RestController
public class FileUploaderController {

    @Resource
    private MinioUtils minioUtils;

    /**
     *
     * @param bucketName 桶名
     * @param file 文件
     * @param objectName 对象名
     * @param contentType 文件类型
     * @return 文件信息
     */
    @ApiOperation(value = "文件上传" , notes = "文件上传")
    @PostMapping("/upload")
    public ResponseEntity<String> upload(String bucketName, MultipartFile file, String objectName, String contentType){
        try {
            minioUtils.putObject(bucketName, file, objectName, contentType);
        } catch (IOException | InvalidKeyException | InvalidResponseException | InsufficientDataException | NoSuchAlgorithmException | ServerException | InternalException | XmlParserException | ErrorResponseException | InvalidBucketNameException | RegionConflictException e) {
            e.printStackTrace();
            System.out.println("程序发生异常！");
            return ResponseEntity.ok(e.getMessage());
        }
        return ResponseEntity.ok(minioUtils.getUrl(bucketName, objectName));
    }

    @ApiOperation(value = "文件下载" , notes = "文件下载")
    @PostMapping("/download")
    public ResponseEntity<Object> download(String bucketName, String objectName){
        ObjectStat objectStat = null;
        try {
            objectStat = minioUtils.statObject(bucketName, objectName);
        } catch (IOException | InvalidKeyException | InvalidResponseException | InsufficientDataException | NoSuchAlgorithmException | ServerException | InternalException | XmlParserException | ErrorResponseException | InvalidBucketNameException e) {
            e.printStackTrace();
            System.out.println("程序发生异常！");
            return ResponseEntity.ok(e.getMessage());
        }
        return ResponseEntity.ok(objectStat);
    }

    @ApiOperation(value = "文件删除" , notes = "文件删除")
    @PostMapping("/delete")
    public void delete(String bucketName, String objectName){
        try {
            minioUtils.removeObject(bucketName, objectName);
        } catch (IOException | InvalidKeyException | InvalidResponseException | InsufficientDataException | NoSuchAlgorithmException | ServerException | InternalException | XmlParserException | ErrorResponseException | InvalidBucketNameException e) {
            e.printStackTrace();
            System.out.println("程序发生异常！");
        }
    }
}
