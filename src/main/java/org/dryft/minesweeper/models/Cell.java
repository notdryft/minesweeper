package org.dryft.minesweeper.models;

import org.dryft.minesweeper.internals.Drawable;

/**
 * Abstraction d'une cellule de démineur.
 *
 * @param <T> Le type représentant la cellule dessinée.
 */
public abstract class Cell<T> implements Drawable<T> {

    /** Ligne de la cellule dans la grille. */
    private int i;
    /** Colonne de la cellule dans la grille. */
    private int j;

    /** Si cette cellule a été découverte ou non (booléen inversé pour faciliter la compréhension des opérations). */
    private boolean undiscovered;
    /** Le nombre de mines voisines à cette cellule. */
    private int neighbourMines;

    /**
     * Constructeur.
     *
     * @param i {@link Cell#i}
     * @param j {@link Cell#j}
     */
    public Cell(int i, int j) {
        this.i = i;
        this.j = j;

        this.undiscovered = true;
    }

    // Getters

    /** @return {@link Cell#i} */
    public int getI() {
        return i;
    }

    /** @return {@link Cell#j} */
    public int getJ() {
        return j;
    }

    /** @return Si cette cellule est vide ou non. */
    public boolean isEmpty() {
        return false;
    }

    /** @return Si cette cellule est est une mine ou non. */
    public boolean hasMine() {
        return false;
    }

    // Getters/setters

    /** @return {@link Cell#undiscovered} */
    public boolean isUndiscovered() {
        return undiscovered;
    }

    /** Sélection de la cellule et découverte de son contenu. */
    public void discover() {
        this.undiscovered = false;
    }

    /** @return {@link Cell#neighbourMines} */
    public int getNeighbourMines() {
        return neighbourMines;
    }

    /** @param neighbourMines {@link Cell#neighbourMines} */
    public void setNeighbourMines(int neighbourMines) {
        this.neighbourMines = neighbourMines;
    }

    // Equals/hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Cell other = (Cell) o;

        return i == other.i && j == other.j;
    }

    @Override
    public int hashCode() {
        return i ^ Integer.reverseBytes(j);
    }
}
