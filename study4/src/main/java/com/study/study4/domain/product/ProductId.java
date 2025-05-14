package com.study.study4.domain.product;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ProductId {
    private final Long value;

    public ProductId(Long value) {
        this.value = value;
    }

    public static ProductId of(Long value) {
        if (value == null) {
            throw new IllegalArgumentException("ID는 null 이 될 수 없습니다.");
        }
        return new ProductId(value);
    }
}
