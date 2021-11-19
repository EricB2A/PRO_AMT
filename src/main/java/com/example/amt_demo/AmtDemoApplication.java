package com.example.amt_demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Arrays;

@SpringBootApplication
public class AmtDemoApplication extends SpringBootServletInitializer {

    @Autowired
    private Environment env;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AmtDemoApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(AmtDemoApplication.class, args);

    }

    public static int getMax(int arr[]) {
        return Arrays.stream(arr).max().getAsInt();
    }


}
