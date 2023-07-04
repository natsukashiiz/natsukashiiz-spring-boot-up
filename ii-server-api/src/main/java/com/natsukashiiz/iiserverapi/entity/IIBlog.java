package com.natsukashiiz.iiserverapi.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class IIBlog implements Serializable {
    private Long id;
    private Integer state;
    private LocalDateTime cdt;
    private String title;
    private String content;
    private Long categoryId;
    private boolean publish;
    private Long uid;
}
