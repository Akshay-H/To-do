package com.tw.todo.repository;

import com.tw.todo.model.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    void shouldReturnSavedTodoWhenSavingATodo() {

        Todo todo = new Todo(1, "First todo");
        int expectedSavedId = 1;

        Todo savedTodo = todoRepository.save(todo);

        assertNotNull(savedTodo);
        assertEquals(expectedSavedId, savedTodo.getId());

    }

    @Test
    void shouldReturnUpdatedTodoWhenUpdatingATodo() {

        Todo todo = new Todo(1, "First todo");
        todoRepository.save(todo);
        Todo savedTodo = todoRepository.findById(todo.getId()).get();

        String nameToUpdate = "Modified todo";
        savedTodo.setName(nameToUpdate);
        Todo updatedTodo = todoRepository.save(savedTodo);

        assertNotNull(updatedTodo);
        assertEquals(nameToUpdate,todoRepository.findById(updatedTodo.getId()).get().getName());

    }


    @Test
    void shouldReturnAllTodoWhenGetAllTodo() {

        Todo firstTodo = new Todo(1, "First todo");
        Todo secondTodo = new Todo(2, "Second todo");
        todoRepository.save(firstTodo);
        todoRepository.save(secondTodo);

        List<Todo> allTodo = todoRepository.findAll();

        assertNotNull(allTodo);
        assertEquals(2,allTodo.size());

    }

    @Test
    void shouldReturnTodoWhenGetTodoById() {

        Todo firstTodo = new Todo(1, "First todo");
        todoRepository.save(firstTodo);
        int expectedId = firstTodo.getId();

        Todo expectedTodo = todoRepository.findById(firstTodo.getId()).get();

        assertNotNull(expectedTodo);
        assertEquals(expectedId,expectedTodo.getId());

    }

    @Test
    void shouldReturnTodoWhenGetTodoByName() {

        Todo firstTodo = new Todo(1, "First todo");
        todoRepository.save(firstTodo);
        int expectedId = firstTodo.getId();

        Todo expectedTodo = todoRepository.findByName(firstTodo.getName()).get();

        assertNotNull(expectedTodo);
        assertEquals(expectedId,expectedTodo.getId());

    }

}
