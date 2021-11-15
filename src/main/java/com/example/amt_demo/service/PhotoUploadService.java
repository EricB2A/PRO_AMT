package com.example.amt_demo.service;

import com.example.amt_demo.utils.FileUploadUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class PhotoUploadService{

    private final String root = "carpet-photos";

    public void save(MultipartFile file, String location, String filename) {
        try {
            FileUploadUtils.saveFile("carpet-photos" + location, filename, file);
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public boolean delete(String filePath){
        return (new File(filePath)).delete();
    }

    public String getRoot(){
        return root;
    }

}
