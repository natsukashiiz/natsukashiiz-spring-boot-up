package com.natsukashiiz.iiserverapi.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookmarkResponse {
    private Long blogId;
    private Long blogTitle;
    private Long blog;
}
