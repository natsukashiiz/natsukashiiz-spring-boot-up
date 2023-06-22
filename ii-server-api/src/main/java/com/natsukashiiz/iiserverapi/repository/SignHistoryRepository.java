package com.natsukashiiz.iiserverapi.repository;

import com.natsukashiiz.iiserverapi.entity.SignHistory;
import com.natsukashiiz.iiserverapi.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignHistoryRepository extends JpaRepository<SignHistory, Long> {
    Page<SignHistory> findByUser(User user, Pageable pageable);
}
