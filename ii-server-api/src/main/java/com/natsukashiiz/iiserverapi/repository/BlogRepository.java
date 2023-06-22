package com.natsukashiiz.iiserverapi.repository;

import com.natsukashiiz.iiserverapi.entity.Blog;
import com.natsukashiiz.iiserverapi.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    Page<Blog> findByUser(User user, Pageable pageable);
    Optional<Blog> findByIdAndUser(long id, User user);
    Page<Blog> findByPublish(boolean publish,Pageable pageable);
}
