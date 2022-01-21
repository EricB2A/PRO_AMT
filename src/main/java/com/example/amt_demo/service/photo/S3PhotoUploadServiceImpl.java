package com.example.amt_demo.service.photo;

import com.amazonaws.services.s3.model.S3ObjectSummary;
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
import java.util.List;

@Component
@Qualifier("amazonService")
@AllArgsConstructor
public class S3PhotoUploadServiceImpl implements PhotoUploadService{
    private S3Repository amazonRepository;

    @Override
    public boolean save(MultipartFile file, String location, String filename) {
        return amazonRepository.uploadFile(file, location + filename);
    }

    @Override
    public boolean delete(String filePath) {
        return amazonRepository.deleteFile(filePath);
    }

    @Override
    public boolean deleteFolder(String folderPath) {
        return amazonRepository.deleteFilesWithPrefix(folderPath);
    }

    @Override
    public String getRoot() {
        return null;
    }
}
