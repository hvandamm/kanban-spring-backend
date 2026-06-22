package com.example.demo1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) 
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository; 

    @InjectMocks
    private TaskService taskService; 

    @Test
    public void should_SetDefaultStatusToTodo_When_CreatingNewTask() {
        Task inputTask = new Task();
        inputTask.setTitle("Write Unit Tests");
        inputTask.setDescription("Learn Mockito framework");

        Task savedTask = new Task(1L, "Write Unit Tests", "Learn Mockito framework", TaskStatus.TODO);
        
        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        Task result = taskService.createTask(inputTask);

        assertNotNull(result.getId());
        assertEquals(TaskStatus.TODO, result.getStatus()); 
    }

    @Test
    public void should_AllowValidStatusTransition_When_UpdatingTask() {
        // GIVEN: An existing task in TODO status
        Long taskId = 1L;
        Task existingTask = new Task(taskId, "Test Task", "Description", TaskStatus.TODO);
        Task updatedTaskInfo = new Task(taskId, "Test Task", "Description", TaskStatus.IN_PROGRESS);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTaskInfo);

        // WHEN: We transition from TODO to IN_PROGRESS
        Task result = taskService.updateTask(taskId, updatedTaskInfo);

        // THEN: The transition succeeds
        assertEquals(TaskStatus.IN_PROGRESS, result.getStatus());
    }

    @Test
    public void should_ThrowException_When_TransitionIsInvalid() {
        // GIVEN: An existing task in TODO status
        Long taskId = 1L;
        Task existingTask = new Task(taskId, "Test Task", "Description", TaskStatus.TODO);
        
        // Target state is DONE, which violates our rule (cannot skip IN_PROGRESS)
        Task illegalUpdatedTaskInfo = new Task(taskId, "Test Task", "Description", TaskStatus.DONE);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));

        // WHEN & THEN: Verify that trying to update throws an IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            taskService.updateTask(taskId, illegalUpdatedTaskInfo);
        });

        // Verify the message matches our guardrail message
        assertEquals("Invalid transition: Cannot move a task directly from TODO to DONE.", exception.getMessage());
    }
}