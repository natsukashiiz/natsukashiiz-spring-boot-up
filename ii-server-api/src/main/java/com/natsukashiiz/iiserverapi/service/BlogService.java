package com.natsukashiiz.iiserverapi.service;

import com.natsukashiiz.iiboot.configuration.jwt.UserDetailsImpl;
import com.natsukashiiz.iicommon.common.ResponseState;
import com.natsukashiiz.iicommon.model.Pagination;
import com.natsukashiiz.iicommon.utils.Comm;
import com.natsukashiiz.iicommon.utils.ResponseUtil;
import com.natsukashiiz.iicommon.utils.ValidateUtil;
import com.natsukashiiz.iiserverapi.entity.Blog;
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

import java.util.Base64;
import java.util.Objects;
import java.util.Optional;
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

    public ResponseEntity<?> getById(Long id) {
        Optional<Blog> opt = blogRepository.findById(id);
        if (!opt.isPresent()) {
            return ResponseUtil.error(ResponseState.NOT_FOUND);
        }
        return ResponseUtil.success(buildResponse(opt.get()));
    }

    public ResponseEntity<?> getAll(Pagination pagination) {
        long count = blogRepository.count();
        if (count == 0) {
            return ResponseUtil.error(ResponseState.NO_DATA);
        }
        Pageable paginate = Comm.getPaginate(pagination);
        Page<Blog> page = blogRepository.findByPublish(true, paginate);
        return ResponseUtil.successList(buildResponseList(page, paginate));
    }

    public ResponseEntity<?> getByUser(String uname, Pagination pagination) {
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
        return ResponseUtil.successList(buildResponseList(page, paginate));
    }

    public ResponseEntity<?> create(UserDetailsImpl auth, BlogRequest request) {
        if (Objects.isNull(request.getTitle())) {
            return ResponseUtil.error(ResponseState.INVALID_REQUEST);
        }

        if (Objects.isNull(request.getContent())) {
            return ResponseUtil.error(ResponseState.INVALID_REQUEST);
        }

        Blog blog = new Blog();
        blog.setTitle(request.getTitle());
        blog.setContent(request.getContent());
        blog.setUser(getUserFromAuth(auth));

        Blog save = blogRepository.save(blog);
        return ResponseUtil.success(buildResponse(save));
    }

    public ResponseEntity<?> update(UserDetailsImpl auth, Long id, BlogRequest request) {
        Optional<Blog> opt = blogRepository.findByIdAndUser(id, getUserFromAuth(auth));
        if (!opt.isPresent()) {
            return ResponseUtil.error(ResponseState.NOT_FOUND);
        }

        Blog blog = opt.get();
        if (Objects.nonNull(request.getTitle())) {
            blog.setTitle(request.getTitle());
        }

        if (Objects.nonNull(request.getContent())) {
            blog.setContent(encodeString(request.getContent()));
        }

        Blog save = blogRepository.save(blog);
        return ResponseUtil.success(buildResponse(save));
    }

    public ResponseEntity<?> publish(UserDetailsImpl auth, Long id) {
        Optional<Blog> opt = blogRepository.findByIdAndUser(id, getUserFromAuth(auth));
        if (!opt.isPresent()) {
            return ResponseUtil.error(ResponseState.NOT_FOUND);
        }

        Blog blog = opt.get();
        blog.setPublish(!blog.isPublish());

        Blog save = blogRepository.save(blog);
        return ResponseUtil.success(buildResponse(save));
    }

    public User getUserFromAuth(UserDetailsImpl auth) {
        User user = new User();
        user.setId(auth.getId());
        return user;
    }

    public String encodeString(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    public String decodeString(String str) {
        byte[] decodedBytes = Base64.getDecoder().decode(str);
        return new String(decodedBytes);
    }

    public BlogResponse buildResponse(Blog blog) {
        return BlogResponse.builder()
                .id(blog.getId())
                .title(blog.getTitle())
                .content(Objects.nonNull(blog.getContent()) ? decodeString(blog.getContent()) : null)
                .publish(blog.isPublish())
                .cdt(blog.getCdt())
                .uid(blog.getUser().getId())
                .uname(blog.getUser().getUsername())
                .build();
    }

    public Page<BlogResponse> buildResponseList(Page<Blog> data, Pageable pageable) {
        return data.stream().map(this::buildResponse).collect(Collectors.collectingAndThen(Collectors.toList(), list -> new PageImpl<>(list, pageable, list.size())));
    }
}
