package com.ingress.fileuploadms.controller;

import com.ingress.fileuploadms.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("upload-file")
@RequiredArgsConstructor
public class FileUploadController {
    private final FileUploadService fileUploadService;

    @PostMapping(path = "/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(
            @PathVariable Long id,
            @RequestPart("files") List<MultipartFile> files) {
        fileUploadService.uploadFile(id, files);
        return ResponseEntity.ok("File uploaded successfully");
    }
}
