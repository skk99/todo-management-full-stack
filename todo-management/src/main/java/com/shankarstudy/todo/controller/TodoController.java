package com.shankarstudy.todo.controller;

import com.shankarstudy.todo.dto.TodoDto;
import com.shankarstudy.todo.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/todos")
@AllArgsConstructor
public class TodoController {

    private TodoService todoService;

    // Build Add Todo REST API
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")   // Only ADMIN can access this ReST API
    public ResponseEntity<TodoDto> addTodo(@RequestBody TodoDto todoDto) {
        TodoDto savedTodoDto = todoService.addTodo(todoDto);
        return new ResponseEntity<>(savedTodoDto, HttpStatus.CREATED);
    }

    // Build Get Todo REST API
    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")  // ADMIN and USER can access this ReST API
    public ResponseEntity<TodoDto> getTodo(@PathVariable("id") Long todoId) {
        TodoDto todoDto = todoService.getTodo(todoId);
        return new ResponseEntity<>(todoDto, HttpStatus.OK);
    }

    // Build Get All Todo Rest API
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")  // ADMIN and USER can access this ReST API
    public ResponseEntity<List<TodoDto>> getAllTodos() {
        List<TodoDto> todoDtos = todoService.getAllTodos();
//        return new ResponseEntity<>(todoDtos, HttpStatus.OK);
        // Writing shortcut for above statement
        return ResponseEntity.ok(todoDtos);
    }

    // Build Update Todo Rest API
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")  // Only ADMIN can access this ReST API
    public ResponseEntity<TodoDto> updateTodo(@RequestBody TodoDto todoDto, @PathVariable("id") Long todoId) {
        TodoDto updatedDto = todoService.updateTodo(todoDto, todoId);
        return ResponseEntity.ok(updatedDto);
    }

    // Build DELETE Todo Rest API
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")    // Only ADMIN can access this ReST API
    public ResponseEntity<String> deleteTodo(@PathVariable("id") Long todoId) {
        todoService.deleteTodo(todoId);
        return ResponseEntity.ok("Todo deleted successfully");
    }

    // Build Complete Todo Rest API
    @PatchMapping("{id}/complete")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")  // ADMIN and USER can access this ReST API
    public ResponseEntity<TodoDto> completeTodo(@PathVariable("id") Long todoId) {
        TodoDto completedTodoDto = todoService.completeTodo(todoId);
        return ResponseEntity.ok(completedTodoDto);
    }

    // Build Incomplete Todo Rest API
    @PatchMapping("{id}/in-complete")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")  // ADMIN and USER can access this ReST API
    public ResponseEntity<TodoDto> inCompleteTodo(@PathVariable("id") Long todoId) {
        TodoDto completedTodoDto = todoService.inCompleteTodo(todoId);
        return ResponseEntity.ok(completedTodoDto);
    }
}
