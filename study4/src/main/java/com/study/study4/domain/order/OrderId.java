package com.study.study4.domain.order;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class OrderId {
    private final Long value;

    public OrderId(Long value) {
        this.value = value;
    }

    public static OrderId of(Long value) {
        if (value == null) {
            throw new IllegalArgumentException("주문 ID는 null 이 될 수 없습니다.");
        }
        return new OrderId(value);
    }
}
