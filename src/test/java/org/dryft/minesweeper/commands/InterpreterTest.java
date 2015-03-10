package org.dryft.minesweeper.commands;

import org.dryft.minesweeper.commands.impl.CellCommand;
import org.dryft.minesweeper.commands.impl.ExitCommand;
import org.dryft.minesweeper.commands.impl.GridCommand;
import org.dryft.minesweeper.commands.impl.UnknownCommand;
import org.dryft.minesweeper.constants.StringsAndCharacters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.dryft.minesweeper.asserts.CommandAsserts.assertThatParsedCommandsAreInstancesOf;

@RunWith(MockitoJUnitRunner.class)
public class InterpreterTest {

    @Mock
    private Receiver receiver;

    @InjectMocks
    private Interpreter interpreter;

    //////////////////////////
    // parse:UnknownCommand //
    //////////////////////////

    @Test
    public void parseShouldReturnAnUnknownCommandForEmptyLinesAndTrimItsInput() {
        String[] unknownPatterns = {
                null,
                StringsAndCharacters.EMPTY,
                StringsAndCharacters.SPACE
        };

        assertThatParsedCommandsAreInstancesOf(interpreter, unknownPatterns, UnknownCommand.class);
    }

    @Test
    public void parseShouldReturnAnUnknownCommandAnyNegativeOrInvalidInput() {
        String[] unknownPatterns = {
                "a", "-1",
                "-1 10", "10 -1", "-1 -1",
                "a 10", "10 a",
                "-1 10 10", "10 -1 10", "10 10 -1", "-1 -1 -1",
                "a 10 10", "10 a 10", "10 10 a"
        };

        assertThatParsedCommandsAreInstancesOf(interpreter, unknownPatterns, UnknownCommand.class);
    }

    ///////////////////////
    // parse:GridCommand //
    ///////////////////////

    @Test
    public void parseShouldHandleGridCommandsAndTrimItsInput() {
        String[] gridPatterns = {
                "16 10 20",
                "  16 10 20",
                "16 10 20  ",
                "  16 10 20  "
        };

        assertThatParsedCommandsAreInstancesOf(interpreter, gridPatterns, GridCommand.class);
    }

    ///////////////////////
    // parse:CellCommand //
    ///////////////////////

    @Test
    public void parseShouldHandleCellCommandsAndTrimItsInput() {
        String[] gridPatterns = {
                "0 0",
                "  0 0",
                "0 0  ",
                "  0 0 "
        };

        assertThatParsedCommandsAreInstancesOf(interpreter, gridPatterns, CellCommand.class);
    }

    ///////////////////////
    // parse:ExitCommand //
    ///////////////////////

    @Test
    public void parseShouldHandleExitCommandsAndTrimItsInput() {
        String[] exitPatterns = {
                "exit",
                "  exit",
                "exit  ",
                "  exit  ",
                "quit",
                "  quit",
                "quit  ",
                "  quit  "
        };

        assertThatParsedCommandsAreInstancesOf(interpreter, exitPatterns, ExitCommand.class);
    }
}
