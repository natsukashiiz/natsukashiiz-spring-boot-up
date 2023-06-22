package com.natsukashiiz.iiserverapi.model.request;

import lombok.Data;

/**
 * {
 *     "email": "xxxx"
 * }
 */
@Data
public class UpdateUserRequest {
    private String email;
}
