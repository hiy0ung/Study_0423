package com.study.study2.todolist_풀이.model;

public record TodoRequest(
        String title,
        String description,
        boolean completed
) {
    public Todo toEntity() {
        return Todo.builder()
                .title(title)
                .description(description)
                .completed(completed)
                .build();
    }
}
