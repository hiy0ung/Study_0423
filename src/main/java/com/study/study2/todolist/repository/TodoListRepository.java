package com.study.study2.todolist.repository;

import com.study.study2.todolist.model.TodoList;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class TodoListRepository {
    public Map<Long, TodoList> todoListStore = new ConcurrentHashMap<>();
    public AtomicLong sequence = new AtomicLong(1);
}
