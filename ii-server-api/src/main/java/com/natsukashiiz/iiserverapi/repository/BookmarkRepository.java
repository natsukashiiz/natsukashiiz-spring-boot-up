package com.natsukashiiz.iiserverapi.repository;

import com.natsukashiiz.iiserverapi.entity.Blog;
import com.natsukashiiz.iiserverapi.entity.Bookmark;
import com.natsukashiiz.iiserverapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    long countByBlogAndUser(Blog blog, User user);
    List<Bookmark> findAllByUser(User user);
    void deleteByBlogAndUser(Blog blog, User user);
}
