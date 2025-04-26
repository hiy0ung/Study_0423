package com.study.study2.todolist.model;

public record TodoListRequestDto(
        String title,
        String description,
        boolean completed
) {
}
