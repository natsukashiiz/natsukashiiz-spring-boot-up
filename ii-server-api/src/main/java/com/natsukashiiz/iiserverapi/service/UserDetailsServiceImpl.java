package com.natsukashiiz.iiserverapi.service;

import com.natsukashiiz.iiboot.configuration.jwt.Authentication;
import com.natsukashiiz.iiboot.configuration.jwt.UserDetailsImpl;
import com.natsukashiiz.iiserverapi.entity.IIUser;
import com.natsukashiiz.iiserverapi.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<IIUser> opt = userMapper.findByUsername(username);
        if (!opt.isPresent()) {
            log.debug("oadUserByUsername-[block](not found). username: {}", username);
            return null;
        }

        IIUser user = opt.get();
        return UserDetailsImpl.build(
                Authentication.builder()
                        .uid(user.getId())
                        .name(user.getUsername())
                        .build()
        );
    }
}
