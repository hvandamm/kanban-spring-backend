package com.example.demo1;

import org.springframework.stereotype.Service;
import com.example.demo1.exception.ResourceNotFoundException;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }

    public Task createTask(Task task) {
        // Updated: Using the Enum value instead of a literal String
        if (task.getStatus() == null) {
            task.setStatus(TaskStatus.TODO); 
        }
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id)
            .map(existingTask -> {
                existingTask.setTitle(updatedTask.getTitle());
                existingTask.setDescription(updatedTask.getDescription());
                existingTask.setStatus(updatedTask.getStatus()); // Maps the enum directly
                return taskRepository.save(existingTask);
            })
            .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task not found with id " + id);
        }
        taskRepository.deleteById(id);
    }
}