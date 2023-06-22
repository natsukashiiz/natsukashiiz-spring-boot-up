package com.natsukashiiz.iicommon.common;

import java.util.Optional;

public enum DefaultState implements BaseState<DefaultState> {
    ENABLED(10),
    DISABLED(20),
    DESTROYED(30)
    ;
    private final Integer value;

    DefaultState(final Integer code) {
        this.value = code;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public Optional<DefaultState> find(Integer code) {
        for (DefaultState values : DefaultState.values()) {
            if (values.value.equals(code))
                return Optional.of(values);
        }
        return Optional.empty();
    }
}
