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
public class CellCommandTest {

    @Mock
    private Receiver receiver;

    @InjectMocks
    private CellCommand command;

    //////////////////////
    // getMaxParameters //
    //////////////////////

    @Test
    public void aCellCommandShouldAcceptTwoParametersOnly() {
        assertThat(command.getMaxParameters()).isEqualTo(2);
    }

    ///////////////////
    // setParameters //
    ///////////////////

    @Test
    public void aCellCommandShouldNotAcceptInvalidParameters() {
        assertThat(command.setParameters(null)).isFalse();
        assertThat(command.setParameters(new String[]{})).isFalse();
        assertThat(command.setParameters(new String[]{WIDTH_STRING})).isFalse();
        assertThat(command.setParameters(new String[]{INVALID, HEIGHT_STRING})).isFalse();
        assertThat(command.setParameters(new String[]{WIDTH_STRING, INVALID})).isFalse();
    }

    @Test
    public void aCellCommandShouldAcceptValidParameters() {
        assertThat(command.setParameters(new String[]{WIDTH_STRING, HEIGHT_STRING})).isTrue();
    }

    /////////////
    // execute //
    /////////////

    @Test
    public void aCellCommandShouldReturnTheResultOfItsReceiverTryCellAt() {
        Action action = new Success();
        when(receiver.tryCellAt(0, 0)).thenReturn(action);

        command.setParameters(new String[]{"0", "0"});
        Action returnedAction = command.execute();

        verify(receiver).tryCellAt(0, 0);
        assertThat(returnedAction).isSameAs(action);
    }
}
