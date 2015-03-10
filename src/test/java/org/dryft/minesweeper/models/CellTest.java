package org.dryft.minesweeper.models;

import org.dryft.minesweeper.models.impl.EmptyCell;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class CellTest {

    private static final int LARGE_WIDTH = 1600;
    private static final int LARGE_HEIGHT = 1000;

    //////////////
    // hashCode //
    //////////////

    @Test
    public void redefinedHashCodeShouldBeDifferentForLargeSizedGrid() {
        Map<Integer, Cell> hashCodes = new HashMap<>();

        for (int i = 0; i < LARGE_HEIGHT; i++) {
            for (int j = 0; j < LARGE_WIDTH; j++) {
                Cell cell = new EmptyCell(i, j);

                if (hashCodes.containsKey(cell.hashCode())) {
                    Cell previousCell = hashCodes.get(cell.hashCode());

                    fail(String.format(
                            "Computed hash code %d is the same for couples (%d, %d) and (%d, %d)",
                            cell.hashCode(), i, j, previousCell.getI(), previousCell.getJ()));
                } else {
                    hashCodes.put(cell.hashCode(), cell);
                }
            }
        }

        assertThat(hashCodes.size()).isEqualTo(LARGE_WIDTH * LARGE_HEIGHT);
    }
}
