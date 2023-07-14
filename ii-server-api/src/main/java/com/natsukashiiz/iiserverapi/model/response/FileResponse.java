package com.natsukashiiz.iiserverapi.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileResponse {
    private String file;
}
