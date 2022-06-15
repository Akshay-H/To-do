package com.tw.todo.service;

import com.tw.todo.model.Todo;
import com.tw.todo.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class TodoServiceTest {

    private TodoRepository todoRepository;

    @Test
    void shouldSaveTodoWhenNewTodoIsSaved() {

        Todo todo = new Todo(1, "First Todo");
        todoRepository = Mockito.mock(TodoRepository.class);
        TodoService todoService = new TodoService(todoRepository);
        when(todoRepository.save(todo)).thenReturn(todo);

        Todo savedTodo = todoService.saveTodo(todo);

        assertNotNull(savedTodo);

    }
}
