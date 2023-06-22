package com.natsukashiiz.iiserverapi.model.request;


import lombok.Data;

/**
 * {
 *     "currentPassword": "xxxx"
 *     "newPassword": "xxxx"
 *     "confirmPassword": "xxxx"
 * }
 */
@Data
public class ChangePasswordRequest {
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}
