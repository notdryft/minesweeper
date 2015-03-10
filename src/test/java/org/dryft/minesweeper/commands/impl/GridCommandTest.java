package org.dryft.minesweeper.commands.impl;

import org.dryft.minesweeper.commands.Receiver;
import org.dryft.minesweeper.internals.Action;
import org.dryft.minesweeper.internals.impl.Success;
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
public class GridCommandTest {

    @Mock
    private Receiver receiver;

    @InjectMocks
    private GridCommand command;

    //////////////////////
    // getMaxParameters //
    //////////////////////

    @Test
    public void aGridCommandShouldAcceptThreeParametersOnly() {
        assertThat(command.getMaxParameters()).isEqualTo(3);
    }

    ///////////////////
    // setParameters //
    ///////////////////

    @Test
    public void aGridCommandShouldNotAcceptInvalidParameters() {
        assertThat(command.setParameters(null)).isFalse();
        assertThat(command.setParameters(new String[]{})).isFalse();
        assertThat(command.setParameters(new String[]{WIDTH_STRING})).isFalse();
        assertThat(command.setParameters(new String[]{WIDTH_STRING, HEIGHT_STRING})).isFalse();
        assertThat(command.setParameters(new String[]{INVALID, HEIGHT_STRING, HEIGHT_STRING})).isFalse();
        assertThat(command.setParameters(new String[]{WIDTH_STRING, INVALID, HEIGHT_STRING})).isFalse();
        assertThat(command.setParameters(new String[]{WIDTH_STRING, HEIGHT_STRING, INVALID})).isFalse();
    }

    @Test
    public void aGridCommandShouldAcceptValidParameters() {
        assertThat(command.setParameters(new String[]{WIDTH_STRING, HEIGHT_STRING, "20"})).isTrue();
    }

    /////////////
    // execute //
    /////////////

    @Test
    public void aGridCommandShouldReturnTheResultOfItsReceiverSelectGridSize() {
        Action action = new Success();
        when(receiver.selectGridSize(WIDTH, HEIGHT, MINES_COUNT)).thenReturn(action);

        command.setParameters(new String[]{WIDTH_STRING, HEIGHT_STRING, MINES_COUNT_STRING});
        Action returnedAction = command.execute();

        verify(receiver).selectGridSize(WIDTH, HEIGHT, MINES_COUNT);
        assertThat(returnedAction).isSameAs(action);
    }
}
