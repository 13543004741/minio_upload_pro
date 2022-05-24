package com.example.minio_upload_pro.config;

import com.example.minio_upload_pro.util.MinioUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MinioConfig
 *
 * @author YuanWang
 * @version 1.0
 * @description minio配置类
 * @date 2022/5/24 14:24
 */
@Configuration
public class MinioConfig {

    /**
     *服务器地址
     */
    @Value("${minio.endpoint}")
    private String endpoint;
    /**
     * 桶名
     */
    @Value("${minio.bucketName}")
    private String bucketName;
    /**
     * 用户名
     */
    @Value("${minio.accessKey}")
    private String accessKey;
    /**
     * 密码
     */
    @Value("${minio.secretKey}")
    private String secretKey;

    @Bean
    public MinioUtils creatMinioClient() {
        return new MinioUtils(endpoint, bucketName, accessKey, secretKey);
    }
}
