package com.study.study2.todolist_풀이.controller;

import com.study.study2.todolist_풀이.model.Todo;
import com.study.study2.todolist_풀이.model.TodoResponse;
import com.study.study2.todolist_풀이.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @GetMapping
    public ResponseEntity<List<TodoResponse>> getAllTodos() {
        List<Todo> todos = todoService.getAllTodos();
        List<TodoResponse> responses = todos.stream()
                .map(TodoResponse::from)
                .toList();
        return ResponseEntity.ok(responses);
    }
}
