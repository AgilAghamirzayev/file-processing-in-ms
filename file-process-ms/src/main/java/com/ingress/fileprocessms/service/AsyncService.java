package com.ingress.fileprocessms.service;

import com.ingress.fileprocessms.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AsyncService {
    private final S3Service s3Service;

    @Async
    public void putObject(String bucketName, String key, byte[] file) {
        s3Service.putObject(bucketName, key, file);
    }
}
