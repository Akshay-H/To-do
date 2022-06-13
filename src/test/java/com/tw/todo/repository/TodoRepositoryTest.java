package com.tw.todo.repository;

import com.tw.todo.model.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
}
