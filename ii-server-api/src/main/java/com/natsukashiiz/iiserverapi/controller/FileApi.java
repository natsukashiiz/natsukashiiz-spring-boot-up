package com.natsukashiiz.iiserverapi.controller;

import com.natsukashiiz.iiserverapi.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/v1/files")
public class FileApi {

    @Resource
    private FileService fileService;

    @GetMapping("/{file}")
    public ResponseEntity<?> find(@PathVariable String file) {
        return fileService.findImage(file);
    }

    @PostMapping
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        return fileService.uploadImage(file);
    }
}
