package com.ingress.fileprocessms.controller;

import com.ingress.fileprocessms.model.UploadFileRequest;
import com.ingress.fileprocessms.service.FileProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("process-file")
@RequiredArgsConstructor
public class FileProcessingController {
    private final FileProcessingService fileProcessingService;

    @PostMapping(path = "/{id}", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<String> processFile(@PathVariable Long id,
                                              @RequestPart("files") List<MultipartFile> files,
                                              @RequestPart("request") UploadFileRequest request) {

        fileProcessingService.processFile(id, files, request);
        return ResponseEntity.ok("File processed successfully");

    }
}
