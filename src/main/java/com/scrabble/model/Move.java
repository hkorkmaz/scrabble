package com.scrabble.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "moves", indexes = {@Index(columnList = "board_id", name = "ix_move_board_id"),
                                  @Index(columnList = "sequence", name = "ix_move_sequence")})
@Data
@NoArgsConstructor
public class Move {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @NotNull
    @Column(name = "start_x")
    private Integer startX;

    @NotNull
    @Column(name = "start_y")
    private Integer startY;

    @Size(min = 2, max = 15)
    private String word;

    @Convert(converter = Direction.Converter.class)
    private Direction direction;

    @NotNull
    private Integer point;

    @NotNull
    private Integer sequence;
}
