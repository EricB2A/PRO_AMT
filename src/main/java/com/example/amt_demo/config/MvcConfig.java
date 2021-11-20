/**
 * @team AMT - Silkyroad
 * @authors Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file MvcConfig.java
 *
 * @brief TODO
 */

package com.example.amt_demo.config;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /**
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        exposeDirectory("carpet-photos", registry);
    }

    /**
     *
     * @param dirName
     * @param registry
     */
    private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get(dirName);
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        if (dirName.startsWith("../")) {
            dirName = dirName.replace("../", "");
        }

        registry.addResourceHandler("/" + dirName + "/**")
                .addResourceLocations("file://"+ uploadPath + "/");

        registry.addResourceHandler("/carpet-photos/**")
                .addResourceLocations("file:carpet-photos/");
    }
}