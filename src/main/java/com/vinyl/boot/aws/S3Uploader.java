package com.vinyl.boot.aws;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.vinyl.boot.command.ProdImgVO;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws_target_bucket}")
    private String bucket;

    /*public void uploadImage(List<MultipartFile> multipartFiles) {
        multipartFiles.forEach(file -> {
            try {
                // 파일이 존재하는지 확인
                if (!file.isEmpty()) {
                    // 파일이 존재하면 업로드 수행
                    FileName fileNameResult = createFileName(file.getOriginalFilename());
                    String fileName = fileNameResult.getFileName();
                    String uuid = fileNameResult.getUuid();
                    ObjectMetadata objectMetadata = new ObjectMetadata();
                    objectMetadata.setContentLength(file.getSize());
                    objectMetadata.setContentType(file.getContentType());
                    try (InputStream inputStream = file.getInputStream()) {
                        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                                .withCannedAcl(CannedAccessControlList.PublicRead));
                    }
                }
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드 실패", e);
            }
        });
    }*/
    public void uploadImage(MultipartFile multipartFiles) {
        FileName fileNameResult = createFileName(multipartFiles.getOriginalFilename());
        String filename = fileNameResult.getFileName();
        String uuid = fileNameResult.getUuid();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFiles.getSize());
        objectMetadata.setContentType(multipartFiles.getContentType());
        try(InputStream inputStream = multipartFiles.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, filename, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드 실패", e);
        }
    }


    private FileName createFileName(String fileName) {
        String uuid = UUID.randomUUID().toString();
        String fileNameResult = uuid.concat(getFileExtension(fileName));
        return FileName.builder().fileName(fileNameResult).uuid(uuid).build();
    }

    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
        }
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class FileName {
        private String fileName;
        private String uuid;
    }
}
