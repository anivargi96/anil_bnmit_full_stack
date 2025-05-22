package com.todo.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.todo.api.model.Todo;
import com.todo.api.service.SlackService;
import com.todo.api.service.SummaryService;
import com.todo.api.service.TodoService;

import java.util.List;

@RestController
@RequestMapping("/todos")
@CrossOrigin(origins = "*")
public class TodoController {

    private final TodoService todoService;
    private final SummaryService summaryService;
    private final SlackService slackService;

    public TodoController(TodoService todoService, SummaryService summaryService, SlackService slackService) {
        this.todoService = todoService;
        this.summaryService = summaryService;
        this.slackService = slackService;
    }

    @GetMapping
    public List<Todo> getAllTodos() {
        return todoService.getAll();
    }

    @PostMapping
    public Todo addTodo(@RequestBody Todo todo) {
        return todoService.addTodo(todo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.ok("Todo with ID " + id + " deleted successfully.");
    }
    
    @PostMapping("/summarize")
    public ResponseEntity<String> summarizeAndSend() {
        try {
            List<Todo> pendingTodos = todoService.getPendingTodos();

            if (pendingTodos.isEmpty()) {
                return ResponseEntity.ok("No pending todos to summarize.");
            }

            String summary = summaryService.summarize(pendingTodos);
            boolean sent = slackService.postMessage(summary);

            return ResponseEntity.ok(sent ? " sent to Slack" : "Failed to send to Slack");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    
    }
}
