package com.scrabble.web.wrapper;

import com.scrabble.model.Direction;
import com.scrabble.model.Move;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MoveRequest {

    @NotNull
    @Range(min = 0, max = 14)
    private Integer startX;

    @NotNull
    @Range(min = 0, max = 14)
    private Integer startY;

    @Size(min = 2, max = 15)
    @Pattern(regexp="^[A-Z^ŞIİÇÖÜĞ]+$", message = "must be upper case")
    private String word;

    private Direction direction;
}
