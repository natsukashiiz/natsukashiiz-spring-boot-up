package com.natsukashiiz.iiserverapi.service;

import com.natsukashiiz.iiboot.configuration.jwt.UserDetailsImpl;
import com.natsukashiiz.iicommon.common.ResponseState;
import com.natsukashiiz.iicommon.model.Pagination;
import com.natsukashiiz.iicommon.utils.Comm;
import com.natsukashiiz.iicommon.utils.ResponseUtil;
import com.natsukashiiz.iicommon.utils.ValidateUtil;
import com.natsukashiiz.iiserverapi.Utils;
import com.natsukashiiz.iiserverapi.entity.Blog;
import com.natsukashiiz.iiserverapi.entity.Bookmark;
import com.natsukashiiz.iiserverapi.entity.Category;
import com.natsukashiiz.iiserverapi.entity.User;
import com.natsukashiiz.iiserverapi.model.request.BlogRequest;
import com.natsukashiiz.iiserverapi.model.response.BlogResponse;
import com.natsukashiiz.iiserverapi.repository.BlogRepository;
import com.natsukashiiz.iiserverapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BlogService {
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    public BlogService(BlogRepository blogRepository, UserRepository userRepository) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> getById(UserDetailsImpl auth, Long id) {
        Optional<Blog> opt = blogRepository.findById(id);
        if (!opt.isPresent()) {
            return ResponseUtil.error(ResponseState.NOT_FOUND);
        }
        return ResponseUtil.success(buildResponse(opt.get()));
    }

    public ResponseEntity<?> getAll(UserDetailsImpl auth, Pagination pagination) {
        long count = blogRepository.count();
        if (count == 0) {
            return ResponseUtil.error(ResponseState.NO_DATA);
        }
        Pageable paginate = Comm.getPaginate(pagination);
        Page<Blog> page = blogRepository.findByPublish(true, paginate);
        return ResponseUtil.successList(buildResponseList(page, paginate, auth.getId()));
    }

    public ResponseEntity<?> getByUser(UserDetailsImpl auth, String uname, Pagination pagination) {
        if (ValidateUtil.invalidUsername(uname)) {
            return ResponseUtil.error(ResponseState.INVALID_USERNAME);
        }

        Optional<User> opt = userRepository.findByUsername(uname);
        if (!opt.isPresent()) {
            return ResponseUtil.error(ResponseState.NOT_FOUND);
        }

        Pageable paginate = Comm.getPaginate(pagination);
        User user = opt.get();
        Page<Blog> page = blogRepository.findByUser(user, paginate);
        return ResponseUtil.successList(buildResponseList(page, paginate, auth.getId()));
    }

    public ResponseEntity<?> create(UserDetailsImpl auth, BlogRequest request) {
        if (Objects.isNull(request.getTitle())) {
            return ResponseUtil.error(ResponseState.INVALID_REQUEST);
        }

        if (Objects.isNull(request.getContent())) {
            return ResponseUtil.error(ResponseState.INVALID_REQUEST);
        }

        if (Objects.isNull(request.getCategoryId())) {
            return ResponseUtil.error(ResponseState.INVALID_REQUEST);
        }

        Category category = new Category();
        category.setId(request.getCategoryId());

        Blog blog = new Blog();
        blog.setTitle(request.getTitle());
        blog.setContent(Comm.encodeString(request.getContent()));
        blog.setCategory(category);
        blog.setUser(Utils.getUserFromAuth(auth));

        Blog save = blogRepository.save(blog);
        return ResponseUtil.success(buildResponse(save));
    }

    public ResponseEntity<?> update(UserDetailsImpl auth, Long id, BlogRequest request) {
        Optional<Blog> opt = blogRepository.findByIdAndUser(id, Utils.getUserFromAuth(auth));
        if (!opt.isPresent()) {
            return ResponseUtil.error(ResponseState.NOT_FOUND);
        }

        Blog blog = opt.get();
        if (Objects.nonNull(request.getTitle())) {
            blog.setTitle(request.getTitle());
        }

        if (Objects.nonNull(request.getContent())) {
            blog.setContent(Comm.encodeString(request.getContent()));
        }

        if (Objects.nonNull(request.getCategoryId())) {
            Category category = new Category();
            category.setId(request.getCategoryId());
            blog.setCategory(category);
        }

        Blog save = blogRepository.save(blog);
        return ResponseUtil.success(buildResponse(save));
    }

    public ResponseEntity<?> publish(UserDetailsImpl auth, Long id) {
        Optional<Blog> opt = blogRepository.findByIdAndUser(id, Utils.getUserFromAuth(auth));
        if (!opt.isPresent()) {
            return ResponseUtil.error(ResponseState.NOT_FOUND);
        }

        Blog blog = opt.get();
        blog.setPublish(!blog.isPublish());

        Blog save = blogRepository.save(blog);
        return ResponseUtil.success(buildResponse(save));
    }


    public static BlogResponse buildResponse(Blog blog) {
        return buildResponse(blog, null);
    }

    public static BlogResponse buildResponse(Blog blog, Long uid) {
        return BlogResponse.builder()
                .id(blog.getId())
                .title(blog.getTitle())
                .content(Comm.decodeString(blog.getContent()))
                .category(blog.getCategory().getName())
                .publish(blog.isPublish())
                .cdt(blog.getCdt())
                .uid(blog.getUser().getId())
                .uname(blog.getUser().getUsername())
                .bookmark(uid != null && bookmarked(blog.getBookmarks(), uid))
                .build();
    }

    public static boolean bookmarked(Set<Bookmark> bookmarks, Long uid) {
        Bookmark bookmark = bookmarks.stream().filter(e -> Objects.equals(e.getUser().getId(), uid)).findAny().orElse(null);
        return !Objects.isNull(bookmark);
    }

    public static Page<BlogResponse> buildResponseList(Page<Blog> data, Pageable pageable) {
        return buildResponseList(data, pageable, null);
    }

    public static Page<BlogResponse> buildResponseList(Page<Blog> data, Pageable pageable, Long uid) {
        return data.stream().map(e -> buildResponse(e, uid)).collect(Collectors.collectingAndThen(Collectors.toList(), list -> new PageImpl<>(list, pageable, list.size())));
    }
}
