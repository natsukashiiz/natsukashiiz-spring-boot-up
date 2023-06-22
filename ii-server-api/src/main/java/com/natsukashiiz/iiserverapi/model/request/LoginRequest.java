package com.natsukashiiz.iiserverapi.model.request;

import lombok.Data;

/**
 * {
 *     "username": "xxxx",
 *     "password": "xxxx"
 * }
 */
@Data
public class LoginRequest {
    private String username;
    private String password;
}
