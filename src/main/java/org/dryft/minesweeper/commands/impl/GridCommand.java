package org.dryft.minesweeper.commands.impl;

import org.dryft.minesweeper.commands.Command;
import org.dryft.minesweeper.commands.Receiver;
import org.dryft.minesweeper.internals.Action;

/**
 * Abstraction d'une commande lancée à partir d'un terminal. Celle-ci représente une commande d'initialisation de
 * grille.
 */
public class GridCommand implements Command {

    /** L'instance d'un {@link Receiver} qui va réellement implémenter la commande. */
    private Receiver receiver;

    /** La longueur souhaitée de la grille. */
    private int width;
    /** La hauteur souhaitée de la grille. */
    private int height;
    /** Le nombre de mines souhaitées au sein de la grille. */
    private int minesCount;

    /**
     * Constructeur.
     *
     * @param receiver {@link GridCommand#receiver}
     */
    public GridCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    /** {@inheritDoc} */
    @Override
    public int getMaxParameters() {
        return 3;
    }

    /** {@inheritDoc} */
    @Override
    public boolean setParameters(String[] args) {
        if (args == null || args.length != 3) {
            return false;
        }

        try {
            width = Integer.parseInt(args[0]);
            height = Integer.parseInt(args[1]);
            minesCount = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    /** {@inheritDoc} */
    @Override
    public Action execute() {
        return receiver.selectGridSize(width, height, minesCount);
    }
}
