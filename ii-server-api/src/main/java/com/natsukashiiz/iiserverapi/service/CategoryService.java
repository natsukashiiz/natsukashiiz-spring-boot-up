package com.natsukashiiz.iiserverapi.service;

import com.natsukashiiz.iicommon.utils.ResponseUtil;
import com.natsukashiiz.iiserverapi.entity.Category;
import com.natsukashiiz.iiserverapi.model.response.CategoryResponse;
import com.natsukashiiz.iiserverapi.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public ResponseEntity<?> getAll() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryResponse> response = categories.stream().map(this::buildResponse).collect(Collectors.toList());
        return ResponseUtil.success(response);
    }

    public CategoryResponse buildResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .cdt(category.getCdt())
                .build();
    }
}
