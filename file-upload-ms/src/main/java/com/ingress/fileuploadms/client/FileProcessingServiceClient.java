package com.ingress.fileuploadms.client;

import com.ingress.fileuploadms.client.decoder.FeignErrorDecoder;
import com.ingress.fileuploadms.client.encoder.FeignEncoder;
import com.ingress.fileuploadms.model.UserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(
        name = "file-process-service",
        url = "http://localhost:8082",
        configuration = {FeignEncoder.class, FeignErrorDecoder.class})
public interface FileProcessingServiceClient {

    @PostMapping(value = "/process-file/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    void processFile(@PathVariable("id") Long id,
                     @RequestPart("files") List<MultipartFile> files,
                     @RequestPart("request") UserRequest request);

}
