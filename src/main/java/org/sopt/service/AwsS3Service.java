package org.sopt.service;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
@Service
public class AwsS3Service {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    public AwsS3Service(AmazonS3 s3) {
        this.amazonS3 = s3;
    }

    public List<String> uploadFiles(List <MultipartFile> files){
        return files.stream().map(this::uploadFile).toList();
    }


    public String uploadFile(MultipartFile file){
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        try{
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            amazonS3.putObject(bucket,fileName,file.getInputStream(),metadata);

            return amazonS3.getUrl(bucket,fileName).toString();


        } catch (IOException e){
            throw new RuntimeException("S3 업로드 실패: " + fileName, e);
        }
    }
}
