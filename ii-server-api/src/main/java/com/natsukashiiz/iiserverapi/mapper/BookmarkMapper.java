package com.natsukashiiz.iiserverapi.mapper;

import com.natsukashiiz.iiserverapi.entity.IIBookmark;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookmarkMapper {
    List<IIBookmark> findByUid(Long uid);
    Integer save(IIBookmark entity);
    Boolean hasIdAndUid(@Param("id") Long id, @Param("uid") Long uid);
    Integer remove(@Param("id") Long id, @Param("uid") Long uid);
}
