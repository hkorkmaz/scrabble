package com.scrabble.repo;

import com.scrabble.model.Move;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MoveRepo extends PagingAndSortingRepository<Move, Long> {

    @Query("from Move m where m.board.id=:boardId and m.sequence <=:sequence")
    List<Move> findByBoardIdAndSequence(@Param("boardId") Long boardId, @Param("sequence") Integer sequence);

    List<Move> findByBoardId(Long boardId);
}
