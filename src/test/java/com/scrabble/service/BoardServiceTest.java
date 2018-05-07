package com.scrabble.service;

import com.scrabble.model.Board;
import com.scrabble.model.BoardStatus;
import com.scrabble.model.Move;
import com.scrabble.repo.BoardRepo;
import com.scrabble.repo.MoveRepo;
import com.scrabble.web.exception.BoardNotFoundException;
import com.scrabble.web.exception.BoardStatusChangeException;
import com.scrabble.web.wrapper.WordsResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class BoardServiceTest {

    @Mock
    private BoardRepo boardRepo;

    @Mock
    private MoveRepo moveRepo;

    @InjectMocks
    private BoardService boardService;

    @Test
    public void test_createBoard() {
        Board board = new Board();
        board.setId(1L);
        Mockito.doReturn(board).when(boardRepo).save(Mockito.any());

        Board created = boardService.createBoard();
        assertTrue(created.getId() == 1L);
    }

    @Test
    public void test_getWords() {
        Long boardId = 1L;
        Board board = new Board();
        board.setStatus(BoardStatus.ACTIVE);
        board.setId(boardId);

        Move m1 = new Move();
        m1.setWord("word1");
        m1.setPoint(1);

        Move m2 = new Move();
        m2.setWord("word2");
        m2.setPoint(2);

        Move m3 = new Move();
        m3.setWord("word3");
        m3.setPoint(3);

        Mockito.doReturn(Optional.of(board)).when(boardRepo).getActiveBoard(boardId);
        Mockito.doReturn(Arrays.asList(m1, m2, m3)).when(moveRepo).findByBoardId(boardId);

        List<WordsResponse> result = boardService.getWords(boardId);

        assertThat(result.size(), is(3));
        assertThat(result.get(0).getWord(), is(m1.getWord()));
        assertThat(result.get(1).getWord(), is(m2.getWord()));
        assertThat(result.get(2).getWord(), is(m3.getWord()));
        assertThat(result.get(0).getPoint(), is(m1.getPoint()));
        assertThat(result.get(1).getPoint(), is(m2.getPoint()));
        assertThat(result.get(2).getPoint(), is(m3.getPoint()));
    }

    @Test
    public void test_changeStatus() {
        Long boardId = 1L;
        Board board = new Board();
        board.setId(boardId);
        board.setStatus(BoardStatus.ACTIVE);

        Mockito.doReturn(Optional.of(board)).when(boardRepo).findById(boardId);

        boardService.changeStatus(boardId, BoardStatus.PASSIVE);

        Mockito.verify(boardRepo, Mockito.times(1)).save(board);
    }

    @Test(expected = BoardStatusChangeException.class)
    public void changeStatus_not_activate_if_status_is_passive() {
        Long boardId = 1L;
        Board board = new Board();
        board.setId(boardId);
        board.setStatus(BoardStatus.PASSIVE);

        Mockito.doReturn(Optional.of(board)).when(boardRepo).findById(boardId);

        boardService.changeStatus(boardId, BoardStatus.ACTIVE);
    }

    @Test(expected = BoardStatusChangeException.class)
    public void changeStatus_throw_ex_if_status_is_initial() {
        Long boardId = 1L;
        boardService.changeStatus(boardId, BoardStatus.INITIAL);
    }

    @Test(expected = BoardNotFoundException.class)
    public void changeStatus_throw_ex_if_board_not_found() {
        Long boardId = 1L;
        Mockito.doReturn(Optional.empty()).when(boardRepo).findById(boardId);
        boardService.changeStatus(boardId, BoardStatus.PASSIVE);
    }

    @Test(expected = BoardNotFoundException.class)
    public void printBoard_throw_ex_if_board_not_found() {
        Long boardId = 1L;
        Mockito.doReturn(Optional.empty()).when(boardRepo).findById(boardId);
        boardService.printBoard(boardId, 0);
    }
}
