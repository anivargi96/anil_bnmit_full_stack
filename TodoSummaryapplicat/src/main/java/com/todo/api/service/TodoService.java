package com.todo.api.service;

import org.springframework.stereotype.Service;
import com.todo.api.model.Todo;
import com.todo.api.repository.TodoRepository;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository repo;

    public TodoService(TodoRepository repo) {
        this.repo = repo;
    }

    public List<Todo> getAll() {
        return (List<Todo>) repo.findAll();
    }

    public Todo addTodo(Todo todo) {
        return repo.save(todo);
    }

    public void deleteTodo(Long id) {
        repo.deleteById(id);
    }

    public List<Todo> getPendingTodos() {
        return repo.findByCompletedFalse();
    }
}
