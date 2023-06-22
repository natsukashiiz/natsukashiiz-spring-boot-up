package com.natsukashiiz.iicommon.common;

import java.util.Optional;

public interface BaseState<E> {
    Integer getValue();
    Optional<E> find(Integer code);
}
