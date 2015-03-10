package org.dryft.minesweeper.models.impl;

import org.dryft.minesweeper.models.Cell;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dryft.minesweeper.asserts.GridAsserts.assertThatGridIsEmpty;
import static org.dryft.minesweeper.Requirements.*;

public class GridTest {

    private Grid grid;

    @Before
    public void setUp() {
        grid = new Grid(WIDTH, HEIGHT, MINES_COUNT);
    }

    //////////
    // Grid //
    //////////

    @Test(expected = IllegalArgumentException.class)
    public void gridConstructorShouldNotAcceptNegativeWidth() {
        new Grid(-1, HEIGHT, MINES_COUNT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void gridConstructorShouldNotAcceptNegativeHeight() {
        new Grid(WIDTH, -1, MINES_COUNT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void gridConstructorShouldNotAcceptNegativeMinesCount() {
        new Grid(WIDTH, HEIGHT, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void gridConstructorShouldNotAcceptTooHighMinesCount() {
        new Grid(WIDTH, HEIGHT, WIDTH * HEIGHT + 1);
    }

    @Test
    public void aFreshGridShouldNotBeInitialized() {
        assertThat(grid.getWidth()).isEqualTo(WIDTH);
        assertThat(grid.getHeight()).isEqualTo(HEIGHT);
        assertThat(grid.getMinesCount()).isEqualTo(MINES_COUNT);

        assertThatGridIsEmpty(grid, null);
    }

    ////////////
    // peekAt //
    ////////////

    @Test(expected = IllegalArgumentException.class)
    public void peekAtShouldCheckRowsLowerBounds() {
        grid.peekAt(-1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void peekAtShouldCheckRowsUpperBounds() {
        grid.peekAt(HEIGHT, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void peekAtShouldCheckColumnsLowerBounds() {
        grid.peekAt(0, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void peekAtShouldCheckColumnsUpperBounds() {
        grid.peekAt(0, WIDTH);
    }

    ///////////
    // setAt //
    ///////////

    @Test(expected = IllegalArgumentException.class)
    public void setAtShouldCheckRowsLowerBounds() {
        grid.setAt(-1, 0, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setAtShouldCheckRowsUpperBounds() {
        grid.setAt(HEIGHT, 0, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setAtShouldCheckColumnsLowerBounds() {
        grid.setAt(0, -1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setAtShouldCheckColumnsUpperBounds() {
        grid.setAt(0, WIDTH, null);
    }

    @Test
    public void setAtShouldCorrectlySetACell() {
        // TODO Same for each possible (i, j) ?
        int chosenI = 0;
        int chosenJ = 0;

        assertThat(grid.peekAt(chosenI, chosenJ)).isNull();

        Cell cell = new EmptyCell(chosenI, chosenJ);
        grid.setAt(chosenI, chosenJ, cell);

        assertThat(grid.peekAt(0, 0)).isEqualTo(cell);
        assertThatGridIsEmpty(grid, (i, j) -> i == chosenI && j == chosenJ);
    }

    /////////////////////////
    // getNeighbours(i, j) //
    /////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void getNeighboursShouldCheckRowsLowerBounds() {
        grid.getNeighbours(-1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNeighboursShouldCheckRowsUpperBounds() {
        grid.getNeighbours(HEIGHT, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNeighboursShouldCheckColumnsLowerBounds() {
        grid.getNeighbours(0, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNeighboursShouldCheckColumnsUpperBounds() {
        grid.getNeighbours(0, WIDTH);
    }

    private void initializeGrid() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                grid.setAt(i, j, new EmptyCell(i, j));
            }
        }
    }

    @Test
    public void getNeighboursShouldCorrectlyReturnsNeighboursCellsInCorners() {
        initializeGrid();

        Set<Cell> neighbours;

        // Top left corner
        neighbours = grid.getNeighbours(0, 0);
        assertThat(neighbours).hasSize(3);
        assertThat(neighbours).containsOnly(
                // @formatter:off
                                     new EmptyCell(0, 1),
                new EmptyCell(1, 0), new EmptyCell(1, 1));
                // @formatter:on

        // Top right corner
        neighbours = grid.getNeighbours(0, WIDTH - 1);
        assertThat(neighbours).hasSize(3);
        assertThat(neighbours).containsOnly(
                // @formatter:off
                new EmptyCell(0, WIDTH - 2),
                new EmptyCell(1, WIDTH - 2), new EmptyCell(1, WIDTH - 1));
                // @formatter:on

        // Bottom right corner
        neighbours = grid.getNeighbours(HEIGHT - 1, WIDTH - 1);
        assertThat(neighbours).hasSize(3);
        assertThat(neighbours).containsOnly(
                // @formatter:off
                new EmptyCell(HEIGHT - 2, WIDTH - 2), new EmptyCell(HEIGHT - 2, WIDTH - 1),
                new EmptyCell(HEIGHT - 1, WIDTH - 2));
                // @formatter:on

        // Bottom left corner
        neighbours = grid.getNeighbours(HEIGHT - 1, 0);
        assertThat(neighbours).hasSize(3);
        assertThat(neighbours).containsOnly(
                // @formatter:off
                new EmptyCell(HEIGHT - 2, 0), new EmptyCell(HEIGHT - 2, 1),
                                              new EmptyCell(HEIGHT - 1, 1));
                // @formatter:on
    }

    @Test
    public void getNeighboursShouldCorrectlyReturnsNeighboursCellsInSides() {
        initializeGrid();

        Set<Cell> neighbours;

        // Top side
        neighbours = grid.getNeighbours(0, 1);
        assertThat(neighbours).hasSize(5);
        assertThat(neighbours).containsOnly(
                // @formatter:off
                new EmptyCell(0, 0),                      new EmptyCell(0, 2),
                new EmptyCell(1, 0), new EmptyCell(1, 1), new EmptyCell(1, 2));
                // @formatter:on

        // Right side
        neighbours = grid.getNeighbours(1, WIDTH - 1);
        assertThat(neighbours).hasSize(5);
        assertThat(neighbours).containsOnly(
                // @formatter:off
                new EmptyCell(0, WIDTH - 2), new EmptyCell(0, WIDTH - 1),
                new EmptyCell(1, WIDTH - 2),
                new EmptyCell(2, WIDTH - 2), new EmptyCell(2, WIDTH - 1));
                // @formatter:on

        // Bottom side
        neighbours = grid.getNeighbours(HEIGHT - 1, 1);
        assertThat(neighbours).hasSize(5);
        assertThat(neighbours).containsOnly(
                // @formatter:off
                new EmptyCell(HEIGHT - 2, 0), new EmptyCell(HEIGHT - 2, 1), new EmptyCell(HEIGHT - 2, 2),
                new EmptyCell(HEIGHT - 1, 0),                               new EmptyCell(HEIGHT - 1, 2));
                // @formatter:on

        // Left side
        neighbours = grid.getNeighbours(1, 0);
        assertThat(neighbours).hasSize(5);
        assertThat(neighbours).containsOnly(
                // @formatter:off
                new EmptyCell(0, 0), new EmptyCell(0, 1),
                                     new EmptyCell(1, 1),
                new EmptyCell(2, 0), new EmptyCell(2, 1));
                // @formatter:on
    }

    @Test
    public void getNeighboursShouldCorrectlyReturnsNeighboursCells() {
        initializeGrid();

        Set<Cell> neighbours = grid.getNeighbours(1, 1);
        assertThat(neighbours.size()).isEqualTo(8);
        assertThat(neighbours).containsOnly(
                // @formatter:off
                new EmptyCell(0, 0), new EmptyCell(0, 1), new EmptyCell(0, 2),
                new EmptyCell(1, 0),                      new EmptyCell(1, 2),
                new EmptyCell(2, 0), new EmptyCell(2, 1), new EmptyCell(2, 2));
                // @formatter:on
    }

    /////////////////////////
    // getNeighbours(cell) //
    /////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void getNeighboursShouldNotAcceptNulls() {
        grid.getNeighbours(null);
    }
}
