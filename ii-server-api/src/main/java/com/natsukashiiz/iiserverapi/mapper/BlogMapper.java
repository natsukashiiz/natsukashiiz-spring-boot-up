package com.natsukashiiz.iiserverapi.mapper;

import com.natsukashiiz.iiserverapi.entity.Blog;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface BlogMapper {
    Optional<Blog> findById(Long id);
}
