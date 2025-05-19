package com.study.bookstore.service.category.dto;

import com.study.bookstore.domain.book.Category;
import com.study.bookstore.infrastructure.category.entity.CategoryJpaEntity;
import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
        @NotBlank(message = "Category name is required")
        String name,
        @NotBlank(message = "Category code is required")
        String code
) {
    // 도메인 category
    public static Category toDomain() {
        return Category.create(toDomain().getName(), toDomain().getCode());
    }
}
