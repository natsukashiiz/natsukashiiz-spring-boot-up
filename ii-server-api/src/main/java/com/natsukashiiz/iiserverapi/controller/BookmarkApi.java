package com.natsukashiiz.iiserverapi.controller;

import com.natsukashiiz.iiboot.configuration.jwt.UserDetailsImpl;
import com.natsukashiiz.iiserverapi.model.request.BookmarkRequest;
import com.natsukashiiz.iiserverapi.service.BookmarkService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/bookmarks")
public class BookmarkApi {
    private final BookmarkService bookmarkService;

    public BookmarkApi(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @GetMapping
    public ResponseEntity<?> getAll(@AuthenticationPrincipal UserDetailsImpl auth) {
        return bookmarkService.getAll(auth);
    }

    @PostMapping
    public ResponseEntity<?> add(@AuthenticationPrincipal UserDetailsImpl auth, @RequestBody BookmarkRequest request) {
        return bookmarkService.add(auth, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@AuthenticationPrincipal UserDetailsImpl auth, @PathVariable Long id) {
        return bookmarkService.remove(auth, id);
    }
}
