package com.ingress.fileuploadms.service;

import com.ingress.fileuploadms.client.FileProcessingServiceClient;
import com.ingress.fileuploadms.entity.UploadFileInfos;
import com.ingress.fileuploadms.model.UserRequest;
import com.ingress.fileuploadms.repo.UploadedFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class FileUploadService {
    private final UploadedFileRepository uploadedFileRepository;
    private final FileProcessingServiceClient clientService;

    @Transactional
    public void uploadFile(Long userId, List<MultipartFile> files) {
        log.info("uploadFile().started with {} file(s) user-id: {}", files.size(), userId);

        List<UploadFileInfos> uploadFileInfos = new ArrayList<>();

        files.forEach(file ->
                uploadFileInfos.add(UploadFileInfos.builder()
                        .originalFileName(file.getOriginalFilename())
                        .size(file.getSize())
                        .contentType(file.getContentType())
                        .build()));

        uploadedFileRepository.saveAll(uploadFileInfos);

        log.info("uploadFile() started processing files");
        clientService.processFile(userId, files, UserRequest.builder()
                .name("User name")
                .description("Test description")
                .build());
        log.info("uploadFile() ended processing files");

        log.info("uploadFile().ended with {} file(s) user-id: {}", files.size(), userId);

    }
}
