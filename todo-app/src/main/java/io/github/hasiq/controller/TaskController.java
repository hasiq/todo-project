package io.github.hasiq.controller;

import io.github.hasiq.model.Task;
import io.github.hasiq.model.TaskRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository repository;

    TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    ResponseEntity<?> createTask(@RequestBody @Valid Task task){
        Task save = repository.save(task);
        return ResponseEntity.created(URI.create("/" + save.getId())).body(save);
    }


    @GetMapping
    ResponseEntity<List<Task>> readAllTasks(Pageable page){
        logger.info("Custom pageable");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }

    @GetMapping("/{id}")
    ResponseEntity<?> readTask(@PathVariable int id){
       return repository.findById(id)
               .map(ResponseEntity::ok)
               .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/search/done")
    ResponseEntity<List<Task>> readDoneTasks(@RequestParam(defaultValue = "true") boolean state){
        return ResponseEntity.ok(repository.findByDone(state));
    }


    @PutMapping("/{id}")
    ResponseEntity<?>updateTask(@PathVariable int id, @RequestBody @Valid Task toUpdate){
        if(!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
      repository.findById(id)
              .ifPresent(task -> {
                  task.updateFrom(toUpdate);
                  repository.save(task);
              });
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<?>toggleTask(@PathVariable int id){
        if(!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.findById(id)
                .ifPresent(task -> task.setDone(!task.isDone()));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/today")
    ResponseEntity<List<Task>> readTodayTasks(){
        LocalDateTime today = LocalDateTime.now();
        List<Task> tasks = repository.findAllByDeadlineBeforeAndDoneIsFalseOrDeadlineIsNullAndDoneIsFalse(today);
        return ResponseEntity.ok(tasks);
    }
}
