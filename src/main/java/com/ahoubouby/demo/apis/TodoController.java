package com.ahoubouby.demo.apis;

import com.ahoubouby.demo.dmain.Todo;
import com.ahoubouby.demo.repos.CommonRepository;
import com.ahoubouby.demo.repos.ToDoRepository;
import com.ahoubouby.demo.validations.ToDoValidationError;
import com.ahoubouby.demo.validations.ToDoValidationErrorBuilder;
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

    private ToDoRepository repository;

    public TodoController(ToDoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/todo")
    public ResponseEntity<Iterable<Todo>> getToDos() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/todo/{id}")
    public ResponseEntity<Todo> getToDoById(@PathVariable String id) {
        Optional<Todo> toDo = repository.findById(id);
        return toDo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PatchMapping("/todo/{id}")
    public ResponseEntity<Todo> setCompleted(@PathVariable String id) {
        Optional<Todo> toDo = repository.findById(id);
        if (!toDo.isPresent())
            return ResponseEntity.notFound().build();
        Todo result = toDo.get();
        result.setCompleted(true);
        repository.save(result);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.ok().header("Location", location.toString()).
                build();
    }

    @RequestMapping(value = "/todo", method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<?> createToDo(@Valid @RequestBody Todo toDo, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().
                    body(ToDoValidationErrorBuilder.fromBindingErrors(errors));
        }
        Todo result = repository.save(toDo);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().
                path("/{id}")
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/todo/{id}")
    public ResponseEntity<Todo> deleteToDo(@PathVariable String id) {
        Todo todo = new Todo();
        todo.setId(id);
        repository.delete(todo);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/todo")
    public ResponseEntity<Todo> deleteToDo(@RequestBody Todo toDo) {
        repository.delete(toDo);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ToDoValidationError handleException(Exception exception) {
        return new ToDoValidationError(exception.getMessage());
    }
}
