package com.study.study2.todolist.repository;

import com.study.study2.todolist.model.TodoList;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TodoListRepository {
    public Map<Long, TodoList> todoListStore = new ConcurrentHashMap<>();
    public AtomicLong sequence = new AtomicLong(1);
}
