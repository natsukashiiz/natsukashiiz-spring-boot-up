package com.natsukashiiz.iiserverapi.service;

import com.natsukashiiz.iiboot.configuration.jwt.UserDetailsImpl;
import com.natsukashiiz.iicommon.common.ResponseState;
import com.natsukashiiz.iicommon.utils.ResponseUtil;
import com.natsukashiiz.iiserverapi.Utils;
import com.natsukashiiz.iiserverapi.entity.Blog;
import com.natsukashiiz.iiserverapi.entity.Bookmark;
import com.natsukashiiz.iiserverapi.entity.User;
import com.natsukashiiz.iiserverapi.model.request.BookmarkRequest;
import com.natsukashiiz.iiserverapi.model.response.BlogResponse;
import com.natsukashiiz.iiserverapi.repository.BookmarkRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;

    public BookmarkService(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    public ResponseEntity<?> getAll(UserDetailsImpl auth) {
        List<Bookmark> list = bookmarkRepository.findAllByUser(Utils.getUserFromAuth(auth));
        List<BlogResponse> response = list.stream().map(e -> BlogService.buildResponse(e.getBlog())).collect(Collectors.toList());
        return ResponseUtil.success(response);
    }

    public ResponseEntity<?> add(UserDetailsImpl auth, BookmarkRequest request) {

        if (Objects.isNull(request.getBlogId())) {
            return ResponseUtil.error(ResponseState.INVALID_REQUEST);
        }

        Blog blog = new Blog();
        blog.setId(request.getBlogId());
        User user = Utils.getUserFromAuth(auth);

        long count = bookmarkRepository.countByBlogAndUser(blog, user);
        if (count == 1) {
            return ResponseUtil.error(ResponseState.INVALID_REQUEST);
        }

        Bookmark bookmark = new Bookmark();
        bookmark.setBlog(blog);
        bookmark.setUser(user);

        // save
        bookmarkRepository.save(bookmark);
        return ResponseUtil.success();
    }

    public ResponseEntity<?> remove(UserDetailsImpl auth, Long id) {
        bookmarkRepository.deleteByIdAndUser(id, Utils.getUserFromAuth(auth));
        return ResponseUtil.success();
    }
}
