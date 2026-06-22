package com.example.demo1.controller;

import com.example.demo1.Board;
import com.example.demo1.BoardController;
import com.example.demo1.BoardService;
import com.example.demo1.dto.BoardRequest;
import com.example.demo1.exception.ResourceNotFoundException;
import tools.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BoardController.class)
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BoardService boardService;

    @Autowired
    private JsonMapper objectMapper;

    @Test
    void shouldReturn400WhenNameIsBlank() throws Exception {
        BoardRequest invalidRequest = new BoardRequest();
        invalidRequest.setName("");

        mockMvc.perform(post("/api/boards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.errors.name").value("Board name is required"));
    }

    @Test
    void shouldReturn201WhenBoardIsCreated() throws Exception {
        BoardRequest validRequest = new BoardRequest();
        validRequest.setName("My Board");

        Board createdBoard = new Board(1L, "My Board", null);

        Mockito.when(boardService.createBoard(Mockito.any(Board.class))).thenReturn(createdBoard);

        mockMvc.perform(post("/api/boards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("My Board"));
    }

    @Test
    void shouldReturn404WhenBoardNotFound() throws Exception {
        Mockito.when(boardService.getBoardById(999L))
                .thenThrow(new ResourceNotFoundException("Board not found with id: 999"));

        mockMvc.perform(get("/api/boards/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Board not found with id: 999"));
    }
}