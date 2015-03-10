package org.dryft.minesweeper.game;

import org.dryft.minesweeper.commands.Command;
import org.dryft.minesweeper.commands.Interpreter;
import org.dryft.minesweeper.commands.Receiver;
import org.dryft.minesweeper.commands.impl.CellCommand;
import org.dryft.minesweeper.commands.impl.GridCommand;
import org.dryft.minesweeper.commands.impl.UnknownCommand;
import org.dryft.minesweeper.internals.impl.Failure;
import org.dryft.minesweeper.internals.impl.Success;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ConsoleGameTest {

    @Mock
    private Interpreter interpreter;

    @Mock
    private Receiver receiver;

    @InjectMocks
    private ConsoleGame game;

    private BufferedReader console;

    @Before
    public void setUp() throws IOException {
        console = mock(BufferedReader.class);
        when(console.readLine()).thenReturn("Line");

        when(receiver.getIntroduction()).thenReturn("Introduction");
        when(receiver.getGrid()).thenReturn("Grid");
        when(receiver.getExitMessage()).thenReturn("ExitMessage");
    }

    // exit patterns ?

    ////////////
    // start0 //
    ////////////

    @Test
    public void gameShouldOutputIntroductionAndExitMessageSurroundingTheGameDevelopment() throws IOException {
        when(receiver.isGridInitialized()).thenReturn(true);
        when(receiver.isGameRunning()).thenReturn(false);

        game.start0(console);

        InOrder inOrder = inOrder(receiver);
        inOrder.verify(receiver).getIntroduction();
        inOrder.verify(receiver).getExitMessage();
    }

    ////////////////////////////////
    // loopUntilGridIsInitialized //
    ////////////////////////////////

    @Test
    public void gameShouldFirstAskTheGridSize() throws IOException {
        Command gridCommand = mock(GridCommand.class);

        when(receiver.isGridInitialized()).thenReturn(false, true);
        when(receiver.isGameRunning()).thenReturn(true);
        when(interpreter.parse(anyString())).thenReturn(gridCommand);
        when(gridCommand.execute()).thenReturn(new Success());

        game.loopUntilGridIsInitialized(console);

        InOrder inOrder = inOrder(console, gridCommand, receiver);
        // Loop 1
        inOrder.verify(receiver).isGridInitialized();
        inOrder.verify(receiver).isGameRunning();
        inOrder.verify(console).readLine();
        inOrder.verify(gridCommand).execute();
        // End of loop
        inOrder.verify(receiver).isGridInitialized();

        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void gameShouldContinueToAskForTheGridSizeUntilComplied() throws IOException {
        Command unknownCommand = mock(UnknownCommand.class);
        Command gridCommand = mock(GridCommand.class);

        when(receiver.isGridInitialized()).thenReturn(false, false, false, true);
        when(receiver.isGameRunning()).thenReturn(true);
        when(interpreter.parse(anyString())).thenReturn(unknownCommand, unknownCommand, gridCommand);
        when(unknownCommand.execute()).thenReturn(new Failure("Failure"));
        when(gridCommand.execute()).thenReturn(new Success());

        game.loopUntilGridIsInitialized(console);

        InOrder inOrder = inOrder(console, gridCommand, receiver, unknownCommand);
        // Loop 1
        inOrder.verify(receiver).isGridInitialized();
        inOrder.verify(receiver).isGameRunning();
        inOrder.verify(console).readLine();
        inOrder.verify(unknownCommand).execute();
        // Loop 2
        inOrder.verify(receiver).isGridInitialized();
        inOrder.verify(receiver).isGameRunning();
        inOrder.verify(console).readLine();
        inOrder.verify(unknownCommand).execute();
        // Loop 3
        inOrder.verify(receiver).isGridInitialized();
        inOrder.verify(receiver).isGameRunning();
        inOrder.verify(console).readLine();
        inOrder.verify(gridCommand).execute();
        // End of loop
        inOrder.verify(receiver).isGridInitialized();

        inOrder.verifyNoMoreInteractions();
    }

    ////////////////////////////
    // loopWhileGameIsRunning //
    ////////////////////////////

    @Test
    public void gameShouldPlayNormallyWhileGameIsRunning() throws IOException {
        Command cellCommand = mock(CellCommand.class);

        when(receiver.isGridInitialized()).thenReturn(true);
        when(receiver.isGameRunning()).thenReturn(true, true, false);
        when(interpreter.parse(anyString())).thenReturn(cellCommand);
        when(cellCommand.execute()).thenReturn(new Success(), new Failure("Failure"));

        game.loopWhileGameIsRunning(console);

        InOrder inOrder = inOrder(console, cellCommand, receiver);
        // Loop 1
        inOrder.verify(receiver).isGameRunning();
        inOrder.verify(receiver).getGrid();
        inOrder.verify(console).readLine();
        inOrder.verify(cellCommand).execute();
        // Loop 2
        inOrder.verify(receiver).isGameRunning();
        inOrder.verify(receiver).getGrid();
        inOrder.verify(console).readLine();
        inOrder.verify(cellCommand).execute();
        // End of loop
        inOrder.verify(receiver).isGameRunning();
        inOrder.verify(receiver).isGridInitialized();
        inOrder.verify(receiver).getGrid();

        inOrder.verifyNoMoreInteractions();
    }
}
