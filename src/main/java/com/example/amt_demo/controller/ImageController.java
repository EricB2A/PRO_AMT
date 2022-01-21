package com.example.amt_demo.controller;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.util.IOUtils;
import com.example.amt_demo.utils.aws.S3Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


@RestController
public class ImageController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private S3Repository repository;

    @GetMapping(
            value="/image/{folder}/{image}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@PathVariable String folder, @PathVariable String image, HttpServletResponse response) throws IOException {
        byte[] content = new byte[0];
        try {
            content = repository.downloadFile(folder + "/" + image);
        } catch (AmazonS3Exception e) {
            response.sendRedirect("/images/placeholder-image.png");
        }
        return content;
    }
}
