package com.scrabble.repo;

import com.scrabble.model.Board;
import com.scrabble.model.BoardStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardRepo extends PagingAndSortingRepository<Board, Long> {

    Optional<Board> findById(Long boardId);

    @Query("from Board b where b.id=:boardId and b.status=com.scrabble.model.BoardStatus.ACTIVE")
    Optional<Board> getActiveBoard(@Param("boardId") Long boardId);
}
