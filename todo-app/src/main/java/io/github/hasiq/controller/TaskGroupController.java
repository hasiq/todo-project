package io.github.hasiq.controller;

import io.github.hasiq.logic.TaskGroupService;
import io.github.hasiq.model.Task;
import io.github.hasiq.model.TaskRepository;
import io.github.hasiq.model.projection.GroupReadModel;
import io.github.hasiq.model.projection.GroupWriteModel;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("groups")
class TaskGroupController {
    public static final Logger logger = LoggerFactory.getLogger(TaskGroupController.class);
    private final TaskGroupService taskGroupService;
    private final TaskRepository taskRepository;

    TaskGroupController(TaskGroupService taskGroupService, TaskRepository taskRepository) {
        this.taskGroupService = taskGroupService;
        this.taskRepository = taskRepository;
    }


    @PostMapping
     ResponseEntity<GroupReadModel> createTaskGroup(@RequestBody @Valid GroupWriteModel task) {
        GroupReadModel result = taskGroupService.createGroup(task);
         return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
     }
     @GetMapping
    ResponseEntity<List<GroupReadModel>> readAllGroups() {
       return ResponseEntity.ok(taskGroupService.readAll());
     }

     @GetMapping("/{id}")
     ResponseEntity<List<Task>> readAllTasksFromGroup(@PathVariable int id){
        return ResponseEntity.ok(taskRepository.findAllByGroup_Id(id));
     }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<?> toggleGroup(@PathVariable int id) {
        taskGroupService.toggleGroup(id);
         return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<?> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> handleIllegalState(IllegalStateException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
