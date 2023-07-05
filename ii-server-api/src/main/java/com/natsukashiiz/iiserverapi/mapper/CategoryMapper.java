package com.natsukashiiz.iiserverapi.mapper;

import com.natsukashiiz.iiserverapi.entity.IICategory;

import java.util.List;

public interface CategoryMapper {
    List<IICategory> findAll();
    Boolean hasId(Long id);
}
