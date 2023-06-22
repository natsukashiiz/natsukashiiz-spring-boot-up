package com.natsukashiiz.iiserverapi.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignHistoryResponse {
    private Long id;
    private String ipv4;
    private String device;
}
