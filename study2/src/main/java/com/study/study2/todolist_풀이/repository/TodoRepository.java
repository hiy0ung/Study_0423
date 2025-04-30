package com.study.study2.todolist_풀이.repository;

import com.study.study2.todolist_풀이.model.Todo;

import java.util.List;

// 인터페이스는 빈 등록을 하는 게 아님 > 구현체에 @Repository 달기!
public interface TodoRepository {
    List<Todo> findAll();

    Todo findById(Long id);

    Todo save(Todo todo);

    Todo update(Long id, Todo todo);

    boolean delete(Long id);

}
