package com.tw.todoapp.service;

import com.tw.todoapp.entity.Todo;
import com.tw.todoapp.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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
        Todo todo1 = new Todo(2L, "sleeping", false);
        List<Todo> todoList = new ArrayList<>();
        todoList.add(todo);
        todoList.add(todo1);
        when(todoService.getAllTodoTask()).thenReturn(todoList);

        List<Todo> fetchTodoList = todoService.getAllTodoTask();

        assertThat(fetchTodoList.size()).isGreaterThan(0);
    }

    @Test
    void shouldReturnTodoTaskById() {
        Todo todo = new Todo(1L, "playing", false);
        Mockito.when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));

        Todo fetchTodo = todoService.getTodoTaskById(1L);

        assertEquals(fetchTodo.getId(),todo.getId());
        assertEquals(fetchTodo.isCompleted(),todo.isCompleted());
        assertEquals(fetchTodo.getDescription(),todo.getDescription());
    }

    @Test
    void shouldReturnUpdatedTodoTaskByGettingItsId() {
        Todo todo = new Todo(1L, "playing", false);
        Todo newTodo=new Todo(1L,"sleeping",false);
        Mockito.when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));

        Todo updatedTodo=todoService.updateTodoTaskById(newTodo.getId(),newTodo);

        assertEquals(updatedTodo.getId(),newTodo.getId());
        assertEquals(updatedTodo.isCompleted(),newTodo.isCompleted());
        assertEquals(updatedTodo.getDescription(),newTodo.getDescription());
    }

    @Test
    void shouldReturnTodoTaskByDeletingTodoTaskByItsId()
    {
        Todo todo = new Todo(1L, "playing", false);
        Mockito.when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));

        Todo deletedTodo=todoService.deleteTodoTaskById(todo.getId());

        verify(todoRepository).deleteById(todo.getId());
        assertEquals(deletedTodo.getDescription(),todo.getDescription());
        assertEquals(deletedTodo.isCompleted(),todo.isCompleted());
        assertEquals(deletedTodo.getId(),todo.getId());
    }
}
