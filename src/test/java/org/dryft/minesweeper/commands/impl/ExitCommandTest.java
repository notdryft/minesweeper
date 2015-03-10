package org.dryft.minesweeper.commands.impl;

import org.dryft.minesweeper.commands.Command;
import org.dryft.minesweeper.commands.Receiver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ExitCommandTest {

    @Mock
    private Receiver receiver;

    /////////////
    // execute //
    /////////////

    @Test
    public void anExitCommandShouldStopTheGameWhenExecuted() {
        Command command = new ExitCommand(receiver);
        command.execute();

        verify(receiver).stopGame();
    }
}
