package com.tw.todo.service;

import com.tw.todo.exception.TodoAlreadyExistsException;
import com.tw.todo.model.Todo;
import com.tw.todo.repository.TodoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class TodoServiceTest {

    private Todo todo;
    private Todo anotherTodo;
    private TodoRepository todoRepository = Mockito.mock(TodoRepository.class);
    private TodoService todoService;

    @BeforeEach
    void setUp() {
        todo = new Todo("First todo");
        anotherTodo = new Todo("Second todo");
        todoService = new TodoService(todoRepository);

    }

    @AfterEach
    void tearDown() {
        if (todo != null) {
            todoRepository.delete(todo);
        }
        if (anotherTodo != null) {
            todoRepository.delete(todo);
        }
    }

    @Test
    void shouldSaveTodoWhenNewTodoIsSaved() throws TodoAlreadyExistsException {

        when(todoRepository.findById(todo.getId())).thenReturn(Optional.empty());
        when(todoRepository.save(todo)).thenReturn(todo);

        Todo savedTodo = todoService.saveTodo(todo);

        assertNotNull(savedTodo);
        assertEquals(todo, savedTodo);

    }

    @Test
    void shouldThrowTodoAlreadyExistsExceptionTodoWhenSavingAlreadyExistingTodo() {

        when(todoRepository.findById(todo.getId())).thenReturn(Optional.of(todo));
        when(todoRepository.save(todo)).thenReturn(todo);

        assertThrows(TodoAlreadyExistsException.class, () -> {
            todoService.saveTodo(todo);
        });

    }

    @Test
    void shouldReturnAllTodosWhenGetAllTodo() {

        List<Todo> expectedTodo = List.of(todo, anotherTodo);
        when(todoRepository.findAll()).thenReturn(expectedTodo);

        List<Todo> expectedTodos = todoService.getAllTodos();

        assertNotNull(expectedTodos);
        assertEquals(2, expectedTodos.size());

    }

    @Test
    void shouldReturnTodoWhenGetById() {

        when(todoRepository.findById(todo.getId())).thenReturn(Optional.of(todo));

        Todo expectedTodo = todoService.getTodoById(todo.getId());

        assertNotNull(expectedTodo);
        assertEquals(todo.getId(), expectedTodo.getId());

    }

}
