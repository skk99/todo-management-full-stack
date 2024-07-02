package com.shankarstudy.todo.repository;

import com.shankarstudy.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    // Get CRUD methods for free for Todo Entity using id as Long Primary Key
}
