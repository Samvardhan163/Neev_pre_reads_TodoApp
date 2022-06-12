package com.tw.todoapp.service;

import com.tw.todoapp.entity.Todo;
import com.tw.todoapp.exception.TodoTaskNotFoundException;
import com.tw.todoapp.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public Todo createTodoTask(Todo todo) {

        return todoRepository.save(todo);
    }

    public List<Todo> getAllTodoTask() {
        List<Todo> todoList = new ArrayList<>();
        todoRepository.findAll().forEach(todo -> todoList.add(todo));
        return todoList;
    }

    public Todo getTodoTaskById(Long id) {
        Optional<Todo> todo = todoRepository.findById(id);
        if (todo.isEmpty()) {
            throw new TodoTaskNotFoundException(String.format("Todo task with id: '%s' not found", id));
        }
        return todoRepository.findById(id).get();
    }
}
