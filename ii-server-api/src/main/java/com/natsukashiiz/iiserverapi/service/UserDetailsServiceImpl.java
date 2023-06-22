package com.natsukashiiz.iiserverapi.service;

import com.natsukashiiz.iiboot.configuration.jwt.Authentication;
import com.natsukashiiz.iiboot.configuration.jwt.UserDetailsImpl;
import com.natsukashiiz.iiserverapi.entity.User;
import com.natsukashiiz.iiserverapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> opt = userRepository.findByUsername(username);
        if (!opt.isPresent()) {
            log.debug("UserDetailsServiceImpl-[loadUserByUsername](not found)");
            return null;
        }

        User user = opt.get();
        return UserDetailsImpl.build(
                Authentication.builder()
                        .uid(user.getId())
                        .name(user.getUsername())
                        .build()
        );
    }
}
