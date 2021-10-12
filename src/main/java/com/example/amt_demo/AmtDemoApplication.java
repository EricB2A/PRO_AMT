package com.example.amt_demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

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

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return agrs ->{
            System.out.println("Let's inspect the beans provided by Spring boot");
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for(String beanName : beanNames)
            {
                System.out.println(beanName);
            }
            System.out.println("End of Run");

        };
    }

    public static int getMax(int arr[]) {
        return Arrays.stream(arr).max().getAsInt();
    }


}
