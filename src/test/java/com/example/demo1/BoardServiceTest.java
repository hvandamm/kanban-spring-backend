package com.example.demo1;

import com.example.demo1.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {

    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private BoardService boardService;

    @Test
    public void should_CreateBoard_When_GivenValidBoard() {
        Board inputBoard = new Board();
        inputBoard.setName("My Board");

        Board savedBoard = new Board(1L, "My Board", null);

        when(boardRepository.save(any(Board.class))).thenReturn(savedBoard);

        Board result = boardService.createBoard(inputBoard);

        assertNotNull(result.getId());
        assertEquals("My Board", result.getName());
    }

    @Test
    public void should_ReturnBoard_When_FoundById() {
        Board board = new Board(1L, "My Board", null);

        when(boardRepository.findById(1L)).thenReturn(Optional.of(board));

        Board result = boardService.getBoardById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("My Board", result.getName());
    }

    @Test
    public void should_ThrowException_When_BoardNotFound() {
        when(boardRepository.findById(999L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            boardService.getBoardById(999L);
        });

        assertEquals("Board not found with id: 999", exception.getMessage());
    }
}