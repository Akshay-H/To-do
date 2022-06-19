package com.tw.todo.service;

import com.tw.todo.exception.TodoAlreadyExistsException;
import com.tw.todo.model.Todo;
import com.tw.todo.repository.TodoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

        todoService.saveTodo(todo);

        verify(todoRepository).save(todo);

    }

    @Test
    void shouldThrowTodoAlreadyExistsExceptionTodoWhenSavingAlreadyExistingTodo() {

        when(todoRepository.findById(todo.getId())).thenReturn(Optional.of(todo));
        when(todoRepository.save(todo)).thenReturn(todo);

        assertThrows(TodoAlreadyExistsException.class, () -> {
            todoService.saveTodo(todo);
        });
        verify(todoRepository, never()).save(any(Todo.class));

    }

    @Test
    void shouldReturnAllTodosWhenGetAllTodo() {

        todoService.getAllTodos();

        verify(todoRepository).findAll();

    }

    @Test
    void shouldReturnTodoWhenGetById() {

        when(todoRepository.findById(todo.getId())).thenReturn(Optional.ofNullable(todo));

        Todo fetchedTodo = todoService.getTodoById(todo.getId());

        assertEquals(todo, fetchedTodo);
    }

    @Test
    void shouldReturnUpdatedTodoWhenUpdatingATodo() {

        todo.setName(anotherTodo.getName());

        todoService.updateTodoName(todo, anotherTodo.getName());

        verify(todoRepository).save(todo);

    }

    @Test
    void shouldDeleteTodoWhenDeleteTodo() {

        todoService.deleteTodo(todo);

        verify(todoRepository).delete(todo);

    }
}
