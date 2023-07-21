package com.natsukashiiz.iiserverapi.mapper;

import com.natsukashiiz.iicommon.model.Pagination;
import com.natsukashiiz.iiserverapi.entity.IISignHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SignHistoryMapper {
    List<IISignHistory> findByUid(@Param("uid") Long uid,@Param("page") Pagination page);
    Integer save(IISignHistory entity);
    Long countByUid(Long uid);
}
