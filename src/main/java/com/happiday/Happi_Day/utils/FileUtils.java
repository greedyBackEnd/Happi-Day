package com.happiday.Happi_Day.utils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.happiday.Happi_Day.exception.CustomException;
import com.happiday.Happi_Day.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileUtils {

    private static final String S3_BUCKET_DIRECTORY_NAME = "static";
    private static final List<String> ALLOWED_FILE_EXTENSIONS = Arrays.asList("png", "jpg", "jpeg", "bmp", "gif");

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadFile(MultipartFile multipartFile) {
        validateFileExtension(multipartFile.getOriginalFilename());

        String fileName = generateFileName(multipartFile.getOriginalFilename());
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, metadata));
        } catch (IOException e) {
            log.error("S3 이미지 업로드 실패 filename : " + multipartFile.getOriginalFilename(), e);
            throw new CustomException(ErrorCode.FILE_UPLOAD_BAD_REQUEST);
        }
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    // 다중 파일 업로드
    public List<String> uploadFiles(List<MultipartFile> multipartFiles) {
        List<String> fileUrls = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            fileUrls.add(uploadFile(multipartFile));
        }

        return fileUrls;
    }

    public S3Object downloadFile(String fileName) {
        return amazonS3.getObject(new GetObjectRequest(bucket, fileName));
    }

    public void deleteFile(String fileName) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }

    private String generateFileName(String originalFilename) {
        String fileExtension = getFileExtension(originalFilename);
        return S3_BUCKET_DIRECTORY_NAME + "/" + UUID.randomUUID() + "." + fileExtension;
    }

    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf(".");
        if (dotIndex < 0) {
            log.error("파일에 확장자가 없음 filename : " + filename);
            throw new CustomException(ErrorCode.FILE_MISSING_EXTENSION);
        }
        return filename.substring(dotIndex + 1);
    }

    private void validateFileExtension(String filename) {
        String fileExtension = getFileExtension(filename);
        if (!ALLOWED_FILE_EXTENSIONS.contains(fileExtension.toLowerCase())) {
            log.error("잘못된 파일 확장자. 이미지만 허용. filename : " + filename);
            throw new CustomException(ErrorCode.FILE_INVALID_EXTENSION);
        }
    }

}
