package com.example.demo1;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    // Dependency Injection: Spring automatically passes the TaskRepository here
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Get all tasks from the database
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Save a new task to the database
    public Task createTask(Task task) {
        if (task.getStatus() == null) {
            task.setStatus("TODO"); // Default status for new tasks
        }
        return taskRepository.save(task);
    }

    // Update an existing task's status or details
    public Task updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id)
            .map(existingTask -> {
                existingTask.setTitle(updatedTask.getTitle());
                existingTask.setDescription(updatedTask.getDescription());
                existingTask.setStatus(updatedTask.getStatus());
                return taskRepository.save(existingTask);
            })
            .orElseThrow(() -> new RuntimeException("Task not found with id " + id));
    }

    // Delete a task from the database
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found with id " + id);
        }
        taskRepository.deleteById(id);
    }

}