package com.natsukashiiz.iiserverapi.controller;


import com.natsukashiiz.iiserverapi.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/categories")
public class CategoryApi {
    private final CategoryService service;

    public CategoryApi(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return service.getAll();
    }
}
