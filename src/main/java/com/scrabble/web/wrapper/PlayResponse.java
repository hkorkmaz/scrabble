package com.scrabble.web.wrapper;

import com.scrabble.model.Move;
import com.scrabble.web.mapper.MoveMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PlayResponse {

    private List<MoveRequest> success = new ArrayList<>();
    private List<FailedMove> failure = new ArrayList<>();

    public void addSuccess(Move move){
        this.success.add(MoveMapper.Mapper.fromMove(move));
    }

    public void addFailure(Move move, String reason){
        this.failure.add(new FailedMove(reason, MoveMapper.Mapper.fromMove(move)));
    }

    @Data
    @AllArgsConstructor
    class FailedMove{
        String reason;
        MoveRequest move;
    }
}
