package com.tw.todo.exception;

public class TodoAlreadyExistsException extends Throwable{
    public TodoAlreadyExistsException(String message) {
        super(message);
    }
}
