package com.natsukashiiz.iiserverapi.controller;

import com.natsukashiiz.iiboot.configuration.jwt.UserDetailsImpl;
import com.natsukashiiz.iicommon.model.Pagination;
import com.natsukashiiz.iiserverapi.model.request.ChangePasswordRequest;
import com.natsukashiiz.iiserverapi.model.request.UpdateUserRequest;
import com.natsukashiiz.iiserverapi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
public class UserApi {
    private final UserService service;

    public UserApi(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> get(@AuthenticationPrincipal UserDetailsImpl auth) {
        return service.getMe(auth);
    }

    /**
     * {baseUrl}/signedHistory?page=1&limit=3&sortBy=cdt&sortType=desc
     */
    @GetMapping("/signedHistory")
    public ResponseEntity<?> signedHistory(@AuthenticationPrincipal UserDetailsImpl auth, Pagination pagination) {
        return service.signedHistory(auth, pagination);
    }

    @PatchMapping
    public ResponseEntity<?> update(@AuthenticationPrincipal UserDetailsImpl auth, @RequestBody UpdateUserRequest request) {
        return service.update(auth, request);
    }

    @PatchMapping("/password")
    public ResponseEntity<?> changePassword(@AuthenticationPrincipal UserDetailsImpl auth, @RequestBody ChangePasswordRequest request) {
        return service.changePassword(auth, request);
    }
}
