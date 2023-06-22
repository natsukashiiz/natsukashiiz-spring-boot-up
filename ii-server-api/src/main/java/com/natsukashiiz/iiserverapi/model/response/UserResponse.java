package com.natsukashiiz.iiserverapi.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * {
 *     "id": xxxx,
 *     "email": "xxxx",
 *     "username": "xxxx"
 * }
 */
@Data
@Builder
public class UserResponse {
    private Long id;
    private String email;
    private String username;
    private LocalDateTime cdt;
}
