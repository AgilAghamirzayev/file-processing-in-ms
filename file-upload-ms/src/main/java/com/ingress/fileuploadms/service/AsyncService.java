package com.ingress.fileuploadms.service;

import com.ingress.fileuploadms.client.FileProcessingServiceClient;
import com.ingress.fileuploadms.model.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AsyncService {
    private final FileProcessingServiceClient fileProcessingServiceClient;

    @Async
    void processFile(Long id, List<MultipartFile> files, UserRequest request) {
        fileProcessingServiceClient.processFile(id, files, request);
    }

}
