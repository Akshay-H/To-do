package com.tw.todo.repository;

import com.tw.todo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

interface TodoRepository extends JpaRepository<Todo, Integer> {

}