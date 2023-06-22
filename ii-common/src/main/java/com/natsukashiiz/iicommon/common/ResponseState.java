package com.natsukashiiz.iicommon.common;

import java.util.Optional;

public enum ResponseState implements BaseState<ResponseState>{
    SUCCESS(0),
    INVALID_REQUEST(210),
    INVALID_EMAIL(211),
    INVALID_USERNAME(212),
    INVALID_PASSWORD(213),
    INVALID_NEW_PASSWORD(214),
    INVALID_CODE(215),
    INVALID_UID(216),
    INVALID_USERNAME_PASSWORD(217),
    PASSWORD_NOT_MATCH(218),
    INVALID_TITLE(219),
    INVALID_CONTENT(220),
    EXISTED_EMAIL(310),
    EXISTED_USERNAME(311),
    NOT_FOUND(410),
    NO_DATA(411),
    TOKEN_EXPIRE(510),
    REFRESH_TOKEN_EXPIRE(511),
    UNAUTHORIZED(888),
    UNKNOWN(999);

    private final Integer value;

    ResponseState(final Integer code) {
        this.value = code;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public Optional<ResponseState> find(Integer code) {
        for (ResponseState values : ResponseState.values()) {
            if (values.value.equals(code))
                return Optional.of(values);
        }
        return Optional.empty();
    }
}

