package com.natsukashiiz.iiserverapi.mapper;

import com.natsukashiiz.iiserverapi.entity.IIBookmark;
import com.natsukashiiz.iiserverapi.model.response.BookmarkResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookmarkMapper {
    List<BookmarkResponse> findByUid(Long uid);
    Integer save(IIBookmark entity);
    Boolean hasBlogIdAndUid(@Param("blogId") Long blogId, @Param("uid") Long uid);
    Integer remove(@Param("blogId") Long blogId, @Param("uid") Long uid);
}
