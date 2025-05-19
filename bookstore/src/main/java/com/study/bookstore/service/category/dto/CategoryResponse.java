package com.study.bookstore.service.category.dto;

import com.study.bookstore.infrastructure.category.entity.CategoryJpaEntity;

import java.util.Optional;

public record CategoryResponse(
        Long id,
        String name,
        String code
) {

    public static CategoryResponse from(CategoryJpaEntity entity) {
        return new CategoryResponse(
                entity.getId(),
                entity.getName(),
                entity.getCode()
        );
    }
}
