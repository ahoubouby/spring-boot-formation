package com.ahoubouby.demo.apis;

import com.ahoubouby.demo.dmain.Todo;
import com.ahoubouby.demo.dmain.TodoBuilder;
import com.ahoubouby.demo.repos.ToDoRepository;
import com.ahoubouby.demo.validations.ToDoValidationError;
import com.ahoubouby.demo.validations.ToDoValidationErrorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TodoController {

    private ToDoRepository TodoRepository;

    @Autowired
    public TodoController(ToDoRepository TodoRepository) {
        this.TodoRepository = TodoRepository;
    }

    @GetMapping("/todo")
    public ResponseEntity<Iterable<Todo>> getTodos() {
        return ResponseEntity.ok(TodoRepository.findAll());
    }

    @GetMapping("/todo/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable String id) {
        Optional<Todo> Todo = TodoRepository.findById(id);
        return Todo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PatchMapping("/todo/{id}")
    public ResponseEntity<Todo> setCompleted(@PathVariable String id) {
        Optional<Todo> Todo = TodoRepository.findById(id);
        if (!Todo.isPresent())
            return ResponseEntity.notFound().build();

        Todo result = Todo.get();
        result.setCompleted(true);
        TodoRepository.save(result);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.ok().header("Location", location.toString()).build();
    }

    @RequestMapping(value = "/todo", method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<?> createTodo(@Valid @RequestBody Todo Todo, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ToDoValidationErrorBuilder.fromBindingErrors(errors));
        }

        Todo result = TodoRepository.save(Todo);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/todo/{id}")
    public ResponseEntity<Todo> deleteTodo(@PathVariable String id) {
        TodoRepository.delete(TodoBuilder.create().withId(id).build());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/todo")
    public ResponseEntity<Todo> deleteTodo(@RequestBody Todo Todo) {
        TodoRepository.delete(Todo);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ToDoValidationError handleException(Exception exception) {
        return new ToDoValidationError(exception.getMessage());
    }

}
