package org.dryft.minesweeper.commands.impl;

import org.dryft.minesweeper.commands.Command;
import org.dryft.minesweeper.commands.Receiver;
import org.dryft.minesweeper.internals.Action;

/**
 * Abstraction d'une commande lancée à partir d'un terminal. Celle-ci représente le choix d'une cellule à jouer.
 */
public class CellCommand implements Command {

    /** L'instance d'un {@link Receiver} qui va réellement implémenter la commande. */
    private Receiver receiver;

    /** L'abscisse à jouer. */
    private int x;
    /** L'ordonnée à jouer. */
    private int y;

    /**
     * Constructeur.
     *
     * @param receiver {@link GridCommand#receiver}
     */
    public CellCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    /** {@inheritDoc} */
    @Override
    public int getMaxParameters() {
        return 2;
    }

    /** {@inheritDoc} */
    @Override
    public boolean setParameters(String[] args) {
        if (args == null || args.length != getMaxParameters()) {
            return false;
        }

        try {
            x = Integer.parseInt(args[0]);
            y = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    /** {@inheritDoc} */
    @Override
    public Action execute() {
        return receiver.tryCellAt(x, y);
    }
}
