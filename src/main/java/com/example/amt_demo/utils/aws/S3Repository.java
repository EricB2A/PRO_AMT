package com.example.amt_demo.utils.aws;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class S3Repository {
    Logger logger = LoggerFactory.getLogger(S3Repository.class);

    private AmazonS3 s3client;

    private String bucketName = "silkyroad.diduno.education";

    @Autowired
    public S3Repository(@Value("${com.example.amt_demo.config.aws.access}") String accessKey, @Value("${com.example.amt_demo.config.aws.secret}") String secretKey, @Value("${com.example.amt_demo.config.aws.region}") String region) {
        logger.info(accessKey);
        logger.info(secretKey);
        AWSCredentials creds = new BasicAWSCredentials(accessKey, secretKey);
        this.s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(creds))
                .withRegion(region)
                .build();
    }

    public AmazonS3 getClient() {
        return this.s3client;
    }

    @Async
    public byte[] downloadFile(final String keyName) {
        byte[] content = null;
        S3Object s3Object = s3client.getObject(bucketName, keyName);
        final S3ObjectInputStream stream = s3Object.getObjectContent();
        try {
            content = IOUtils.toByteArray(stream);
            s3Object.close();
        } catch(final IOException ex) {
            System.out.println(ex.getMessage());
        }
        return content;
    }

    @Async
    public boolean deleteFile(String key) {
        try {
            s3client.deleteObject(bucketName, key);
            return true;
        } catch (AmazonServiceException e) {
            return false;
        }
    }

    @Async
    public boolean deleteFilesWithPrefix(String prefix) {
        List<S3ObjectSummary> objects = s3client.listObjects(bucketName, prefix).getObjectSummaries();
        List<DeleteObjectsRequest.KeyVersion> keys = new ArrayList<>();
        for(S3ObjectSummary summary : objects) {
            keys.add(new DeleteObjectsRequest.KeyVersion (summary.getKey()));
        }
        if(keys.isEmpty()) return true;
        try {
            DeleteObjectsRequest deleteRequest = new DeleteObjectsRequest(bucketName)
                    .withKeys(keys)
                    .withQuiet(false);
            s3client.deleteObjects(deleteRequest);
            return true;
        } catch (AmazonServiceException e) {
            return false;
        }
    }

    @Async
    public boolean uploadFile(final MultipartFile multipartFile, String key) {
        try {
            final File file = convertMultiPartFileToFile(multipartFile);
            s3client.putObject(bucketName, key, file);
            file.delete();
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }



    private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
        final File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (final IOException ex) {
            System.out.println(ex.getMessage());
        }
        return file;
    }




}
