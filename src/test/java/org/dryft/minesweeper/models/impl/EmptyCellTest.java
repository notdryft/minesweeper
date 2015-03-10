package org.dryft.minesweeper.models.impl;

import org.dryft.minesweeper.constants.StringsAndCharacters;
import org.dryft.minesweeper.models.Cell;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EmptyCellTest {

    ///////////////////////
    // isEmpty & hasMine //
    ///////////////////////

    @Test
    public void anEmptyCellShouldBeEmptyAndNotHaveAMine() {
        Cell cell = new EmptyCell(0, 0);

        assertThat(cell.isEmpty()).isTrue();
        assertThat(cell.hasMine()).isFalse();
    }

    //////////
    // draw //
    //////////

    @Test
    public void zeroNeighboursCellsShouldDrawEmpty() {
        Cell cell = new EmptyCell(0, 0);

        assertThat(cell.draw()).isEqualTo(StringsAndCharacters.EMPTY_CELL);
    }

    @Test
    public void nonZeroNeighboursCellsShouldDrawAsANumber() {
        Cell cell = new EmptyCell(0, 0);
        cell.setNeighbourMines(8);

        assertThat(cell.draw()).isEqualTo('8');
    }
}
