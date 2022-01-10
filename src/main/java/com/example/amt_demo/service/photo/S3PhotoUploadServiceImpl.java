package com.example.amt_demo.service.photo;

import com.example.amt_demo.utils.aws.S3Repository;
import com.example.amt_demo.utils.aws.Utils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
@Qualifier("amazonService")
@AllArgsConstructor
public class S3PhotoUploadServiceImpl implements PhotoUploadService{
    private S3Repository amazonRepository;
    private static String bucketName = "silkyroad.diduno.education";

    @Override
    public boolean save(MultipartFile file, String location, String filename) {
        amazonRepository.uploadFile(file, location + filename);
        return true;
    }

    @Override
    public boolean delete(String filePath) {
        return false;
    }

    @Override
    public boolean deleteFolder(String folderPath) {
        return false;
    }

    @Override
    public String getRoot() {
        return null;
    }
}
