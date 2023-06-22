package com.natsukashiiz.iicommon.common;

import java.util.Optional;

public enum DeviceState implements BaseState<DeviceState> {
    iPhone(10),
    Android(20),
    Windows(30),
    Unknown(100);

    private final Integer value;

    DeviceState(final Integer code) {
        this.value = code;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public Optional<DeviceState> find(Integer code) {
        for (DeviceState values : DeviceState.values()) {
            if (values.value.equals(code))
                return Optional.of(values);
        }
        return Optional.empty();
    }
}
