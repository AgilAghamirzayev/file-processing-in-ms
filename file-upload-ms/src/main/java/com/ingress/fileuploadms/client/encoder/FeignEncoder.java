package com.ingress.fileuploadms.client.encoder;

import org.springframework.cloud.openfeign.support.JsonFormWriter;
import org.springframework.context.annotation.Bean;

public class FeignEncoder {
    @Bean
    JsonFormWriter jsonFormWriter() {
        return new JsonFormWriter();
    }
}
