package com.natsukashiiz.iiserverapi.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SignHistoryResponse {
    private Long id;
    private String ipv4;
    private String device;
    private LocalDateTime cdt;
}
