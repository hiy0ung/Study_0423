package com.study.study2.todolist.service;

import com.study.study2.todolist.model.ResponseDto;
import com.study.study2.todolist.model.TodoList;
import com.study.study2.todolist.model.TodoListRequestDto;
import com.study.study2.todolist.model.TodoListResponseDto;
import com.study.study2.todolist.repository.TodoListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@RequiredArgsConstructor
public class TodoListService {
    private final TodoListRepository todoListRepository;

    public TodoListService(TodoListRepository todoListRepository) {
        this.todoListRepository = todoListRepository;
    }

    // 모든 할 일 목록 조회
    public ResponseDto<List<TodoListResponseDto>> getAllTodos() {
        List<TodoListResponseDto> data;
        try {
            data = todoListRepository.todoListStore.values().stream()
                    .map(todoList -> new TodoListResponseDto(
                            todoList.getId(),
                            todoList.getTitle(),
                            todoList.getDescription(),
                            todoList.isCompleted(),
                            todoList.getCreatedAt(),
                            todoList.getUpdatedAt()
                    )).toList();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("Database Error");
        }
        return ResponseDto.setSuccess("Success", data);
    }

    // 특정 할 일 조회
    public ResponseDto<TodoListResponseDto> getTodoById(Long id) {
        TodoListResponseDto data;
        try {
            TodoList todoList = todoListRepository.todoListStore.get(id);
//                    .orElseThrow(() -> new Error("TodoList not Found with id" + id));
            if (todoList == null) {
                return ResponseDto.setFailed("Not Exist data");
            }

            data = new TodoListResponseDto(
                    todoList.getId(),
                    todoList.getTitle(),
                    todoList.getDescription(),
                    todoList.isCompleted(),
                    todoList.getCreatedAt(),
                    todoList.getUpdatedAt()
            );

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("Database Error");
        }
        return ResponseDto.setSuccess("Success", data);
    }

    // 새로운 할 일 생성
    public ResponseDto<TodoListResponseDto> createTodo(TodoListRequestDto dto) {
        TodoListResponseDto data;
        try {
            Long id = todoListRepository.sequence.getAndIncrement();
            TodoList todoList = new TodoList(
                    null, dto.title(), dto.description()
            );
            todoListRepository.todoListStore.put(id, todoList);

            data = new TodoListResponseDto(
                    todoList.getId(), todoList.getTitle(), todoList.getDescription(), todoList.isCompleted(),
                    todoList.getCreatedAt(), todoList.getUpdatedAt()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("Database Error");
        }
        return ResponseDto.setSuccess("Success", data);
    }

    // 기존 할 일 수정
    public ResponseDto<TodoListResponseDto> updateTodo(Long id, TodoListRequestDto dto) {
        TodoListResponseDto data;
        try {
            TodoList todoList = todoListRepository.todoListStore.get(id);
            if (todoList == null) {
                return ResponseDto.setFailed("Not Exist data");
            }
            todoList.setTitle(dto.title());
            todoList.setDescription(dto.description());

            todoListRepository.todoListStore.put(id, todoList);

            data = new TodoListResponseDto(
                    todoList.getId(), todoList.getTitle(), todoList.getDescription(), todoList.isCompleted(),
                    todoList.getCreatedAt(), todoList.getUpdatedAt()
            );

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("Database Error");
        }
        return ResponseDto.setSuccess("Success", data);
    }

    // 할 일 삭제
    public ResponseDto<Void> deleteTodo(Long id) {
        try {
            TodoList todoList = todoListRepository.todoListStore.get(id);
            if (todoList == null) {
                return ResponseDto.setFailed("Not Exist data");
            }
            todoListRepository.todoListStore.remove(id);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("Database Error");
        }
        return ResponseDto.setSuccess("Success", null);
    }
}
