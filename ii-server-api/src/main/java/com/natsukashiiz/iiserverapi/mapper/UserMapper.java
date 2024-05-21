package com.natsukashiiz.iiserverapi.mapper;

import com.natsukashiiz.iiserverapi.entity.IIUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    Optional<IIUser> findOne(IIUser user);

    Optional<IIUser> findById(Long id);
    Optional<IIUser> findByUsername(String username);

    Boolean hasId(Long id);

    Boolean hasEmail(String email);

    Boolean hasUsername(String username);

    Integer save(IIUser user);

    Integer update(IIUser user);
}
