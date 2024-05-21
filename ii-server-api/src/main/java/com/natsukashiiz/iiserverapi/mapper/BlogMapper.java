package com.natsukashiiz.iiserverapi.mapper;

import com.natsukashiiz.iicommon.model.Pagination;
import com.natsukashiiz.iiserverapi.entity.IIBlog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BlogMapper {
    Optional<IIBlog> findOne(IIBlog blog);
    Optional<IIBlog> findById(Long id);
    List<IIBlog> findByUid(Long uid);
    List<IIBlog> findBy(@Param("entity") IIBlog entity, @Param("page") Pagination page);
    List<IIBlog> findByUname(@Param("uname") String uname, @Param("page") Pagination page);
    Optional<IIBlog> findByIdWithBookmark(@Param("id") Long id, @Param("uid") Long uid);
    List<IIBlog> findByUnameWithBookmark(@Param("uname") String uname, @Param("uid") Long uid, @Param("page") Pagination page);
    List<IIBlog> findBySelfWithBookmark(@Param("uname") String uname, @Param("uid") Long uid, @Param("page") Pagination page);
    List<IIBlog> findAll(@Param("page") Pagination page);
    List<IIBlog> findAllWithBookmark(@Param("uid") Long uid, @Param("page") Pagination page);
    Integer save(IIBlog blog);
    Integer update(IIBlog blog);
    Integer publish(@Param("id") Long id, @Param("uid") Long uid);
    Boolean hasId(Long id);
    Integer delete(@Param("id") Long id, @Param("uid") Long uid);
    Long count();
    Long countByUname(String uname);
}
