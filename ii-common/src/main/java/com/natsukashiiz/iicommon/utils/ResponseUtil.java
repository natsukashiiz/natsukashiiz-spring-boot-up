package com.natsukashiiz.iicommon.utils;

import com.natsukashiiz.iicommon.common.ResponseState;
import com.natsukashiiz.iicommon.model.BaseResponse;
import com.natsukashiiz.iicommon.model.PaginationResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

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

    public static <T> ResponseEntity<?> successList(Page<T> result) {
        PaginationResponse pagination = PaginationResponse.builder()
                .limit(result.getPageable().getPageSize())
                .current(result.getPageable().getPageNumber() + 1)
                .records((int) result.getTotalElements())
                .pages(result.getTotalPages())
                .first(result.isFirst())
                .last(result.isLast())
                .build();
        ResponseState code = ResponseState.SUCCESS;
        BaseResponse<?> response = BaseResponse.builder()
                .code(code.getValue())
                .result(result.getContent())
                .text(code)
                .pagination(pagination)
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
