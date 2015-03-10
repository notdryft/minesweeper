package org.dryft.minesweeper.commands;

import org.dryft.minesweeper.internals.Action;
import org.dryft.minesweeper.internals.impl.Failure;
import org.dryft.minesweeper.internals.impl.Success;
import org.dryft.minesweeper.models.impl.EmptyCell;
import org.dryft.minesweeper.models.impl.Mine;
import org.dryft.minesweeper.wrappers.GridWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dryft.minesweeper.Requirements.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReceiverTest {

    @Mock
    private GridWrapper gridWrapper;

    @InjectMocks
    private Receiver receiver;

    //////////////
    // Receiver //
    //////////////

    @Test
    public void gridShouldNotBeConsideredInitializedAfterReceiverInstantiation() {
        assertThat(receiver.isGridInitialized()).isFalse();
    }

    @Test
    public void gameShouldBeConsideredRunningAfterReceiverInstantiation() {
        assertThat(receiver.isGameRunning()).isTrue();
    }

    ////////////////////
    // selectGridSize //
    ////////////////////

    @Test
    public void selectGridSizeShouldSucceedIfCalledForTheFirstTimeWithValidParameters() { // And control grid/game state
        assertThat(receiver.isGridInitialized()).isFalse();
        assertThat(receiver.isGameRunning()).isTrue();

        Action action = receiver.selectGridSize(WIDTH, HEIGHT, MINES_COUNT);

        assertThat(receiver.isGridInitialized()).isTrue();
        assertThat(receiver.isGameRunning()).isTrue();
        assertThat(action).isInstanceOf(Success.class);
    }

    @Test
    public void selectGridSizeShouldFailIfGridHasAlreadyBeenInitialized() {
        receiver.selectGridSize(WIDTH, HEIGHT, MINES_COUNT);
        Action action = receiver.selectGridSize(WIDTH, HEIGHT, MINES_COUNT);

        assertThat(action).isInstanceOf(Failure.class);
    }

    @Test
    public void selectGridSizeShouldFailForAnyGivenWrongParameter() {
        Action action = receiver.selectGridSize(-1, HEIGHT, MINES_COUNT);
        assertThat(receiver.isGridInitialized()).isFalse();
        assertThat(receiver.isGameRunning()).isTrue();
        assertThat(action).isInstanceOf(Failure.class);

        action = receiver.selectGridSize(WIDTH, -1, MINES_COUNT);
        assertThat(receiver.isGridInitialized()).isFalse();
        assertThat(receiver.isGameRunning()).isTrue();
        assertThat(action).isInstanceOf(Failure.class);

        action = receiver.selectGridSize(WIDTH, HEIGHT, -1);
        assertThat(receiver.isGridInitialized()).isFalse();
        assertThat(receiver.isGameRunning()).isTrue();
        assertThat(action).isInstanceOf(Failure.class);
    }

    ///////////////
    // tryCellAt //
    ///////////////

    @Test
    public void tryCellAtShouldFailIfGridIsNotYetInitialized() {
        Action action = receiver.tryCellAt(0, 0);
        assertThat(action).isInstanceOf(Failure.class);
    }

    @Test
    public void tryCellAtShouldPropagateFailureWhenInnerUncoverRefuseParameters() {
        when(gridWrapper.uncover(-1, -1)).thenThrow(new IllegalArgumentException());

        receiver.selectGridSize(WIDTH, HEIGHT, MINES_COUNT);
        Action action = receiver.tryCellAt(-1, -1);

        verify(gridWrapper).uncover(-1, -1);
        assertThat(action).isInstanceOf(Failure.class);
    }

    @Test
    public void tryCellAtShouldFailIfUncoverTriggeredAMine() { // And game should no longer be considered as running
        when(gridWrapper.uncover(0, 0)).thenReturn(new Mine(0, 0));

        receiver.selectGridSize(WIDTH, HEIGHT, MINES_COUNT);
        Action action = receiver.tryCellAt(0, 0);

        verify(gridWrapper).uncover(0, 0);
        assertThat(receiver.isGameRunning()).isFalse();
        assertThat(action).isInstanceOf(Failure.class);
    }

    @Test
    public void tryCellAtShouldSucceedOtherwise() {
        when(gridWrapper.uncover(0, 0)).thenReturn(new EmptyCell(0, 0));

        receiver.selectGridSize(WIDTH, HEIGHT, MINES_COUNT);
        Action action = receiver.tryCellAt(0, 0);

        verify(gridWrapper).uncover(0, 0);
        assertThat(receiver.isGameRunning()).isTrue();
        assertThat(action).isInstanceOf(Success.class);
    }

    @Test
    public void tryCellAtShouldFlagTheGameAsEndedWhenDiscoveredTheLastCell() {
        when(gridWrapper.uncover(0, 0)).thenReturn(new EmptyCell(0, 0));
        when(gridWrapper.areAllEmptyCellsDiscovered()).thenReturn(true);

        receiver.selectGridSize(WIDTH, HEIGHT, MINES_COUNT);
        Action action = receiver.tryCellAt(0, 0);

        verify(gridWrapper).uncover(0, 0);
        assertThat(receiver.isGameRunning()).isFalse();
        assertThat(action).isInstanceOf(Success.class);
    }

    //////////////
    // stopGame //
    //////////////

    @Test
    public void stopGameShouldSucceedAndFlagTheGameAsStopped() {
        assertThat(receiver.isGridInitialized()).isFalse();
        assertThat(receiver.isGameRunning()).isTrue();

        Action action = receiver.selectGridSize(WIDTH, HEIGHT, MINES_COUNT);

        assertThat(receiver.isGridInitialized()).isTrue();
        assertThat(receiver.isGameRunning()).isTrue();
        assertThat(action).isInstanceOf(Success.class);

        action = receiver.stopGame();

        assertThat(receiver.isGameRunning()).isFalse();
        assertThat(action).isInstanceOf(Success.class);
    }
}
