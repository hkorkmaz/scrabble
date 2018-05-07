package com.scrabble.repo;


import com.scrabble.IntegrationTest;
import com.scrabble.model.Board;
import com.scrabble.model.BoardStatus;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class BoardRepoIntegrationTest extends IntegrationTest {

    @Autowired
    private BoardRepo boardRepo;

    @Test
    public void test_getActiveBoard() {
        Board newBoard = new Board();
        newBoard.setStatus(BoardStatus.ACTIVE);
        Board saved = boardRepo.save(newBoard);

        Board result = boardRepo.getActiveBoard(saved.getId()).get();
        assertNotNull(result);
        assertEquals(result.getStatus(), BoardStatus.ACTIVE);
    }

    @Test
    public void return_none_when_board_not_active() throws Exception {
        Board board = new Board();
        board.setStatus(BoardStatus.PASSIVE);
        Board saved = boardRepo.save(board);

        assertEquals(boardRepo.getActiveBoard(saved.getId()).isPresent(), false);
    }
}
