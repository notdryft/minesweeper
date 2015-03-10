package org.dryft.minesweeper.wrappers.impl;

import org.dryft.minesweeper.models.Cell;
import org.dryft.minesweeper.models.impl.EmptyCell;
import org.dryft.minesweeper.models.impl.Grid;
import org.dryft.minesweeper.models.impl.Mine;
import org.dryft.minesweeper.wrappers.GridWrapper;
import org.dryft.minesweeper.wrappers.impl.GridWrapperImpl;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dryft.minesweeper.Requirements.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GridWrapperTest {

    private Grid grid;
    private GridWrapperImpl wrapper;

    @Before
    public void setUp() {
        grid = new Grid(WIDTH, HEIGHT, MINES_COUNT);
        wrapper = new GridWrapperImpl();
        wrapper.setGrid(grid);
    }

    ////////////////////
    // initializeGrid //
    ////////////////////

    @Test
    public void initializedGridShouldContainTheExpectedMinesCount() {
        wrapper.initializeGrid(0, 0);

        int minesCount = 0;
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (grid.peekAt(i, j).hasMine()) {
                    minesCount += 1;
                }
            }
        }

        assertThat(minesCount).isEqualTo(MINES_COUNT);
    }

    /////////////
    // uncover //
    /////////////

    @Test
    public void firstUncoveredCellShouldBeEmpty() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Grid grid = new Grid(WIDTH, HEIGHT, MINES_COUNT);
                GridWrapperImpl wrapper = new GridWrapperImpl();
                wrapper.setGrid(grid);

                Cell cell = wrapper.uncover(i, j);

                assertThat(cell.hasMine()).isFalse();
            }
        }
    }

    private void assertThatMinesAreUndiscovered(Grid grid, boolean undiscovered) {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Cell cell = grid.peekAt(i, j);

                if (cell.hasMine()) {
                    assertThat(cell.isUndiscovered()).isEqualTo(undiscovered);
                }
            }
        }
    }

    private Cell firstMineThatComesAround(Grid grid) {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Cell cell = grid.peekAt(i, j);

                if (cell.hasMine()) {
                    return cell;
                }
            }
        }

        return null;
    }

    @Test
    public void trippingOnAMineShouldDiscoverThemAll() {
        wrapper.uncover(0, 0);

        assertThatMinesAreUndiscovered(grid, true);

        Cell cell = firstMineThatComesAround(grid);
        wrapper.uncover(cell.getI(), cell.getJ());

        assertThatMinesAreUndiscovered(grid, false);
    }

    ////////////////////////////////
    // areAllEmptyCellsDiscovered //
    ////////////////////////////////

    @Test
    public void areAllEmptyCellsDiscoveredShouldReturnFalseOnANonInitializedGrid() {
        assertThat(wrapper.areAllEmptyCellsDiscovered()).isFalse();
    }


    @Test
    public void areAllEmptyCellsDiscoveredShouldReturnFalseOnAFreshGrid() {
        wrapper.initializeGrid(0, 0);

        assertThat(wrapper.areAllEmptyCellsDiscovered()).isFalse();
    }

    @Test
    public void areAllEmptyCellsDiscoveredShouldReturnTrueOnAnyFullyResolvedGrid() {
        Grid veryDifficultGrid = mock(Grid.class);
        when(veryDifficultGrid.getWidth()).thenReturn(WIDTH);
        when(veryDifficultGrid.getHeight()).thenReturn(HEIGHT);
        when(veryDifficultGrid.getMinesCount()).thenReturn(1);

        when(veryDifficultGrid.peekAt(anyInt(), anyInt())).thenAnswer(answer -> {
            Object[] arguments = answer.getArguments();

            int i = (Integer) arguments[0];
            int j = (Integer) arguments[1];

            if (i == 5 && j == 8) {
                return new Mine(i, j);
            }

            Cell emptyCell = new EmptyCell(i, j);
            emptyCell.discover();

            return emptyCell;
        });

        GridWrapperImpl veryDifficultWrapper = new GridWrapperImpl();
        veryDifficultWrapper.setGrid(veryDifficultGrid);

        veryDifficultWrapper.uncover(0, 0);

        assertThat(veryDifficultWrapper.areAllEmptyCellsDiscovered()).isTrue();
    }
}
