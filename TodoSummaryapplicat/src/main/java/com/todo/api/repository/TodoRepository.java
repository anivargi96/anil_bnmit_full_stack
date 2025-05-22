package com.todo.api.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.todo.api.model.Todo;

public interface TodoRepository extends CrudRepository<Todo, Long> {
    List<Todo> findByCompletedFalse();
}
