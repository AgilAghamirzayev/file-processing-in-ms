package com.ingress.fileprocessms.service;


import com.ingress.fileprocessms.entity.ProcessedFile;
import com.ingress.fileprocessms.model.UploadFileRequest;
import com.ingress.fileprocessms.repo.ProcessedFileRepository;
import com.ingress.fileprocessms.s3.S3Buckets;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Log4j2
@Service
@RequiredArgsConstructor
public class FileProcessingService {
    private final ProcessedFileRepository processedFileRepository;
    private final S3Buckets s3Buckets;
    private final AsyncService asyncService;

    @Transactional
    public void processFile(Long id, List<MultipartFile> files, UploadFileRequest request) {

        log.info("processFile().start with user id: {}", id);

        ProcessedFile processedFile = new ProcessedFile();
        processedFile.setName(request.getName());
        processedFile.setDescription(request.getDescription());
        processedFileRepository.save(processedFile);

//        List<CompletableFuture<MultipartFile>> futureList = files.stream()
//                .map(file -> CompletableFuture.supplyAsync(() -> file))
//                .toList();
//
//        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0])).join();

        files.forEach(file -> {
            String fileId = UUID.randomUUID().toString();
            try {
                asyncService.putObject(
                        s3Buckets.getFileBucket(),
                        "files/%s/%s".formatted(id, fileId),
                        file.getBytes()
                );
                log.info("File saved with id: {}", fileId);
            } catch (IOException e) {
                log.error("Failed to upload file {}", e.getMessage());
                throw new RuntimeException("Failed to upload file");
            }
        });

    }
}
