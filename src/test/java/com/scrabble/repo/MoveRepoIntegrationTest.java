package com.scrabble.repo;


import com.scrabble.IntegrationTest;
import com.scrabble.model.Board;
import com.scrabble.model.Direction;
import com.scrabble.model.Move;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class MoveRepoIntegrationTest extends IntegrationTest {

    @Autowired
    private MoveRepo moveRepo;

    @Autowired
    private BoardRepo boardRepo;

    @Test
    public void test_findByBoardIdAndSequence() {
        Board board = new Board();

        Board savedBoard = boardRepo.save(board);
        Move m1 = new Move();
        m1.setBoard(savedBoard);
        m1.setStartY(0);
        m1.setStartX(0);
        m1.setSequence(1);
        m1.setDirection(Direction.VERTICAL);
        m1.setPoint(99);
        moveRepo.save(m1);

        Move m2 = new Move();
        m2.setBoard(savedBoard);
        m2.setSequence(2);
        m2.setStartY(0);
        m2.setStartX(0);
        m2.setDirection(Direction.VERTICAL);
        m2.setPoint(100);
        moveRepo.save(m2);

        Move m3 = new Move();
        m3.setBoard(savedBoard);
        m3.setSequence(3);
        m3.setStartY(0);
        m3.setStartX(0);
        m3.setDirection(Direction.VERTICAL);
        m3.setPoint(101);
        moveRepo.save(m3);

        List<Move> expected = moveRepo.findByBoardIdAndSequence(savedBoard.getId(), 2);

        assertTrue(expected.size() == 2);
        assertTrue(expected.get(0).getSequence() == 1);
        assertTrue(expected.get(1).getSequence() == 2);
    }
}
