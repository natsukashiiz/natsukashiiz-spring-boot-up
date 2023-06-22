package com.natsukashiiz.iiserverapi.model.request;

import lombok.Data;

/**
 * {
 *     "refreshToken": "xxxx"
 * }
 */
@Data
public class TokenRefreshRequest {
    private String refreshToken;
}
