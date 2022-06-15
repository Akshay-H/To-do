package com.tw.todo.repository;

import com.tw.todo.model.Todo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    public Todo todo;

    @BeforeEach
    void setUp() {
        todo = new Todo(1, "First todo");
    }

    @AfterEach
    void tearDown() {
        if (todo != null) {
            todoRepository.delete(todo);
        }
    }

    @Test
    void shouldReturnSavedTodoWhenSavingATodo() {

        int expectedSavedId = 1;

        Todo savedTodo = todoRepository.save(todo);

        assertNotNull(savedTodo);
        assertEquals(expectedSavedId, savedTodo.getId());

    }

    @Test
    void shouldReturnUpdatedTodoWhenUpdatingATodo() {

        todoRepository.save(todo);
        Todo savedTodo = todoRepository.findById(todo.getId()).get();

        String nameToUpdate = "Modified todo";
        savedTodo.setName(nameToUpdate);
        Todo updatedTodo = todoRepository.save(savedTodo);

        assertNotNull(updatedTodo);
        assertEquals(nameToUpdate, todoRepository.findById(updatedTodo.getId()).get().getName());

    }


    @Test
    void shouldReturnAllTodoWhenGetAllTodo() {

        Todo anotherTodo = new Todo(2, "Second todo");
        todoRepository.save(todo);
        todoRepository.save(anotherTodo);

        List<Todo> allTodo = todoRepository.findAll();

        assertNotNull(allTodo);
        assertEquals(2, allTodo.size());

    }

    @Test
    void shouldReturnTodoWhenGetTodoById() {

        todoRepository.save(todo);
        int expectedId = todo.getId();

        Todo expectedTodo = todoRepository.findById(todo.getId()).get();

        assertNotNull(expectedTodo);
        assertEquals(expectedId, expectedTodo.getId());

    }

    @Test
    void shouldReturnTodoWhenGetTodoByName() {

        todoRepository.save(todo);
        int expectedId = todo.getId();

        Todo expectedTodo = todoRepository.findByName(todo.getName()).get();

        assertNotNull(expectedTodo);
        assertEquals(expectedId, expectedTodo.getId());

    }

    @Test
    void shouldDeleteTodoWhenDeletingTodo() {

        int expectedId = todo.getId();

        todoRepository.delete(todo);
        Optional<Todo> expectedTodo = todoRepository.findById(expectedId);

        assertThat(expectedTodo).isEmpty();

    }

}
