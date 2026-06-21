package com.example.demo1.controller;

import com.example.demo1.dto.TaskRequest;
import com.example.demo1.exception.ResourceNotFoundException;
//import com.example.demo1.Task;
import com.example.demo1.TaskService;
import com.example.demo1.TaskController;
import tools.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
//import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean; // New SB4 Import!
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean // Replaced @MockBean with the new Spring Boot 4 variant
    private TaskService taskService;

    @Autowired
    private JsonMapper objectMapper;

    @Test
    void shouldReturn400WhenTitleIsBlank() throws Exception {
        TaskRequest invalidRequest = new TaskRequest();
        invalidRequest.setTitle(""); 
        invalidRequest.setDescription("Testing validation bound");

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
}