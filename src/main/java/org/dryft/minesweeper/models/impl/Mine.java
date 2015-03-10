package org.dryft.minesweeper.models.impl;

import org.dryft.minesweeper.models.Cell;
import org.dryft.minesweeper.constants.StringsAndCharacters;

/**
 * Implémentation d'une cellule représentant une mine.
 */
public class Mine extends Cell<Character> {

    /**
     * Constructeur.
     *
     * @param i {@link Cell#i}
     * @param j {@link Cell#j}
     */
    public Mine(int i, int j) {
        super(i, j);
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasMine() {
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public Character draw() {
        return StringsAndCharacters.MINE;
    }
}
