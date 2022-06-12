package com.tw.todoapp.service;

import com.tw.todoapp.entity.Todo;
import com.tw.todoapp.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public Todo createTodoTask(Todo todo) {

        return todoRepository.save(todo);
    }


}
