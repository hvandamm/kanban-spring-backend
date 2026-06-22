package com.example.demo1;

import com.example.demo1.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    public Board getBoardById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Board not found with id: " + id));
    }

    public Board createBoard(Board board) {
        return boardRepository.save(board);
    }

    public void deleteBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Board not found with id: " + id));
        boardRepository.delete(board);
    }
}