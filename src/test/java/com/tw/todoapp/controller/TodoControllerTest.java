package com.tw.todoapp.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.todoapp.entity.Todo;
import com.tw.todoapp.exception.TodoTaskNotFoundException;
import com.tw.todoapp.service.TodoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)
@ExtendWith(SpringExtension.class)
public class TodoControllerTest {

    @MockBean
    private TodoService todoService;

    @Autowired
    private MockMvc mockMvc;

    @Captor
    private ArgumentCaptor<Todo> todoArgumentCaptor;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldAbleCreateNewTodoTask() throws Exception {
        Todo todo = new Todo(1L, "Playing", false);
        when(todoService.createTodoTask(todoArgumentCaptor.capture())).thenReturn(todo);

        this.mockMvc
                .perform(post("/api/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "http://localhost/api/todo/1"));

        assertThat(todoArgumentCaptor.getValue().getDescription(), is("Playing"));
        assertThat(todoArgumentCaptor.getValue().isCompleted(), is(false));
    }


    @Test
    void allTodoTaskEndpointShouldReturnTwoTodoTask() throws Exception {
        Todo todo = new Todo(1L, "playing", false);
        Todo todo1 = new Todo(2L, "Reading", false);

        when(todoService.getAllTodoTask()).thenReturn(List.of(todo, todo1));

        this.mockMvc
                .perform(get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].description", is("playing")))
                .andExpect(jsonPath("$[0].completed", is(false)));
    }

    @Test
    void shouldReturnTodoTaskWhenTheirIdIsPassed() throws Exception {
        Todo todo = new Todo(1L, "playing", false);

        when(todoService.getTodoTaskById(1L)).thenReturn(todo);

        this.mockMvc
                .perform(get("/api/todo/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.description", is("playing")));
    }

    @Test
    void shouldReturn404WhenIdIsNotFound() throws Exception {
        Todo todo = new Todo(1L, "playing", false);

        when(todoService.getTodoTaskById(1L)).thenThrow(new TodoTaskNotFoundException("Todo task with id  '1' not found"));

        this.mockMvc
                .perform(get("/api/todo/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateTheTodoTaskWhenTheirIdIsPassed() throws Exception {
        Todo todo = new Todo(1L, "playing", false);
        Todo newTodo = new Todo(1L, "sleeping", true);
        when(todoService.updateTodoTaskById(eq(1L), todoArgumentCaptor.capture())).thenReturn(newTodo);

        this.mockMvc
                .perform(put("/api/todo/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.description", is("sleeping")))
                .andExpect(jsonPath("$.completed", is(true)));

        assertThat(todoArgumentCaptor.getValue().getDescription(), is(todo.getDescription()));
        assertThat(todoArgumentCaptor.getValue().isCompleted(), is(todo.isCompleted()));

    }

    @Test
    void shouldDeleteATodoTaskWithTheirId() throws Exception {
        Todo todo = new Todo(1L, "sleeping", false);
        when(todoService.deleteTodoTaskById(1L)).thenReturn(todo);

        this.mockMvc
                .perform(delete("/api/todo/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("sleeping")))
                .andExpect(jsonPath("$.completed", is(false)));
    }
}
