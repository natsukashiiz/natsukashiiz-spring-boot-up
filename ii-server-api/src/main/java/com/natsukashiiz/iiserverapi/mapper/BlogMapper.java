package com.natsukashiiz.iiserverapi.mapper;

import com.natsukashiiz.iicommon.model.Pagination;
import com.natsukashiiz.iiserverapi.entity.Blog;
import com.natsukashiiz.iiserverapi.model.response.BlogResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BlogMapper {
    List<Blog> findAll();

    Optional<Blog> findById(Long id);

    List<Blog> findByUid(Long uid);

    List<Blog> findBy(@Param("entity") Blog entity, @Param("page") Pagination page);

    List<BlogResponse> findWithBookmark(Long uid);
}
