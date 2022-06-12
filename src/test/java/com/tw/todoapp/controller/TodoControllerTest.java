package com.tw.todoapp.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.todoapp.entity.Todo;
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
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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


}
