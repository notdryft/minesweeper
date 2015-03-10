package org.dryft.minesweeper.commands.impl;

import org.dryft.minesweeper.commands.Receiver;
import org.dryft.minesweeper.internals.Action;

/**
 * Abstraction d'une commande lancée à partir d'un terminal. Celle-ci représente la terminaison du jeu.
 */
public class ExitCommand extends AbstractCommand {

    /** L'instance d'un {@link Receiver} qui va réellement implémenter la commande. */
    private Receiver receiver;

    /**
     * Constructeur.
     *
     * @param receiver {@link GridCommand#receiver}
     */
    public ExitCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    /** {@inheritDoc} */
    @Override
    public Action execute() {
        return receiver.stopGame();
    }
}
