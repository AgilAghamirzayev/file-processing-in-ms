package com.ingress.fileprocessms.repo;

import com.ingress.fileprocessms.entity.ProcessedFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessedFileRepository extends JpaRepository<ProcessedFile, Long> {
}
