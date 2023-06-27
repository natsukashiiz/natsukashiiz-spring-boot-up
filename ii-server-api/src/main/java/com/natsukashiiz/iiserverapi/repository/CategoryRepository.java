package com.natsukashiiz.iiserverapi.repository;

import com.natsukashiiz.iiserverapi.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
