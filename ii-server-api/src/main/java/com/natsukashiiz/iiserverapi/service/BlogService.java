package com.natsukashiiz.iiserverapi.service;

import com.natsukashiiz.iiboot.configuration.jwt.UserDetailsImpl;
import com.natsukashiiz.iicommon.common.ResponseState;
import com.natsukashiiz.iicommon.model.Pagination;
import com.natsukashiiz.iicommon.utils.Comm;
import com.natsukashiiz.iicommon.utils.ResponseUtil;
import com.natsukashiiz.iicommon.utils.ValidateUtil;
import com.natsukashiiz.iiserverapi.entity.IIBlog;
import com.natsukashiiz.iiserverapi.mapper.BlogMapper;
import com.natsukashiiz.iiserverapi.mapper.CategoryMapper;
import com.natsukashiiz.iiserverapi.model.request.BlogRequest;
import com.natsukashiiz.iiserverapi.model.response.BlogResponse;
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

    public ResponseEntity<?> getById(UserDetailsImpl auth, Long id) {
        Optional<IIBlog> opt = blogMapper.findByIdWithBookmark(id, auth.getId());
        if (!opt.isPresent()) {
            return ResponseUtil.error(ResponseState.BLOG_NOT_FOUND);
        }
        return ResponseUtil.success(buildResponse(opt.get()));
    }

    public ResponseEntity<?> getAll(UserDetailsImpl auth, Pagination pagination) {
        List<IIBlog> blogs = blogMapper.findAllWithBookmark(auth.getId(), pagination);
        return ResponseUtil.successList(buildResponseList(blogs));
    }

    public ResponseEntity<?> getByUser(UserDetailsImpl auth, String uname, Pagination pagination) {
        if (ValidateUtil.invalidUsername(uname)) {
            return ResponseUtil.error(ResponseState.INVALID_USERNAME);
        }

        List<IIBlog> blogs = blogMapper.findByUid(auth.getId());
        return ResponseUtil.successList(blogs);
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
        blog.setContent(request.getContent());
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
        blog.setContent(request.getContent());
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


    public static BlogResponse buildResponse(IIBlog blog) {
        return BlogResponse.builder()
                .id(blog.getId())
                .title(blog.getTitle())
                .content(Comm.decodeString(blog.getContent()))
                .category(blog.getCategory())
                .publish(blog.isPublish())
                .uid(blog.getUid())
                .uname(blog.getUname())
                .bookmark(blog.isBookmark())
                .cdt(blog.getCdt())
                .build();
    }

    public static List<BlogResponse> buildResponseList(List<IIBlog> data) {
        return data.stream().map(BlogService::buildResponse).collect(Collectors.toList());
    }
}
