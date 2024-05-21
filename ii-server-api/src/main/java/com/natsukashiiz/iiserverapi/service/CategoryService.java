package com.natsukashiiz.iiserverapi.service;

import com.natsukashiiz.iicommon.utils.ResponseUtil;
import com.natsukashiiz.iiserverapi.entity.IICategory;
import com.natsukashiiz.iiserverapi.mapper.CategoryMapper;
import com.natsukashiiz.iiserverapi.model.response.CategoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Resource private CategoryMapper categoryMapper;

    public ResponseEntity<?> getAll() {
        List<IICategory> list = categoryMapper.findAll();
        return ResponseUtil.successList(buildResponseList(list));
    }

    public static CategoryResponse buildResponse(IICategory data) {
        return CategoryResponse.builder()
                .id(data.getId())
                .name(data.getName())
                .build();
    }

    public List<CategoryResponse> buildResponseList(List<IICategory> list) {
        return list.stream().map(CategoryService::buildResponse).collect(Collectors.toList());
    }
}
