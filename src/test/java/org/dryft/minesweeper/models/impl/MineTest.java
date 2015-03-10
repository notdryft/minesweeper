package org.dryft.minesweeper.models.impl;

import org.dryft.minesweeper.models.Cell;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MineTest {

    ///////////////////////
    // isEmpty & hasMine //
    ///////////////////////

    @Test
    public void anEmptyCellShouldNotBeEmptyAndHaveAMine() {
        Cell cell = new Mine(0, 0);

        assertThat(cell.isEmpty()).isFalse();
        assertThat(cell.hasMine()).isTrue();
    }
}
