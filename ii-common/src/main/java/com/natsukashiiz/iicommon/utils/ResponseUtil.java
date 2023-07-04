package com.natsukashiiz.iicommon.utils;

import com.natsukashiiz.iicommon.common.ResponseState;
import com.natsukashiiz.iicommon.model.BaseResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ResponseUtil {

    public static <E> ResponseEntity<?> success() {
        return success(null);
    }

    public static <E> ResponseEntity<?> success(E result) {
        ResponseState code = ResponseState.SUCCESS;
        BaseResponse<?> response = BaseResponse.builder()
                .code(code.getValue())
                .text(code)
                .result(result)
                .build();
        return ResponseEntity.ok(response);
    }

    public static <E> ResponseEntity<?> successList(List<E> result) {
        ResponseState code = ResponseState.SUCCESS;
        BaseResponse<?> response = BaseResponse.builder()
                .code(code.getValue())
                .text(code)
                .result(result)
                .records((long) result.size())
                .build();
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<?> error(ResponseState code) {
        BaseResponse<?> response = BaseResponse.builder()
                .code(code.getValue())
                .text(code)
                .build();
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<?> unauthorized() {
        ResponseState code = ResponseState.UNAUTHORIZED;
        BaseResponse<?> response = BaseResponse.builder()
                .code(code.getValue())
                .text(code)
                .build();
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<?> unknown() {
        ResponseState code = ResponseState.UNKNOWN;
        BaseResponse<?> response = BaseResponse.builder()
                .code(code.getValue())
                .text(code)
                .build();
        return ResponseEntity.ok(response);
    }
}
