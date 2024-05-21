package com.natsukashiiz.iiserverapi.controller;

import com.natsukashiiz.iiboot.configuration.jwt.UserDetailsImpl;
import com.natsukashiiz.iicommon.model.Pagination;
import com.natsukashiiz.iiserverapi.mapper.BlogMapper;
import com.natsukashiiz.iiserverapi.model.request.BlogRequest;
import com.natsukashiiz.iiserverapi.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/blog")
@Slf4j
public class BlogApi {
    private final BlogService blogService;

    public BlogApi(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping
    public ResponseEntity<?> getAll(@AuthenticationPrincipal UserDetailsImpl auth, Pagination pagination) {
        return blogService.getAll(auth, pagination);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@AuthenticationPrincipal UserDetailsImpl auth, @PathVariable Long id) {
        return blogService.getById(auth, id);
    }

    @GetMapping("/u/{uname}")
    public ResponseEntity<?> getByUsername(@AuthenticationPrincipal UserDetailsImpl auth, @PathVariable String uname, Pagination pagination) {
        return blogService.getByUser(auth, uname, pagination);
    }

    @PostMapping
    public ResponseEntity<?> create(
            @AuthenticationPrincipal UserDetailsImpl auth,
            @RequestBody BlogRequest request
    ) {
        return blogService.create(auth, request);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(
            @AuthenticationPrincipal UserDetailsImpl auth,
            @PathVariable Long id,
            @RequestBody BlogRequest request
    ) {
        return blogService.update(auth, id, request);
    }

    @PatchMapping("/{id}/publish")
    public ResponseEntity<?> publish(
            @AuthenticationPrincipal UserDetailsImpl auth,
            @PathVariable Long id
    ) {
        return blogService.publish(auth, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @AuthenticationPrincipal UserDetailsImpl auth,
            @PathVariable Long id
    ) {
        return blogService.delete(auth, id);
    }
}
