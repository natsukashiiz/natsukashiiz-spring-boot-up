package com.natsukashiiz.iicommon.common;

import java.util.Optional;

public enum CommonState implements BaseState<CommonState> {
    NORMAL(10),
    DISABLED(20),
    DESTROYED(30)
    ;
    private final Integer value;

    CommonState(final Integer code) {
        this.value = code;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public Optional<CommonState> find(Integer code) {
        for (CommonState values : CommonState.values()) {
            if (values.value.equals(code))
                return Optional.of(values);
        }
        return Optional.empty();
    }
}
