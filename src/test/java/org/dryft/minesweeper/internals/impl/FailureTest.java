package org.dryft.minesweeper.internals.impl;

import org.dryft.minesweeper.internals.Action;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FailureTest {

    ////////////////
    // getMessage //
    ////////////////

    @Test
    public void youShouldBeAbleToGetAMessageRepresentingTheOriginOfTheFailure() {
        Failure failure = new Failure("Message");

        assertThat(failure.getMessage()).isEqualTo("Message");
    }

    /////////
    // get //
    /////////

    @Test(expected = UnsupportedOperationException.class)
    public void youShouldNotBeAbleToGetTheValueOfAFailure() {
        new Failure("Message").get();
    }

    //////////////////////////////
    // hasSucceeded & hasFailed //
    //////////////////////////////

    @Test
    public void aFailureShouldSignalFailureAndNotSuccess() {
        Action action = new Failure("Message");

        assertThat(action.hasFailed()).isTrue();
        assertThat(action.hasSucceeded()).isFalse();
    }
}
