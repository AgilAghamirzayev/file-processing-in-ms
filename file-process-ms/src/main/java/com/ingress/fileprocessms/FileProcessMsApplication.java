package com.ingress.fileprocessms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class FileProcessMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileProcessMsApplication.class, args);
    }

}
