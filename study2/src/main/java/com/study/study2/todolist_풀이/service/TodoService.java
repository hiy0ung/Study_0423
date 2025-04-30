package com.study.study2.todolist_풀이.service;

import com.study.study2.todolist_풀이.exception.TodoNotFoundException;
import com.study.study2.todolist_풀이.model.Todo;
import com.study.study2.todolist_풀이.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Todo getTodoById(Long id) {
        Todo todo = todoRepository.findById(id);
        if (todo == null) {
            throw new TodoNotFoundException(id);
        }
        return todo;
    }

    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo updateTodo(Long id, Todo todo) {
        Todo existingTodo = todoRepository.findById(id);
        if (existingTodo == null) {
            throw new TodoNotFoundException(id);
        }

        LocalDateTime oldUpdatedAt = existingTodo.getUpdatedAt();

        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {

        }
        Todo updatedTodo = todoRepository.update(id, todo);

        if (updatedTodo.getUpdatedAt().equals(oldUpdatedAt)) {
            updatedTodo.modifiedUpdatedAt(LocalDateTime.now().plusNanos(1000));
            todoRepository.save(updatedTodo);
        }
        return updatedTodo;
    }

    public boolean deletedTodo(Long id) {
        if (todoRepository.findById(id) == null) {
            throw new TodoNotFoundException(id);
        }
        return todoRepository.delete(id);
    }
}
