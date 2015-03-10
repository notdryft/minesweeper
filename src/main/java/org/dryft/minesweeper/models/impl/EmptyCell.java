package org.dryft.minesweeper.models.impl;

import org.dryft.minesweeper.models.Cell;
import org.dryft.minesweeper.constants.StringsAndCharacters;

/**
 * Implémentation d'une cellule vide.
 */
public class EmptyCell extends Cell<Character> {

    /**
     * Constructeur.
     *
     * @param i {@link Cell#i}
     * @param j {@link Cell#j}
     */
    public EmptyCell(int i, int j) {
        super(i, j);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isEmpty() {
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public Character draw() {
        int neighboursMines = getNeighbourMines();

        return getNeighbourMines() > 0 ?
                Character.forDigit(neighboursMines, 10) : StringsAndCharacters.EMPTY_CELL;
    }
}
