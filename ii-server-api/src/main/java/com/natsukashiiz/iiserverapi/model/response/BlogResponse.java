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
    private boolean publish;
    private LocalDateTime cdt;
    private Long uid;
    private String uname;
    private boolean bookmark;
}
