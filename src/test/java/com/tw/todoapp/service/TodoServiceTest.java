package com.tw.todoapp.service;

import com.tw.todoapp.entity.Todo;
import com.tw.todoapp.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @Test
    void AssertingWhetherNewTodoTaskIsCreatedReturnsThatTodoTask() {
        Todo todo = new Todo(1L, "playing", false);

        when(todoService.createTodoTask(any(Todo.class))).thenReturn(todo);

        Todo savedTodo = todoRepository.save(todo);

        assertThat(savedTodo.getDescription()).isNotNull();
    }

    @Test
    void shouldReturnTwoTodoTask() {
        Todo todo = new Todo(1L, "playing", false);
        Todo todo1=new Todo(2L,"sleeping",false);
        List<Todo>todoList=new ArrayList<>();
        todoList.add(todo);
        todoList.add(todo1);
        when(todoService.getAllTodoTask()).thenReturn(todoList);

       List<Todo> fetchTodoList=todoService.getAllTodoTask();

       assertThat(fetchTodoList.size()).isGreaterThan(0);
    }

}
