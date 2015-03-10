package org.dryft.minesweeper.commands.impl;

import org.dryft.minesweeper.internals.Action;
import org.dryft.minesweeper.internals.impl.Failure;

/**
 * Abstraction d'une commande lancée à partir d'un terminal. Celle-ci représente une commande inconnue, qui échoue
 * systématiquement.
 */
public class UnknownCommand extends AbstractCommand {

    /** {@inheritDoc} */
    @Override
    public Action execute() {
        return new Failure("Unknown command");
    }
}
