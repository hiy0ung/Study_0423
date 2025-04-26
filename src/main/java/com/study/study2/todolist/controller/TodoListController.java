package com.study.study2.todolist.controller;

import com.study.study2.todolist.model.ResponseDto;
import com.study.study2.todolist.model.TodoListRequestDto;
import com.study.study2.todolist.model.TodoListResponseDto;
import com.study.study2.todolist.service.TodoListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
//@RequiredArgsConstructor
public class TodoListController {

    private final TodoListService todoListService;

    public TodoListController(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<TodoListResponseDto>>> getAllTodos() {
        ResponseDto<List<TodoListResponseDto>> response = todoListService.getAllTodos();
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<TodoListResponseDto>> getTodoById(@PathVariable Long id) {
        ResponseDto<TodoListResponseDto> response = todoListService.getTodoById(id);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    @PostMapping
    public ResponseEntity<ResponseDto<TodoListResponseDto>> createTodoList(@RequestBody TodoListRequestDto dto) {
        ResponseDto<TodoListResponseDto> response = todoListService.createTodo(dto);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<TodoListResponseDto>> updateTodo(
            @PathVariable Long id,
            @RequestBody TodoListRequestDto dto
    ) {
        ResponseDto<TodoListResponseDto> response = todoListService.updateTodo(id, dto);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteTodo(@PathVariable Long id) {
        ResponseDto<Void> response = todoListService.deleteTodo(id);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }
}
