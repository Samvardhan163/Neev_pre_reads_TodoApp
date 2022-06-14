package com.tw.todoapp.repository;

import com.tw.todoapp.entity.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    void shouldSaveNewTodoTask()
    {
        Todo todo=new Todo(1L,"playing",false);

        Todo savedTodo=todoRepository.save(todo);

        assertThat(savedTodo).isNotNull();
    }

    @Test
    void shouldReturnTwoTodoTask()
    {
        Todo todo=new Todo(1L,"playing",false);
        Todo todo1=new Todo(2L,"sleeping",false);
        todoRepository.save(todo);
        todoRepository.save(todo1);

       List<Todo> fetchedTodo =todoRepository.findAll();

       assertThat(fetchedTodo.size()).isGreaterThan(0);
    }

    @Test
    void shouldReturnTodoTaskByItsId()
    {
        Todo todo=new Todo(1L,"playing",false);
        todoRepository.save(todo);

        Optional<Todo> fetchedTodo =todoRepository.findById(1L);

       assertEquals(fetchedTodo.get().getDescription(),todo.getDescription());
    }

    @Test
    void shouldReturnTheUpdatedTodoTaskByItsId() {
        Todo todo=new Todo(1L,"playing",false);
        todoRepository.save(todo);
        Todo savedTodo=todoRepository.findById(todo.getId()).get();
       savedTodo.setDescription("sleeping");
       savedTodo.setCompleted(true);

       Todo updatedTodo=todoRepository.save(savedTodo);

       assertThat(updatedTodo.getDescription()).isEqualTo("sleeping");
       assertThat(updatedTodo.isCompleted()).isEqualTo(true);
    }
}
