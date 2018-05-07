package com.scrabble.web.wrapper;

import com.scrabble.model.BoardStatus;
import lombok.Data;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class StatusChangeRequest {

    @NotNull
    @Valid
    private BoardStatus newStatus;
}
