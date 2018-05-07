package com.scrabble.web.wrapper;

import lombok.Data;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class PlayRequest {

    @NotNull
    @Valid
    private List<MoveRequest> moves;

}
