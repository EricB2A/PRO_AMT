/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file PhotoUploadService.java
 *
 * @brief TODO
 */

package com.example.amt_demo.service;

import com.example.amt_demo.utils.FileUploadUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class PhotoUploadService{

    private final String root = "carpet-photos";

    /**
     *
     * @param file
     * @param location
     * @param filename
     */
    public boolean save(MultipartFile file, String location, String filename) {
        try {
            String directoryPath = root + location;
            File f = new File(directoryPath + filename);
            if(!f.exists() && !f.isDirectory()) {
                FileUploadUtils.saveFile(directoryPath, filename, file);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    /**
     *
     * @param filePath
     * @return
     */
    public boolean delete(String filePath){
        return (new File(filePath)).delete();
    }

    public boolean deleteFolder(String folderPath){
        File directory = new File(folderPath);
        File[] allContents = directory.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteFolder(file.getPath());
            }
        }
        return directory.delete();
    }

    /**
     *
     * @return
     */
    public String getRoot(){
        return root;
    }
}