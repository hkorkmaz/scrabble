package com.scrabble.service;

import com.scrabble.model.Board;
import com.scrabble.model.BoardStatus;
import com.scrabble.model.Move;
import com.scrabble.model.WordGrid;
import com.scrabble.repo.BoardRepo;
import com.scrabble.repo.MoveRepo;
import com.scrabble.web.exception.BoardNotFoundException;
import com.scrabble.web.exception.BoardStatusChangeException;
import com.scrabble.web.wrapper.WordsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BoardService {

    @Autowired
    private BoardRepo boardRepo;

    @Autowired
    private MoveRepo moveRepo;

    public Board createBoard() {
        Board newBoard = new Board();
        newBoard.setStatus(BoardStatus.INITIAL);
        return boardRepo.save(newBoard);
    }

    public String printBoard(Long boardId, Integer sequence) {
        boardRepo.findById(boardId).orElseThrow(BoardNotFoundException::new);

        List<Move> moves = moveRepo.findByBoardIdAndSequence(boardId, sequence);
        WordGrid wordGrid = new WordGrid();
        wordGrid.addMoves(moves);
        return wordGrid.print();
    }

    public List<WordsResponse> getWords(Long boardId) {
        boardRepo.getActiveBoard(boardId).orElseThrow(BoardNotFoundException::new);

        List<Move> moves = moveRepo.findByBoardId(boardId);
        return moves.stream().map(m -> new WordsResponse(m.getWord(), m.getPoint()))
                .collect(Collectors.toList());
    }

    public void changeStatus(Long boardId, BoardStatus newStatus) {
        if (newStatus == BoardStatus.INITIAL) {
            throw new BoardStatusChangeException("New status can only be 'ACTIVE' or 'PASSIVE'");
        }

        Board board = boardRepo.findById(boardId).orElseThrow(BoardNotFoundException::new);
        BoardStatus currentStatus = board.getStatus();

        Boolean updatable = (currentStatus == BoardStatus.INITIAL && newStatus == BoardStatus.ACTIVE) ||
                (currentStatus == BoardStatus.ACTIVE && newStatus == BoardStatus.PASSIVE);

        if (updatable) {
            board.setStatus(newStatus);
            boardRepo.save(board);
        } else {
            throw new BoardStatusChangeException("Passive board can not be activated");
        }
    }
}
