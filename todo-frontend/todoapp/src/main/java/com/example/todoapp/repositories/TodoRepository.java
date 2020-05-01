package com.example.todoapp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.todoapp.models.Todo;

@Repository
public interface TodoRepository extends MongoRepository<Todo, String> {

}
