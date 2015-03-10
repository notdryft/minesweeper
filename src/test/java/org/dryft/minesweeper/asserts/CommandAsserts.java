package org.dryft.minesweeper.asserts;

import org.dryft.minesweeper.commands.Command;
import org.dryft.minesweeper.commands.Interpreter;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandAsserts {

    public static void assertThatParsedCommandsAreInstancesOf(Interpreter interpreter, String[] patterns, Class<?> expectedCommandClass) {
        for (String pattern : patterns) {
            Command command = interpreter.parse(pattern);

            assertThat(command).isInstanceOf(expectedCommandClass);
        }
    }
}
