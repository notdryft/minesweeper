package org.dryft.minesweeper.internals.impl;

import org.dryft.minesweeper.internals.Action;
import org.dryft.minesweeper.models.impl.EmptyCell;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SuccessTest {


    /////////
    // get //
    /////////

    @Test
    public void getShouldReturnNullWhenSuccessIsCreatedBlank() {
        Action action = new Success();

        assertThat(action.get()).isNull();
    }

    @Test
    public void youShouldNotBeAbleToGetTheValueOfAFailure() {
        Object value = new EmptyCell(0, 0);
        Action action = new Success(value);

        assertThat(action.get()).isSameAs(value);
    }

    //////////////////////////////
    // hasSucceeded & hasFailed //
    //////////////////////////////

    @Test
    public void aSuccessShouldSignalSuccessAndNotFailure() {
        Action action = new Success();

        assertThat(action.hasFailed()).isFalse();
        assertThat(action.hasSucceeded()).isTrue();
    }
}
