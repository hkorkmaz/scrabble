package com.scrabble.controller;

import com.scrabble.model.Board;
import com.scrabble.service.BoardService;
import com.scrabble.service.PlayService;
import com.scrabble.web.exception.ApplicationException;
import com.scrabble.web.mapper.MoveMapper;
import com.scrabble.web.wrapper.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@Controller
@ResponseBody
@RequestMapping(value = "/board", produces = "application/json")
public class ScrabbleController extends BaseController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private PlayService playService;

    @PostMapping("/create")
    public ResponseEntity<Result> createBoard() {
        Board newBoard = boardService.createBoard();
        return Result.Success().add("boardId", newBoard.getId()).build();
    }

    @PostMapping("/{boardId}/play")
    public ResponseEntity<Result> play(@PathVariable Long boardId, @Valid @RequestBody PlayRequest request, BindingResult bindingResult) {
        try {
            validateInput(bindingResult);
            PlayResponse response = playService.play(boardId, MoveMapper.Mapper.toMoves(request.getMoves()));
            return Result.Success().add("result", response).build();
        } catch (ApplicationException exc) {
            return Result.Error(BAD_REQUEST).message(exc.getMessage()).build();
        }
    }

    @GetMapping("/{boardId}/words")
    public ResponseEntity<Result> getWords(@PathVariable Long boardId) {
        try {
            List<WordsResponse> response = boardService.getWords(boardId);
            return Result.Success().add("words", response).build();
        } catch (ApplicationException exc) {
            return Result.Error(BAD_REQUEST).message(exc.getMessage()).build();
        }
    }

    @GetMapping(value = "/{boardId}/print", produces = "text/plain;charset=utf8")
    public String getContent(@PathVariable Long boardId, @RequestParam Integer sequence) {
        try {
            return boardService.printBoard(boardId, sequence);
        } catch (ApplicationException exc) {
            return exc.getMessage();
        }
    }

    @PostMapping("/{boardId}/status")
    public ResponseEntity<Result> setStatus(@PathVariable Long boardId, @Valid @RequestBody StatusChangeRequest request, BindingResult bindingResult) {
        try {
            validateInput(bindingResult);
            boardService.changeStatus(boardId, request.getNewStatus());
            return Result.Success().message("OK").build();
        } catch (ApplicationException exc) {
            return Result.Error(BAD_REQUEST).message(exc.getMessage()).build();
        }
    }
}
