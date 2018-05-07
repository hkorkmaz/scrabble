package com.scrabble.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name="boards")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = BoardStatus.Converter.class)
    private BoardStatus status = BoardStatus.INITIAL;

    @NotNull
    private Integer sequence = 0;

    public Board(Long id){
        this.id = id;
    }

    public Board incrementSequence(){
        this.sequence++;
        return this;
    }
}
