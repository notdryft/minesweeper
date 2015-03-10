package org.dryft.minesweeper.commands.impl;


import org.dryft.minesweeper.commands.Command;
import org.dryft.minesweeper.internals.Action;
import org.dryft.minesweeper.internals.impl.Failure;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UnknownCommandTest {

    /////////////
    // execute //
    /////////////

    @Test
    public void anUnknownCommandShouldAlwaysFail() {
        Command command = new UnknownCommand();
        Action action = command.execute();

        assertThat(action).isInstanceOf(Failure.class);
    }
}
