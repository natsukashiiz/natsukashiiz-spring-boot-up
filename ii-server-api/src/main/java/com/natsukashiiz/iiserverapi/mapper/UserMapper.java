package com.natsukashiiz.iiserverapi.mapper;

import com.natsukashiiz.iiserverapi.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    Optional<User> findOne(User user);
    Optional<User> findById(Long id);
    Boolean hasId(Long id);
    Boolean hasEmail(String email);
    Boolean hasUsername(String username);
    Integer save(User user);
    Integer update(User user);
}
