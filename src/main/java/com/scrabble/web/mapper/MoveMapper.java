package com.scrabble.web.mapper;

import com.scrabble.model.Move;
import com.scrabble.web.wrapper.MoveRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MoveMapper {
    MoveMapper Mapper = Mappers.getMapper(MoveMapper.class);

    Move toMove(MoveRequest moveRequest);

    MoveRequest fromMove(Move move);

    List<Move> toMoves(List<MoveRequest> moveRequestList);

    List<MoveRequest> fromMoves(List<Move> moves);
}
