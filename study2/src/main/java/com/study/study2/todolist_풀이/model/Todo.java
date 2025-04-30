package com.study.study2.todolist_풀이.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Builder
@AllArgsConstructor
@Getter
public class Todo {
    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Todo() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // setter 미사용 권장
    public void updateId(Long id) {
        this.id = id;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void updateCompleted(boolean completed) {
        this.completed = completed;
    }

    public void modifiedCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void modifiedUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
