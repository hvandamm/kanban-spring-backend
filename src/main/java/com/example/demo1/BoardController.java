package com.example.demo1;

import com.example.demo1.dto.BoardRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping
    public List<Board> getAllBoards() {
        return boardService.getAllBoards();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Board> getBoardById(@PathVariable Long id) {
        Board board = boardService.getBoardById(id);
        return ResponseEntity.ok(board);
    }

    @PostMapping
    public ResponseEntity<Board> createBoard(@Valid @RequestBody BoardRequest request) {
        Board board = new Board();
        board.setName(request.getName());
        return new ResponseEntity<>(boardService.createBoard(board), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.noContent().build();
    }
}