package com.natsukashiiz.iiserverapi.service;

import com.natsukashiiz.iiboot.configuration.jwt.UserDetailsImpl;
import com.natsukashiiz.iicommon.common.ResponseState;
import com.natsukashiiz.iicommon.model.Pagination;
import com.natsukashiiz.iicommon.utils.Comm;
import com.natsukashiiz.iicommon.utils.ResponseUtil;
import com.natsukashiiz.iicommon.utils.ValidateUtil;
import com.natsukashiiz.iiserverapi.entity.IIBlog;
import com.natsukashiiz.iiserverapi.entity.IIUser;
import com.natsukashiiz.iiserverapi.mapper.BlogMapper;
import com.natsukashiiz.iiserverapi.mapper.CategoryMapper;
import com.natsukashiiz.iiserverapi.mapper.UserMapper;
import com.natsukashiiz.iiserverapi.model.request.BlogRequest;
import com.natsukashiiz.iiserverapi.model.response.BlogResponse;
import com.natsukashiiz.iiserverapi.model.response.UserBlogResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BlogService {
    @Resource
    private BlogMapper blogMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserService userService;

    public ResponseEntity<?> getById(UserDetailsImpl auth, Long id) {
        Optional<IIBlog> opt;
        if (Objects.isNull(auth)) {
            opt = blogMapper.findById(id);
        } else {
            opt = blogMapper.findByIdWithBookmark(id, auth.getId());
        }

        if (!opt.isPresent()) {
            return ResponseUtil.error(ResponseState.BLOG_NOT_FOUND);
        }

        return ResponseUtil.success(buildResponse(opt.get()));
    }

    public ResponseEntity<?> getAll(UserDetailsImpl auth, Pagination pagination) {
        Long count = blogMapper.count();
        List<IIBlog> blogs;
        if (Objects.isNull(auth)) {
            blogs = blogMapper.findAll(pagination);
        } else {
            blogs = blogMapper.findAllWithBookmark(auth.getId(), pagination);
        }
        return ResponseUtil.successList(buildResponseList(blogs), count);
    }

    public ResponseEntity<?> getByUser(UserDetailsImpl auth, String uname, Pagination pagination) {
        if (ValidateUtil.invalidUsername(uname)) {
            return ResponseUtil.error(ResponseState.INVALID_USERNAME);
        }

        if (!userMapper.hasUsername(uname)) {
            return ResponseUtil.error(ResponseState.NOT_FOUND);
        }

        IIUser user = userMapper.findByUsername(uname).get();
        Long count = blogMapper.countByUname(uname);

        List<IIBlog> blogs;
        if (Objects.nonNull(auth)) {
            if (auth.getUsername().equals(uname)) {
                blogs = blogMapper.findBySelfWithBookmark(uname, auth.getId(), pagination);
            } else {
                blogs = blogMapper.findByUnameWithBookmark(uname, auth.getId(), pagination);
            }
        } else {
            blogs = blogMapper.findByUname(uname, pagination);
        }

        return ResponseUtil.success(UserBlogResponse.builder()
                .user(userService.buildResponse(user))
                .blog(buildResponseList(blogs))
                .build(),
                count
                );
    }

    public ResponseEntity<?> create(UserDetailsImpl auth, BlogRequest request) {
        if (Objects.isNull(request.getTitle())) {
            return ResponseUtil.error(ResponseState.INVALID_TITLE);
        }

        if (Objects.isNull(request.getContent())) {
            return ResponseUtil.error(ResponseState.INVALID_CONTENT);
        }

        if (Objects.isNull(request.getCategoryId())) {
            return ResponseUtil.error(ResponseState.INVALID_CATEGORY_ID);
        }

        if (!categoryMapper.hasId(request.getCategoryId())) {
            return ResponseUtil.error(ResponseState.CATEGORY_NOT_FOUND);
        }

        IIBlog blog = new IIBlog();
        blog.setUid(auth.getId());
        blog.setTitle(request.getTitle());
        blog.setContent(Comm.encodeString(request.getContent()));
        blog.setCategoryId(request.getCategoryId());

        blogMapper.save(blog);
        return ResponseUtil.success(buildResponse(blog));
    }

    public ResponseEntity<?> update(UserDetailsImpl auth, Long id, BlogRequest request) {
        IIBlog find = new IIBlog();
        find.setId(id);
        find.setUid(auth.getId());

        Optional<IIBlog> opt = blogMapper.findOne(find);
        if (!opt.isPresent()) {
            return ResponseUtil.error(ResponseState.BLOG_NOT_FOUND);
        }

        IIBlog blog = opt.get();
        blog.setTitle(request.getTitle());
        blog.setContent(Comm.encodeString(request.getContent()));
        blog.setCategoryId(request.getCategoryId());
        blogMapper.update(blog);
        return ResponseUtil.success(buildResponse(blog));
    }

    public ResponseEntity<?> publish(UserDetailsImpl auth, Long id) {
        Integer publish = blogMapper.publish(id, auth.getId());
        if (publish == 0) {
            return ResponseUtil.unknown();
        }
        return ResponseUtil.success();
    }

    public ResponseEntity<?> delete(UserDetailsImpl auth, Long id) {
        IIBlog find = new IIBlog();
        find.setId(id);
        find.setUid(auth.getId());

        Optional<IIBlog> opt = blogMapper.findOne(find);
        if (!opt.isPresent()) {
            return ResponseUtil.error(ResponseState.BLOG_NOT_FOUND);
        }

        blogMapper.delete(id, auth.getId());
        return ResponseUtil.success();
    }

    public static BlogResponse buildResponse(IIBlog blog) {
        return BlogResponse.builder()
                .id(blog.getId())
                .title(blog.getTitle())
                .content(Comm.decodeString(blog.getContent()))
                .category(blog.getCategory())
                .publish(blog.isPublish())
                .uid(blog.getUid())
                .uname(blog.getUname())
                .uname(blog.getUname())
                .avatar(blog.getAvatar())
                .bookmark(blog.isBookmark())
                .cdt(blog.getCdt())
                .build();
    }

    public static List<BlogResponse> buildResponseList(List<IIBlog> data) {
        return data.stream().map(BlogService::buildResponse).collect(Collectors.toList());
    }
}
