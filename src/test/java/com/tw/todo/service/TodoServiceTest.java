package com.tw.todo.service;

import com.tw.todo.exception.TodoAlreadyExistsException;
import com.tw.todo.model.Todo;
import com.tw.todo.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class TodoServiceTest {

    private TodoRepository todoRepository;

    @Test
    void shouldSaveTodoWhenNewTodoIsSaved() throws TodoAlreadyExistsException {

        Todo todo = new Todo("First Todo");
        todoRepository = Mockito.mock(TodoRepository.class);
        TodoService todoService = new TodoService(todoRepository);
        when(todoRepository.findById(todo.getId())).thenReturn(Optional.empty());
        when(todoRepository.save(todo)).thenReturn(todo);

        Todo savedTodo = todoService.saveTodo(todo);

        assertNotNull(savedTodo);
        assertEquals(todo, savedTodo);

    }

    @Test
    void shouldThrowTodoAlreadyExistsExceptionTodoWhenSavingAlreadyExistingTodo() {

        Todo todo = new Todo("First Todo");
        todoRepository = Mockito.mock(TodoRepository.class);
        TodoService todoService = new TodoService(todoRepository);
        when(todoRepository.findById(todo.getId())).thenReturn(Optional.of(todo));
        when(todoRepository.save(todo)).thenReturn(todo);

        assertThrows(TodoAlreadyExistsException.class, () -> {
            todoService.saveTodo(todo);
        });

    }
}
