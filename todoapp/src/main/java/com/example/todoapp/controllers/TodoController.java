package com.example.todoapp.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todoapp.models.Todo;
import com.example.todoapp.repositories.TodoRepository;

@RestController
@RequestMapping("/todoapi")
@CrossOrigin("*")
public class TodoController {

	@Autowired
	TodoRepository todoRepository;

	@GetMapping("/todos")
	public List<Todo> fetchAllTodos() {
		return todoRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
	}

	@PostMapping("/todos")
	public Todo createTodo(@Valid @RequestBody Todo todo) {
		return todoRepository.save(todo);
	}

	@GetMapping("/todos/{id}")
	public ResponseEntity<Todo> fetchTodoById(@PathVariable("id") String id) {
		return todoRepository.findById(id).map(todo -> ResponseEntity.ok().body(todo))
				.orElse(ResponseEntity.notFound().build());

	}

	@PutMapping("/todos/{id}")
	public ResponseEntity<Todo> updateTodo(@PathVariable("id") String id, @Valid @RequestBody Todo todo) {
		return todoRepository.findById(id).map(todoObject -> {
			todoObject.setTitle(todo.getTitle());
			todoObject.setCompleted(todo.getCompleted());
			Todo updatedTodo = todoRepository.save(todoObject);
			return ResponseEntity.ok().body(updatedTodo);

		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/todos/{id}")
	public ResponseEntity<?> deleteTodo(@PathVariable("id") String id) {
		return todoRepository.findById(id).map(todo -> {
			todoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}).orElse(ResponseEntity.notFound().build());
	}

}
