package com.shankarstudy.todo.service.impl;

import com.shankarstudy.todo.dto.TodoDto;
import com.shankarstudy.todo.entity.Todo;
import com.shankarstudy.todo.exception.ResourceNotFoundException;
import com.shankarstudy.todo.repository.TodoRepository;
import com.shankarstudy.todo.service.TodoService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {

    private TodoRepository todoRepository;
    // ModelMapper object is used to map JPA entity to Dto and vice versa
    private ModelMapper modelMapper;

//    @Autowired
//    public TodoServiceImpl(TodoRepository theTodoRepository) {
//        this.todoRepository = theTodoRepository;
//    }
    // We can also use @AllArgsConstructor for dependency injection, instead of constructor way

    @Override
    public TodoDto addTodo(TodoDto todoDto) {

        // convert TodoDto into Todo JPA Entity
//        Todo todo = new Todo();
//        todo.setTitle(todoDto.getTitle());
//        todo.setDescription(todoDto.getDescription());
//        todo.setCompleted(todoDto.isCompleted());

        // Using ModelMapper for mapping
        Todo todo = modelMapper.map(todoDto, Todo.class);

        // Save JPA entity in DB
        Todo savedTodo = todoRepository.save(todo);

        // Convert saved Todo JPA entity object into TodoDto object
//        TodoDto savedTodoDto = new TodoDto();
//        savedTodoDto.setId(savedTodo.getId());
//        savedTodoDto.setTitle(savedTodo.getTitle());
//        savedTodoDto.setDescription(savedTodo.getDescription());
//        savedTodoDto.setCompleted(savedTodo.isCompleted());

        TodoDto savedTodoDto = modelMapper.map(savedTodo, TodoDto.class);

        return savedTodoDto;
    }

    @Override
    public TodoDto getTodo(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Todo not found with id: "+ id)); // Used orElse(null) in place of get()
        return modelMapper.map(todo, TodoDto.class);
    }

    @Override
    public List<TodoDto> getAllTodos() {
        List<Todo> todos = todoRepository.findAll();
        return todos.stream().map((todo) -> modelMapper.map(todo, TodoDto.class)).collect(Collectors.toList());
    }

    @Override
    public TodoDto updateTodo(TodoDto todoDto, Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: "+ id));

        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todoDto.isCompleted());

        Todo updatedTodo = todoRepository.save(todo);
        return modelMapper.map(updatedTodo, TodoDto.class);
    }

    @Override
    public void deleteTodo(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));

        todoRepository.deleteById(id);
    }

    @Override
    public TodoDto completeTodo(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));

        todo.setCompleted(Boolean.TRUE);

        Todo completedTodo = todoRepository.save(todo);

        return modelMapper.map(completedTodo, TodoDto.class);
    }

    @Override
    public TodoDto inCompleteTodo(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));

        todo.setCompleted(Boolean.FALSE);

        Todo incompletedTodo = todoRepository.save(todo);

        return modelMapper.map(incompletedTodo, TodoDto.class);
    }
}
