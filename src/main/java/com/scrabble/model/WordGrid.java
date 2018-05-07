package com.scrabble.model;

import com.scrabble.util.StringUtils;
import com.scrabble.web.exception.InvalidPositionException;
import com.scrabble.web.exception.TileNotAvailableException;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;

public class WordGrid {

    private Boolean isGridEmpty = true;
    private String[][] grid = new String[BOARD_SIZE][BOARD_SIZE];

    private static Integer BOARD_SIZE = 15;
    private static String EMPTY = "-";

    private MoveDirection horizontal = (x, y, step) -> new Tile(x + step, y);
    private MoveDirection vertical = (x, y, step) -> new Tile(x, y + step);

    public WordGrid() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                grid[i][j] = EMPTY;
            }
        }
    }

    public WordGrid(List<Move> moves) {
        this();
        addMoves(moves);
    }

    public void addMove(Move move) {
        String[] letters = StringUtils.letters(move.getWord());

        MoveDirection direction = move.getDirection() == Direction.HORIZONTAL ? horizontal : vertical;

        if (!isGridEmpty && !hasNonEmptyNeighbour(move.getStartX(), move.getStartY())) {
            throw new InvalidPositionException();
        }

        for (int i = 0; i < letters.length; i++) {
            Tile nextTile = direction.move(move.getStartX(), move.getStartY(), i);
            if (!isAvailable(nextTile)) {
                throw new TileNotAvailableException();
            } else {
                grid[nextTile.y][nextTile.x] = letters[i];
            }
        }

        isGridEmpty = false;
    }

    public void addMoves(List<Move> moves) {
        for (Move move : moves) {
            addMove(move);
        }
    }

    public String print() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                sb.append(grid[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private Boolean isAvailable(Tile tile) {
        return tile.x < BOARD_SIZE && tile.x >= 0 &&
                tile.y < BOARD_SIZE && tile.y >= 0 &&
                grid[tile.y][tile.x].equals(EMPTY);
    }

    private Boolean hasNonEmptyNeighbour(Integer x, Integer y) {
        boolean hasNonEmptyNeighbour = false;

        List<Tile> neighbours = Arrays.asList(
                new Tile(x, y - 1),
                new Tile(x, y + 1),
                new Tile(x - 1, y),
                new Tile(x + 1, y)
        );

        for (Tile neighbour : neighbours) {
            try {
                if (!grid[neighbour.y][neighbour.x].equals(EMPTY)) {
                    hasNonEmptyNeighbour = true;
                }
            } catch (Exception ignore) {}
        }
        return hasNonEmptyNeighbour;
    }

    @FunctionalInterface
    interface MoveDirection {
        Tile move(Integer x, Integer y, Integer i);
    }

    @AllArgsConstructor
    class Tile {
        Integer x;
        Integer y;
    }
}
