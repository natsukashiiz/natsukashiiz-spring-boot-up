package com.natsukashiiz.iiserverapi.service;

import com.natsukashiiz.iicommon.common.ResponseState;
import com.natsukashiiz.iicommon.utils.ResponseUtil;
import com.natsukashiiz.iicommon.utils.ValidateUtil;
import com.natsukashiiz.iiserverapi.model.response.FileResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class FileService {

    private final String rootPath = "C:/uploads/";

    public ResponseEntity<?> findImage(String file) {
        if (Objects.isNull(file)) {
            log.warn("uploadImage-[block](file is empty)");
            return ResponseUtil.error(ResponseState.INVALID_FILE);
        }

        // Resolve the file path
        Path filePath = Paths.get(this.rootPath, file);

        // Load the file resource
        Resource resource = new FileSystemResource(filePath.toString());


        // Check if the file exists
        if (resource.exists()) {
            // Build the response entity
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } else {
            log.warn("FindImage-[block](file not found). full: {}", resource);
            return ResponseUtil.unknown();
        }

    }

    public ResponseEntity<?> uploadImage(MultipartFile file) {
        if (file.isEmpty()) {
            log.warn("UploadImage-[block](file is empty). file: {}", file);
            return ResponseUtil.error(ResponseState.INVALID_FILE);
        }

        String type = this.getFileType(file);
        if (ValidateUtil.invalidImage(type)) {
            log.warn("UploadImage-[block](validation file type). file: {}, type: {}", file, type);
            return ResponseUtil.error(ResponseState.INVALID_FILE);
        }

        String upload = this.upload(file);
        if (Objects.isNull(upload)) {
            return ResponseUtil.unknown();
        }

        return ResponseUtil.success(
                FileResponse.builder()
                        .file(upload)
                        .build()
        );
    }

    public String upload(MultipartFile file) {
        this.initDirectory();
        try {
            // Get the original file name
            String newFileName = this.randomFileName(getFileType(file));

            // Resolve the target file path
            Path targetPath = Paths.get(this.rootPath, newFileName);

            // Copy the uploaded file to the target path
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            return newFileName;
        } catch (IOException e) {
            return null;
        }
    }

    private void initDirectory() {
        File dir = new File(this.rootPath);
        if (!dir.exists()) {
            dir.mkdirs();
            dir.canWrite();
            dir.canRead();
        }
    }

    private String getFileType(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        if (originalFileName != null) {
            int dotIndex = originalFileName.lastIndexOf('.');
            if (dotIndex > 0 && dotIndex < originalFileName.length() - 1) {
                return originalFileName.substring(dotIndex + 1).toLowerCase();
            }
        }
        return null;
    }

    private String randomFileName(String fileType) {
        return UUID.randomUUID() + "." + fileType;
    }
}
