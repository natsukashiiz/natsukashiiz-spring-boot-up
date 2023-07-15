package com.natsukashiiz.iiserverapi.service;

import com.natsukashiiz.iiboot.configuration.jwt.Authentication;
import com.natsukashiiz.iiboot.configuration.jwt.JwtService;
import com.natsukashiiz.iiboot.configuration.jwt.UserDetailsImpl;
import com.natsukashiiz.iiboot.configuration.jwt.model.TokenResponse;
import com.natsukashiiz.iicommon.common.CommonState;
import com.natsukashiiz.iicommon.common.ResponseState;
import com.natsukashiiz.iicommon.model.Pagination;
import com.natsukashiiz.iicommon.utils.Comm;
import com.natsukashiiz.iicommon.utils.ResponseUtil;
import com.natsukashiiz.iicommon.utils.ValidateUtil;
import com.natsukashiiz.iiserverapi.entity.IISignHistory;
import com.natsukashiiz.iiserverapi.entity.IIUser;
import com.natsukashiiz.iiserverapi.mapper.SignHistoryMapper;
import com.natsukashiiz.iiserverapi.mapper.UserMapper;
import com.natsukashiiz.iiserverapi.model.request.*;
import com.natsukashiiz.iiserverapi.model.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private JwtService tokenService;
    @Resource
    private UserMapper userMapper;

    @Resource
    private SignHistoryMapper signHistoryMapper;

    @Resource
    private FileService fileService;

    public ResponseEntity<?> getMe(UserDetailsImpl auth) {
        IIUser user = userMapper.findById(auth.getId()).get();
        UserResponse response = buildResponse(user);
        return ResponseUtil.success(response);
    }

    public ResponseEntity<?> signHistory(UserDetailsImpl auth, Pagination paginate) {
        List<IISignHistory> histories = signHistoryMapper.findByUid(auth.getId(), paginate);
        return ResponseUtil.successList(histories);
    }


    public ResponseEntity<?> update(UserDetailsImpl auth, UpdateUserRequest request) {
        if (ValidateUtil.invalidEmail(request.getEmail())) {
            return ResponseUtil.error(ResponseState.INVALID_EMAIL);
        }

        IIUser user = userMapper.findById(auth.getId()).get();
        user.setEmail(request.getEmail());
        userMapper.update(user);
        UserResponse response = buildResponse(user);
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

        IIUser user = userMapper.findById(auth.getId()).get();

        // check password
        if (matchPassword(request.getCurrentPassword(), user.getPassword())) {
            return ResponseUtil.error(ResponseState.INVALID_PASSWORD);
        }

        // password encoded
        String passwordEncoded = passwordEncoder.encode(request.getNewPassword());

        user.setPassword(passwordEncoded);
        userMapper.update(user);
        UserResponse response = buildResponse(user);
        return ResponseUtil.success(response);
    }

    public ResponseEntity<?> changeAvatar(UserDetailsImpl auth, MultipartFile file,HttpServletRequest servlet) {
        if (Objects.isNull(file)) {
            return ResponseUtil.error(ResponseState.INVALID_FILE);
        }

        IIUser user = userMapper.findById(auth.getId()).get();
        String upload = this.fileService.upload(file);
        if (Objects.isNull(upload)) {
            log.debug("ChangeAvatar-[block]:(upload null)");
            return ResponseUtil.error(ResponseState.INVALID_FILE);
        }

        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(servlet)
                .replacePath(null)
                .build()
                .toUriString();

        user.setAvatar(baseUrl+"/v1/files/"+upload);
        userMapper.update(user);
        UserResponse response = buildResponse(user);
        return ResponseUtil.success(response);
    }

    public ResponseEntity<?> removeAvatar(UserDetailsImpl auth) {
        IIUser user = userMapper.findById(auth.getId()).get();
        user.setAvatar("");
        userMapper.update(user);
        UserResponse response = buildResponse(user);
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
        if (userMapper.hasEmail(email)) {
            return ResponseUtil.error(ResponseState.EXISTED_EMAIL);
        }

        // check username existed
        if (userMapper.hasUsername(username)) {
            return ResponseUtil.error(ResponseState.EXISTED_USERNAME);
        }

        // encode password
        String passwordEncoded = passwordEncoder.encode(password);

        // to entity
        IIUser user = new IIUser();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(passwordEncoded);
        user.setState(CommonState.NORMAL.getValue());

        // save
        userMapper.save(user);
        UserResponse response = buildResponse(user);
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
        Optional<IIUser> opt = userMapper.findOne(new IIUser().setUsername(username));

        if (!opt.isPresent()) {
            log.debug("Login-[block]:(not found)");
            return ResponseUtil.error(ResponseState.INVALID_USERNAME_PASSWORD);
        }

        IIUser user = opt.get();
        if (matchPassword(password, user.getPassword())) {
            log.debug("Login-[block]:(incorrect password)");
            return ResponseUtil.error(ResponseState.INVALID_USERNAME_PASSWORD);
        }

        // save signed history
        IISignHistory history = new IISignHistory();
        history.setIpv4(ipv4);
        history.setDevice(Comm.getDeviceType(userAgent));
        history.setUa(userAgent);
        history.setUid(user.getId());

        signHistoryMapper.save(history);

        // generate token
        return ResponseUtil.success(this.token(user));
    }

    public ResponseEntity<?> refreshToken(TokenRefreshRequest request) {
        if (Objects.isNull(request.getRefreshToken())) {
            return ResponseUtil.error(ResponseState.INVALID_REFRESH_TOKEN);
        }

        String refreshToken = request.getRefreshToken();
        if (!tokenService.validate(refreshToken)) {
            return ResponseUtil.error(ResponseState.REFRESH_TOKEN_EXPIRE);
        }

        String username = tokenService.getUsername(refreshToken);
        Optional<IIUser> opt = userMapper.findOne(new IIUser().setUsername(username));
        if (!opt.isPresent()) {
            return ResponseUtil.error(ResponseState.UNAUTHORIZED);
        }
        IIUser user = opt.get();
        TokenResponse token = token(user);
        return ResponseUtil.success(token);
    }

    public TokenResponse token(IIUser user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        return tokenService.generateToken(
                Authentication.builder()
                        .uid(user.getId())
                        .name(user.getUsername())
                        .email(user.getEmail())
                        .avatar(user.getAvatar())
                        .build()
        );
    }

    public boolean matchPassword(String rawPassword, String encodedPassword) {
        return !passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public UserResponse buildResponse(IIUser user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .cdt(user.getCdt())
                .build();
    }
}
