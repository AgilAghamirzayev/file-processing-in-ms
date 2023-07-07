package com.ingress.fileuploadms.repo;

import com.ingress.fileuploadms.entity.UploadFileInfos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadedFileRepository extends JpaRepository<UploadFileInfos, Long> {
}
