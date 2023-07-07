package com.ingress.fileuploadms.config;

import org.springframework.cloud.openfeign.support.JsonFormWriter;
import org.springframework.context.annotation.Bean;

public class FeignEncoderConfig {

    @Bean
    JsonFormWriter jsonFormWriter() {
        return new JsonFormWriter();
    }

}