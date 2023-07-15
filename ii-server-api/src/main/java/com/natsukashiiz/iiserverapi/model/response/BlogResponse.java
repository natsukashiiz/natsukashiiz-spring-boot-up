package com.natsukashiiz.iiserverapi.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BlogResponse {
    private Long id;
    private String title;
    private String content;
    private String category;
    private Boolean publish;
    private LocalDateTime cdt;
    private Long uid;
    private String uname;
    private String avatar;
    private Boolean bookmark;
}
