package com.example.amt_demo.service.photo;

import org.springframework.web.multipart.MultipartFile;

public interface PhotoUploadService {
    boolean save(MultipartFile file, String location, String filename);
    boolean delete(String filePath);
    boolean deleteFolder(String folderPath);
    String getRoot();
}
