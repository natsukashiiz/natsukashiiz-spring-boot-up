package com.natsukashiiz.iiserverapi.service;

import com.natsukashiiz.iiboot.configuration.jwt.Authentication;
import com.natsukashiiz.iiboot.configuration.jwt.JwtService;
import com.natsukashiiz.iiboot.configuration.jwt.UserDetailsImpl;
import com.natsukashiiz.iiboot.configuration.jwt.model.TokenResponse;
import com.natsukashiiz.iicommon.common.ResponseState;
import com.natsukashiiz.iicommon.model.Pagination;
import com.natsukashiiz.iicommon.utils.Comm;
import com.natsukashiiz.iicommon.utils.ResponseUtil;
import com.natsukashiiz.iicommon.utils.ValidateUtil;
import com.natsukashiiz.iiserverapi.entity.SignHistory;
import com.natsukashiiz.iiserverapi.entity.User;
import com.natsukashiiz.iiserverapi.model.request.*;
import com.natsukashiiz.iiserverapi.model.response.UserResponse;
import com.natsukashiiz.iiserverapi.repository.SignHistoryRepository;
import com.natsukashiiz.iiserverapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final SignHistoryRepository historyRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService tokenService;

    public UserService(UserRepository userRepository, SignHistoryRepository historyRepository, PasswordEncoder passwordEncoder, JwtService tokenService) {
        this.userRepository = userRepository;
        this.historyRepository = historyRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public ResponseEntity<?> getMe(UserDetailsImpl auth) {
        Optional<User> opt = userRepository.findByUsername(auth.getUsername());
        if (!opt.isPresent()) {
            return ResponseUtil.error(ResponseState.UNAUTHORIZED);
        }

        User user = opt.get();
        log.info("DATA: {}", user.getBookmarks());
        UserResponse response = buildResponse(user);
        return ResponseUtil.success(response);
    }

    public ResponseEntity<?> signHistory(UserDetailsImpl auth, Pagination paginate) {
        Pageable pageable = Comm.getPaginate(paginate);
        User user = new User();
        user.setId(auth.getId());
        Page<SignHistory> histories = historyRepository.findByUser(user, pageable);
        return ResponseUtil.successList(histories);
    }


    public ResponseEntity<?> update(UserDetailsImpl auth, UpdateUserRequest request) {
        if (ValidateUtil.invalidEmail(request.getEmail())) {
            return ResponseUtil.error(ResponseState.INVALID_EMAIL);
        }
        Optional<User> opt = userRepository.findByUsername(auth.getUsername());
        if (!opt.isPresent()) {
            return ResponseUtil.error(ResponseState.NOT_FOUND);
        }

        User user = opt.get();
        user.setEmail(request.getEmail());
        User save = userRepository.save(user);
        UserResponse response = buildResponse(save);
        return ResponseUtil.success(response);
    }

    public ResponseEntity<?> changePassword(UserDetailsImpl auth, ChangePasswordRequest request) {
        if (ValidateUtil.invalidPassword(request.getCurrentPassword())) {
            return ResponseUtil.error(ResponseState.INVALID_PASSWORD);
        }

        if (ValidateUtil.invalidPassword(request.getNewPassword())) {
            return ResponseUtil.error(ResponseState.INVALID_NEW_PASSWORD);
        }

        if (!Objects.equals(request.getNewPassword(), request.getConfirmPassword())) {
            return ResponseUtil.error(ResponseState.PASSWORD_NOT_MATCH);
        }

        Optional<User> opt = userRepository.findByUsername(auth.getUsername());
        if (!opt.isPresent()) {
            return ResponseUtil.error(ResponseState.NOT_FOUND);
        }

        User user = opt.get();

        // check password
        if (matchPassword(request.getCurrentPassword(), user.getPassword())) {
            return ResponseUtil.error(ResponseState.INVALID_PASSWORD);
        }

        // password encoded
        String passwordEncoded = passwordEncoder.encode(request.getNewPassword());

        user.setPassword(passwordEncoded);
        User save = userRepository.save(user);
        UserResponse response = buildResponse(save);
        return ResponseUtil.success(response);
    }

    public ResponseEntity<?> create(RegisterRequest request) {
        // validate
        if (ValidateUtil.invalidEmail(request.getEmail())) {
            log.debug("Create-[block]:(validate email)");
            return ResponseUtil.error(ResponseState.INVALID_EMAIL);
        }

        if (ValidateUtil.invalidUsername(request.getUsername())) {
            log.debug("Create-[block]:(validate username)");
            return ResponseUtil.error(ResponseState.INVALID_USERNAME);
        }

        if (ValidateUtil.invalidPassword(request.getPassword())) {
            log.debug("Create-[block]:(validate password)");
            return ResponseUtil.error(ResponseState.INVALID_PASSWORD);
        }

        String email = request.getEmail();
        String username = request.getUsername();
        String password = request.getPassword();

        // check email existed
        if (userRepository.existsByEmail(email)) {
            return ResponseUtil.error(ResponseState.EXISTED_EMAIL);
        }

        // check username existed
        if (userRepository.existsByUsername(username)) {
            return ResponseUtil.error(ResponseState.EXISTED_USERNAME);
        }

        // encode password
        String passwordEncoded = passwordEncoder.encode(password);

        // to entity
        User entity = new User();
        entity.setEmail(email);
        entity.setUsername(username);
        entity.setPassword(passwordEncoded);

        // save
        User save = userRepository.save(entity);
        UserResponse response = buildResponse(save);
        return ResponseUtil.success(response);
    }

    public ResponseEntity<?> login(LoginRequest request, HttpServletRequest httpRequest) {

        String ipv4 = Comm.getIpAddress(httpRequest);
        String userAgent = Comm.getUserAgent(httpRequest);

        // validate
        if (ValidateUtil.invalidUsername(request.getUsername())) {
            log.debug("Login-[block]:(validate username)");
            return ResponseUtil.error(ResponseState.INVALID_USERNAME);
        }

        if (ValidateUtil.invalidPassword(request.getPassword())) {
            log.debug("Login-[block]:(validate password)");
            return ResponseUtil.error(ResponseState.INVALID_PASSWORD);
        }

        String username = request.getUsername();
        String password = request.getPassword();

        // check user in db
        Optional<User> opt = userRepository.findByUsername(username);

        if (!opt.isPresent()) {
            log.debug("Login-[block]:(not found)");
            return ResponseUtil.error(ResponseState.INVALID_USERNAME_PASSWORD);
        }

        User user = opt.get();
        if (matchPassword(password, user.getPassword())) {
            log.debug("Login-[block]:(incorrect password)");
            return ResponseUtil.error(ResponseState.INVALID_USERNAME_PASSWORD);
        }

        // save signed history
        SignHistory history = new SignHistory();
        history.setUser(user);
        history.setIpv4(ipv4);
        history.setDevice(Comm.getDeviceType(userAgent));
        history.setUa(userAgent);
        historyRepository.save(history);

        // generate token
        return ResponseUtil.success(this.token(user));
    }

    public ResponseEntity<?> refreshToken(TokenRefreshRequest request) {
        if (Objects.isNull(request.getRefreshToken())) {
            return ResponseUtil.error(ResponseState.INVALID_REQUEST);
        }

        String refreshToken = request.getRefreshToken();
        if (!tokenService.validate(refreshToken)) {
            return ResponseUtil.error(ResponseState.REFRESH_TOKEN_EXPIRE);
        }

        String username = tokenService.getUsername(refreshToken);
        Optional<User> opt = userRepository.findByUsername(username);
        if (!opt.isPresent()) {
            return ResponseUtil.error(ResponseState.UNAUTHORIZED);
        }
        User user = opt.get();
        TokenResponse token = token(user);
        return ResponseUtil.success(token);
    }

    public TokenResponse token(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        return tokenService.generateToken(
                Authentication.builder()
                        .uid(user.getId())
                        .name(user.getUsername())
                        .build()
        );
    }

    public boolean matchPassword(String rawPassword, String encodedPassword) {
        return !passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public UserResponse buildResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .cdt(user.getCdt())
                .build();
    }
}
