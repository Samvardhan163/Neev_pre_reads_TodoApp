package com.tw.todoapp.controller;

import com.tw.todoapp.entity.Todo;
import com.tw.todoapp.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping
    public ResponseEntity<Todo> createTodoTask(@RequestBody Todo todolist, UriComponentsBuilder uriComponentsBuilder) {
        Todo todoTask = todoService.createTodoTask(todolist);
        UriComponents uriComponents =uriComponentsBuilder.path("/api/todo/{id}").buildAndExpand(todolist.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponents.toUri());

        return new ResponseEntity<>(todolist,headers, HttpStatus.CREATED);
    }

    @GetMapping
    public  ResponseEntity<List<Todo>> getAllTodoTask()
    {
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<>(todoService.getAllTodoTask(), responseHeaders,HttpStatus.OK);
    }

}
