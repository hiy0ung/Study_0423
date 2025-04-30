package com.study.study2.todolist.model;

import java.time.LocalDateTime;

public record TodoListResponseDto(
        Long id,
        String title,
        String description,
        boolean completed,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
