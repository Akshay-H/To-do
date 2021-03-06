package com.tw.todo.service;

import com.tw.todo.exception.TodoAlreadyExistsException;
import com.tw.todo.model.Todo;
import com.tw.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo saveTodo(Todo todo) throws TodoAlreadyExistsException {
        Optional<Todo> savedTodo = todoRepository.findById(todo.getId());
        if(savedTodo.isPresent()){
            throw new TodoAlreadyExistsException("Todo already exists");
        }
        return todoRepository.save(todo);
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Todo getTodoById(int id) {
        return todoRepository.findById(id).get();
    }

    public Todo updateTodoName(Todo todo, String name) {
        todo.setName(name);
        return todoRepository.save(todo);
    }

    public void deleteTodo(Todo todo) {
        todoRepository.delete(todo);
    }
}
