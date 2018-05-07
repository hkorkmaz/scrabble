package com.scrabble.controller;

import com.scrabble.IntegrationTest;
import com.scrabble.model.Board;
import com.scrabble.model.BoardStatus;
import com.scrabble.model.Direction;
import com.scrabble.model.Move;
import com.scrabble.repo.BoardRepo;
import com.scrabble.repo.MoveRepo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.scrabble.TestHelper.*;
import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ScrabbleControllerTest extends IntegrationTest {

    @Autowired
    private BoardRepo boardRepo;

    @Autowired
    private MoveRepo moveRepo;

    @Test
    public void test_createBoard() throws Exception {

        this.mockMvc.perform(post("/board/create")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonResult)
                .andExpect(jsonPath("$.boardId").exists());
    }

    @Test
    public void test_changeStatus_make_active() throws Exception {
        Board board = new Board();
        board.setStatus(BoardStatus.INITIAL);
        Board saved = boardRepo.save(board);

        String url = String.format("/board/%s/status", saved.getId());
        String file = "data/req_change_status.json";

        this.mockMvc.perform(postJson(url, file)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    public void test_play_if_board_is_active() throws Exception {
        Board board = new Board();
        board.setStatus(BoardStatus.ACTIVE);
        Board saved = boardRepo.save(board);

        String url = String.format("/board/%s/play", saved.getId());
        String file = "data/req_play.json";

        this.mockMvc.perform(postJson(url, file)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonResult)
                .andExpect(jsonPath("$.result.success").isNotEmpty())
                .andExpect(jsonPath("$.result.success[0].word").value("ADAM"))
                .andExpect(jsonPath("$.result.failure").isNotEmpty())
                .andExpect(jsonPath("$.result.failure[0].move.word").value("ARABA"))
                .andExpect(jsonPath("$.result.failure[0].reason").exists())
                .andExpect(jsonPath("$.result.failure[1].move.word").value("ASA"))
                .andExpect(jsonPath("$.result.failure[0].reason").exists());
    }

    @Test
    public void test_play_if_board_is_passive() throws Exception {
        Board board = new Board();
        board.setStatus(BoardStatus.PASSIVE);
        Board saved = boardRepo.save(board);

        String url = String.format("/board/%s/play", saved.getId());
        String file = "data/req_play.json";

        this.mockMvc.perform(postJson(url, file)).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonResult)
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    public void test_play_if_input_is_invalid() throws Exception {
        Board board = new Board();
        board.setStatus(BoardStatus.ACTIVE);
        Board saved = boardRepo.save(board);

        String url = String.format("/board/%s/play", saved.getId());
        String file = "data/req_play_invalid_input.json";

        this.mockMvc.perform(postJson(url, file)).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_word_stats() throws Exception {
        Board board = new Board();
        board.setStatus(BoardStatus.ACTIVE);
        Board saved = boardRepo.save(board);

        Move move = new Move();
        move.setWord("ADAM");
        move.setBoard(saved);
        move.setDirection(Direction.VERTICAL);
        move.setStartX(0);
        move.setStartY(0);
        move.setPoint(999);
        move.setSequence(0);

        moveRepo.save(move);

        String url = String.format("/board/%s/words", saved.getId());

        this.mockMvc.perform(get(url)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonResult)
                .andExpect(jsonPath("$.words").isNotEmpty())
                .andExpect(jsonPath("$.words[0].word").value("ADAM"))
                .andExpect(jsonPath("$.words[0].point").value(999));
    }

    @Test
    public void test_word_stats_if_board_not_found() throws Exception {
        String url = String.format("/board/%s/words", 999);

        this.mockMvc.perform(get(url)).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonResult)
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    public void test_print() throws Exception {
        Board board = new Board();
        board.setStatus(BoardStatus.ACTIVE);
        Board saved = boardRepo.save(board);

        Move move1 = new Move();
        move1.setWord("ADAM");
        move1.setBoard(saved);
        move1.setDirection(Direction.VERTICAL);
        move1.setStartX(0);
        move1.setStartY(0);
        move1.setSequence(0);
        move1.setPoint(99);

        Move move2 = new Move();
        move2.setWord("ARABA");
        move2.setBoard(saved);
        move2.setDirection(Direction.HORIZONTAL);
        move2.setStartX(0);
        move2.setStartY(4);
        move2.setSequence(1);
        move2.setPoint(99);

        moveRepo.save(move1);
        moveRepo.save(move2);

        String url1 = String.format("/board/%s/print?sequence=%s", saved.getId(), 0);
        String url2 = String.format("/board/%s/print?sequence=%s", saved.getId(), 1);

        String response1 = this.mockMvc.perform(get(url1)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(textPlainResult)
                .andReturn().getResponse().getContentAsString();

        String response2 = this.mockMvc.perform(get(url2)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(textPlainResult)
                .andReturn().getResponse().getContentAsString();

        assertThat(response1, is(expected1));
        assertThat(response2, is(expected2));
    }


    String expected1 = "A - - - - - - - - - - - - - - \n" +
            "D - - - - - - - - - - - - - - \n" +
            "A - - - - - - - - - - - - - - \n" +
            "M - - - - - - - - - - - - - - \n" +
            "- - - - - - - - - - - - - - - \n" +
            "- - - - - - - - - - - - - - - \n" +
            "- - - - - - - - - - - - - - - \n" +
            "- - - - - - - - - - - - - - - \n" +
            "- - - - - - - - - - - - - - - \n" +
            "- - - - - - - - - - - - - - - \n" +
            "- - - - - - - - - - - - - - - \n" +
            "- - - - - - - - - - - - - - - \n" +
            "- - - - - - - - - - - - - - - \n" +
            "- - - - - - - - - - - - - - - \n" +
            "- - - - - - - - - - - - - - - \n";

    String expected2 = "A - - - - - - - - - - - - - - \n" +
            "D - - - - - - - - - - - - - - \n" +
            "A - - - - - - - - - - - - - - \n" +
            "M - - - - - - - - - - - - - - \n" +
            "A R A B A - - - - - - - - - - \n" +
            "- - - - - - - - - - - - - - - \n" +
            "- - - - - - - - - - - - - - - \n" +
            "- - - - - - - - - - - - - - - \n" +
            "- - - - - - - - - - - - - - - \n" +
            "- - - - - - - - - - - - - - - \n" +
            "- - - - - - - - - - - - - - - \n" +
            "- - - - - - - - - - - - - - - \n" +
            "- - - - - - - - - - - - - - - \n" +
            "- - - - - - - - - - - - - - - \n" +
            "- - - - - - - - - - - - - - - \n";
}
