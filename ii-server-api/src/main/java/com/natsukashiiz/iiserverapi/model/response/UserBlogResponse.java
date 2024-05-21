package com.natsukashiiz.iiserverapi.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserBlogResponse {
    private UserResponse user;
    private List<BlogResponse> blog;
}
