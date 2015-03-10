package org.dryft.minesweeper.asserts;

import org.dryft.minesweeper.models.impl.Grid;

import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;

public class GridAsserts {

    // TODO Custom assertj matcher ?
    public static void assertThatGridIsEmpty(Grid grid, BiFunction<Integer, Integer, Boolean> discard) {
        for (int i = 0; i < grid.getHeight(); i++) {
            for (int j = 0; j < grid.getWidth(); j++) {
                if (discard != null && !discard.apply(i, j)) {
                    assertThat(grid.peekAt(i, j)).isNull();
                }
            }
        }
    }
}
