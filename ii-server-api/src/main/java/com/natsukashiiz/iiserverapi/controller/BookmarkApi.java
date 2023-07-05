package com.natsukashiiz.iiserverapi.controller;

import com.natsukashiiz.iiboot.configuration.jwt.UserDetailsImpl;
import com.natsukashiiz.iiserverapi.model.request.BookmarkRequest;
import com.natsukashiiz.iiserverapi.service.BookmarkService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/v1/bookmarks")
public class BookmarkApi {
    @Resource
    private BookmarkService bookmarkService;

    @GetMapping
    public ResponseEntity<?> getSelf(@AuthenticationPrincipal UserDetailsImpl auth) {
        return bookmarkService.getSelf(auth);
    }

    @PostMapping
    public ResponseEntity<?> add(@AuthenticationPrincipal UserDetailsImpl auth, @RequestBody BookmarkRequest request) {
        return bookmarkService.save(auth, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@AuthenticationPrincipal UserDetailsImpl auth, @PathVariable Long id) {
        return bookmarkService.remove(auth, id);
    }
}
