package com.natsukashiiz.iiserverapi.service;

import com.natsukashiiz.iiboot.configuration.jwt.UserDetailsImpl;
import com.natsukashiiz.iicommon.common.ResponseState;
import com.natsukashiiz.iicommon.utils.ResponseUtil;
import com.natsukashiiz.iiserverapi.entity.IIBookmark;
import com.natsukashiiz.iiserverapi.mapper.BlogMapper;
import com.natsukashiiz.iiserverapi.mapper.BookmarkMapper;
import com.natsukashiiz.iiserverapi.model.request.BookmarkRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class BookmarkService {
    @Resource
    private BookmarkMapper bookmarkMapper;

    @Resource
    private BlogMapper blogMapper;

    public ResponseEntity<?> getSelf(UserDetailsImpl auth) {
        List<IIBookmark> bookmarks = bookmarkMapper.findByUid(auth.getId());
        return ResponseUtil.successList(bookmarks);
    }

    public ResponseEntity<?> save(UserDetailsImpl auth, BookmarkRequest request) {
        if (Objects.isNull(request.getBlogId())) {
            return ResponseUtil.error(ResponseState.INVALID_BLOG_ID);
        }

        if (!blogMapper.hasId(request.getBlogId())) {
            return ResponseUtil.error(ResponseState.BLOG_NOT_FOUND);
        }

        if (bookmarkMapper.hasBlogIdAndUid(request.getBlogId(), auth.getId())) {
            return ResponseUtil.error(ResponseState.EXISTED_BOOKMARK);
        }

        IIBookmark bookmark = new IIBookmark();
        bookmark.setBlogId(request.getBlogId());
        bookmark.setUid(auth.getId());
        bookmarkMapper.save(bookmark);

        return ResponseUtil.success();
    }

    public ResponseEntity<?> remove(UserDetailsImpl auth, Long id) {
        if (!bookmarkMapper.hasBlogIdAndUid(id, auth.getId())) {
            return ResponseUtil.error(ResponseState.BOOKMARK_NOT_FOUND);
        }

        bookmarkMapper.remove(id, auth.getId());
        return ResponseUtil.success();
    }
}
