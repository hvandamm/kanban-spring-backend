package com.example.demo1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo1.dto.TaskRequest;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/tasks") // Sets the base URL path for this controller
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // GET Request to fetch all tasks: http://localhost:8080/api/tasks
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    // POST Request to submit a new task: http://localhost:8080/api/tasks
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody TaskRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        // status logic handled in service layer
        return new ResponseEntity<>(taskService.createTask(task), HttpStatus.CREATED);
    }

    // PUT Request to update a task: http://localhost:8080/api/tasks/1
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        return taskService.updateTask(id, updatedTask);
    }

    // DELETE Request to destroy a task: http://localhost:8080/api/tasks/1
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }



}