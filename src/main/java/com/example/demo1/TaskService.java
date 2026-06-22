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
        if (task.getStatus() == null) {
            task.setStatus(TaskStatus.TODO); 
        }
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id)
            .map(existingTask -> {
                // Validate state transition before modifying the entity
                validateStatusTransition(existingTask.getStatus(), updatedTask.getStatus());

                existingTask.setTitle(updatedTask.getTitle());
                existingTask.setDescription(updatedTask.getDescription());
                existingTask.setStatus(updatedTask.getStatus());
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

    /**
     * Helper method to enforce Kanban state machine rules.
     */
    private void validateStatusTransition(TaskStatus currentStatus, TaskStatus newStatus) {
        // If status isn't changing, it's always valid
        if (currentStatus == newStatus || newStatus == null) {
            return;
        }

        // Rule: Cannot jump directly from TODO to DONE
        if (currentStatus == TaskStatus.TODO && newStatus == TaskStatus.DONE) {
            throw new IllegalArgumentException("Invalid transition: Cannot move a task directly from TODO to DONE.");
        }

        // Rule: Cannot move backwards from DONE to IN_PROGRESS or TODO (Optional, depending on your board style)
        if (currentStatus == TaskStatus.DONE) {
            throw new IllegalArgumentException("Invalid transition: Completed tasks cannot be reopened.");
        }
    }
}