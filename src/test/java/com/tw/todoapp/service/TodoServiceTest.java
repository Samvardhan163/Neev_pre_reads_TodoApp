package com.tw.todoapp.service;

import com.tw.todoapp.entity.Todo;
import com.tw.todoapp.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @Test
    void AssertingWhetherNewTodoTaskIsCreatedReturnTodoTask() {
        Todo todo = new Todo(1L, "playing", false);

        when(todoService.createTodoTask(any(Todo.class))).thenReturn(todo);

       Todo savedTodo=todoRepository.save(todo);

       assertThat(savedTodo.getDescription()).isNotNull();
    }
}
