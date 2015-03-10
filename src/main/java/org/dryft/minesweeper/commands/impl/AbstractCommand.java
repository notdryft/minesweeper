package org.dryft.minesweeper.commands.impl;

import org.dryft.minesweeper.commands.Command;

/**
 * Abstraction d'une commande lancée à partir d'un terminal.
 */
public abstract class AbstractCommand implements Command {

    /** {@inheritDoc} */
    @Override
    public int getMaxParameters() {
        return 0;
    }

    /** {@inheritDoc} */
    @Override
    public boolean setParameters(String[] args) {
        return false;
    }
}
