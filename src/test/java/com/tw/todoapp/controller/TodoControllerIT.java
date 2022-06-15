package com.tw.todoapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.todoapp.entity.Todo;
import com.tw.todoapp.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TodoControllerIT {

    private Todo todo;

    @BeforeEach
    public void setUp() {
        todoRepository.deleteAll();
    }

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldAbleCreateNewTodoTask() throws Exception {
        todo = new Todo("sleeping", false);
        todoRepository.save(todo);

        this.mockMvc
                .perform(post("/api/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "http://localhost/api/todo/"+todo.getId()));

    }


    @Test
    void allTodoTaskEndpointShouldReturnTwoTodoTask() throws Exception {
        todo = new Todo("sleeping", false);
        Todo todo1 = new Todo( "Reading", false);

        List<Todo> todoList = new ArrayList<>();
        todoList.add(todo);
        todoList.add(todo1);

        todoRepository.saveAll(todoList);

        this.mockMvc
                .perform(get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(todo.getId().intValue())))
                .andExpect(jsonPath("$[0].description", is("sleeping")))
                .andExpect(jsonPath("$[0].completed", is(false)));
    }

    @Test
    void shouldReturnTodoTaskWhenTheirIdIsPassed() throws Exception {
        todo = new Todo("sleeping", false);
        todoRepository.save(todo);

        this.mockMvc
                .perform(get("/api/todo/{id}",todo.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.description", is("sleeping")));
    }

    @Test
    void shouldReturn404WhenIdIsNotFound() throws Exception {
        todo = new Todo("sleeping", false);

        this.mockMvc
                .perform(get("/api/todo/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateTheTodoTaskWhenTheirIdIsPassed() throws Exception {
        todo = new Todo("sleeping", false);
        Todo newTodo = new Todo( "Reading", true);

        todoRepository.save(todo);


        this.mockMvc
                .perform(put("/api/todo/{id}",todo.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTodo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(todo.getId().intValue())))
                .andExpect(jsonPath("$.description", is("Reading")))
                .andExpect(jsonPath("$.completed", is(true)));

    }

    @Test
    void shouldReturn404WhenUpdateIdIsNotFound() throws Exception {
        todo = new Todo("sleeping", false);


        this.mockMvc
                .perform(put("/api/todo/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteATodoTaskWithTheirId() throws Exception {
        todo = new Todo("sleeping", false);
        todoRepository.save(todo);

        this.mockMvc
                .perform(delete("/api/todo/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("sleeping")))
                .andExpect(jsonPath("$.completed", is(false)));
    }
}
