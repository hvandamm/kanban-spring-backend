package com.example.demo1.controller;

import com.example.demo1.Board;
import com.example.demo1.BoardService;
import com.example.demo1.Task;
import com.example.demo1.TaskController;
import com.example.demo1.TaskService;
import com.example.demo1.TaskStatus;
import com.example.demo1.dto.TaskRequest;
import com.example.demo1.exception.ResourceNotFoundException;
import org.mockito.Mockito;
import tools.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @MockitoBean
    private BoardService boardService;

    @Autowired
    private JsonMapper objectMapper;

    @Test
    void shouldReturn400WhenTitleIsBlank() throws Exception {
        TaskRequest invalidRequest = new TaskRequest();
        invalidRequest.setTitle("");
        invalidRequest.setDescription("Testing validation bound");
        invalidRequest.setBoardId(1L); // Provide a valid boardId to isolate title validation

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.errors.title").value("Title is required"));
    }

    @Test
    void shouldReturn404WhenTaskNotFound() throws Exception {
        Mockito.when(taskService.getTaskById(999L))
                .thenThrow(new ResourceNotFoundException("Task not found with id: 999"));

        mockMvc.perform(get("/api/tasks/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Task not found with id: 999"));
    }

    @Test
    void shouldReturn201WhenTaskIsCreated() throws Exception {
        TaskRequest validRequest = new TaskRequest();
        validRequest.setTitle("New Task");
        validRequest.setDescription("Task description");
        validRequest.setBoardId(1L);

        Board board = new Board(1L, "My Board", null);
        Task createdTask = new Task(1L, "New Task", "Task description", TaskStatus.TODO);
        createdTask.setBoard(board);

        Mockito.when(boardService.getBoardById(1L)).thenReturn(board);
        Mockito.when(taskService.createTask(Mockito.any(Task.class))).thenReturn(createdTask);

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("New Task"))
                .andExpect(jsonPath("$.board.name").value("My Board"));
    }
}