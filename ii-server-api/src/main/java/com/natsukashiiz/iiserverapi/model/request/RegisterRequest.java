package com.natsukashiiz.iiserverapi.model.request;

import lombok.Data;

/**
 * {
 *     "email: "xxxx",
 *     "username: "xxxx",
 *     "password: "xxxx"
 * }
 */
@Data
public class RegisterRequest {
    private String email;
    private String username;
    private String password;
}
