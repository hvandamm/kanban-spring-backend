package com.example.demo1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Tells JUnit to use the Mockito framework
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository; // 1. Fake the database layer

    @InjectMocks
    private TaskService taskService; // 2. Inject that fake database into the real service

    @Test
    public void should_SetDefaultStatusToTodo_When_CreatingNewTask() {
        // GIVEN: A mock task with no status
        Task inputTask = new Task();
        inputTask.setTitle("Write Unit Tests");
        inputTask.setDescription("Learn Mockito framework");

        Task savedTask = new Task(1L, "Write Unit Tests", "Learn Mockito framework", TaskStatus.TODO);
        
        // Train our fake database to return our savedTask when save() is called
        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        // WHEN: We call the service layer to create the task
        Task result = taskService.createTask(inputTask);

        // THEN: Verify the business rules worked perfectly!
        assertNotNull(result.getId());
        assertEquals(TaskStatus.TODO, result.getStatus()); // Asserts that the default status was correctly applied
    }
}