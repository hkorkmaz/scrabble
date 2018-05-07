package com.scrabble.service;

import com.scrabble.model.Board;
import com.scrabble.model.Move;
import com.scrabble.model.WordGrid;
import com.scrabble.repo.BoardRepo;
import com.scrabble.repo.MoveRepo;
import com.scrabble.web.exception.BoardNotFoundException;
import com.scrabble.web.exception.WordNotFoundException;
import com.scrabble.web.mapper.MoveMapper;
import com.scrabble.web.wrapper.MoveRequest;
import com.scrabble.web.wrapper.PlayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PlayService {

    @Autowired
    private BoardRepo boardRepo;

    @Autowired
    private MoveRepo moveRepo;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private PointService pointService;

    public PlayResponse play(Long boardId, List<Move> newMoves) {
        getActiveBoard(boardId);
        checkWords(newMoves);
        List<Move> previousMoves = moveRepo.findByBoardId(boardId);

        PlayResponse response = new PlayResponse();
        WordGrid wordGrid = new WordGrid(previousMoves);

        for (Move move : newMoves) {
            try {
                wordGrid.addMove(move);
                response.addSuccess(move);
            } catch (Exception exc) {
                response.addFailure(move, exc.getMessage());
            }
        }

        if (!response.getSuccess().isEmpty())
            saveSuccessfulMoves(boardId, response.getSuccess());
        return response;
    }

    private void checkWords(List<Move> moves) {
        List<String> invalidWords = moves.stream()
                .map(Move::getWord)
                .filter(word -> !dictionaryService.isValid(word))
                .collect(Collectors.toList());

        if (!invalidWords.isEmpty()) {
            throw new WordNotFoundException(invalidWords);
        }
    }

    private void saveSuccessfulMoves(Long boardId, List<MoveRequest> moves) {
        Board board = getActiveBoard(boardId);

        List<Move> movesToSave = moves.stream()
                .map(MoveMapper.Mapper::toMove)
                .peek(m -> {
                    m.setBoard(board);
                    m.setSequence(board.getSequence());
                    m.setPoint(pointService.pointOf(m.getWord()));
                }).collect(Collectors.toList());

        moveRepo.saveAll(movesToSave);
        boardRepo.save(board.incrementSequence());
    }

    private Board getActiveBoard(Long boardId){
        return boardRepo.getActiveBoard(boardId).orElseThrow(BoardNotFoundException::new);
    }
}
