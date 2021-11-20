/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file FileUploadHtils.java
 *
 * @brief TODO
 */

package com.example.amt_demo.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;

public class FileUploadUtils {

    /**
     *
     * @param uploadDir
     * @param fileName
     * @param multipartFile
     * @throws IOException
     */
    public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }
}