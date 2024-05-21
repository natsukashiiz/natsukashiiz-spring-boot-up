package com.natsukashiiz.iiserverapi.controller;

import com.natsukashiiz.iiserverapi.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/v1/categories")
public class CategoryApi {
    @Resource private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return categoryService.getAll();
    }
}
