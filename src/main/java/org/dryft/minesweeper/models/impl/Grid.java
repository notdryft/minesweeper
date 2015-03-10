package org.dryft.minesweeper.models.impl;

import org.dryft.minesweeper.models.Cell;

import java.util.HashSet;
import java.util.Set;

/**
 * Implémentation d'une grille de démineur.
 */
public class Grid {

    /** La largeur de la grille. */
    private int width;
    /** La hauteur de la grille. */
    private int height;
    /** Le nombre de mines contenus dans la grille. */
    private int minesCount;

    /** La grille. */
    private Cell[][] cells;

    /**
     * Constructeur.
     *
     * @param width      {@link Grid#width}
     * @param height     {@link Grid#height}
     * @param minesCount {@link Grid#minesCount}
     */
    public Grid(int width, int height, int minesCount) {
        if (width < 0) {
            throw new IllegalArgumentException("width should not be negative");
        } else if (height < 0) {
            throw new IllegalArgumentException("height should not be negative");
        } else if (minesCount < 0) {
            throw new IllegalArgumentException("minesCount should not be negative");
        } else if (minesCount > width * height) {
            throw new IllegalArgumentException("minesCount should not exceed width * height");
        }

        this.width = width;
        this.height = height;
        this.minesCount = minesCount;

        cells = new Cell[height][width];
    }

    // Getters

    /** @return {@link Grid#width} */
    public int getWidth() {
        return width;
    }

    /** @return {@link Grid#height} */
    public int getHeight() {
        return height;
    }

    /** @return {@link Grid#minesCount} */
    public int getMinesCount() {
        return minesCount;
    }

    // Tools

    /**
     * Vérification des bornes d'une ligne.
     *
     * @param i La ligne à vérifier.
     * @return Vrai si le numéro de la ligne est bien dans les bornes, faux sinon.
     */
    private boolean rowInBound(int i) {
        return i >= 0 && i < height;
    }

    /**
     * Vérification des bornes d'une colonne.
     *
     * @param j La colonne à vérifier.
     * @return Vrai si le numéro de la colonne est bien dans les bornes, faux sinon.
     */
    private boolean columnInBound(int j) {
        return j >= 0 && j < width;
    }

    /**
     * Vérification des bornes d'un couple (ligne, colonne).
     *
     * @param i La ligne à vérifier.
     * @param j La colonne à vérifier.
     */
    private void checkBounds(int i, int j) {
        if (!rowInBound(i)) {
            throw new IllegalArgumentException("i should be in bounds [0, " + height + "[, got " + i);
        } else if (!columnInBound(j)) {
            throw new IllegalArgumentException("j should be in bounds [0, " + width + "[, got " + j);
        }
    }

    /**
     * Consultation d'une cellule.
     *
     * @param i La ligne de la cellule.
     * @param j La colonne de la cellule.
     * @return La cellule si présente à cet endroit.
     */
    public Cell peekAt(int i, int j) {
        checkBounds(i, j);

        return cells[i][j];
    }

    /**
     * Définition d'une cellule.
     *
     * @param i    La ligne de la cellule.
     * @param j    La colonne de la cellule.
     * @param cell La cellule à positionner.
     */
    public void setAt(int i, int j, Cell cell) {
        checkBounds(i, j);

        cells[i][j] = cell;
    }

    /**
     * Récupération des cellules voisines à une cellule choisie, en fonction de sa position dans la grille.
     *
     * @param i La ligne de la cellule.
     * @param j La colonne de la cellule.
     * @return Les cellules voisines à la position donnée en entrée.
     */
    public Set<Cell> getNeighbours(int i, int j) {
        checkBounds(i, j);

        Set<Cell> neighbours = new HashSet<>();
        for (int neighbourI = i - 1; neighbourI <= i + 1; neighbourI++) {
            for (int neighbourJ = j - 1; neighbourJ <= j + 1; neighbourJ++) {
                if (rowInBound(neighbourI)
                        && columnInBound(neighbourJ)
                        && !(neighbourI == i && neighbourJ == j)) {
                    neighbours.add(cells[neighbourI][neighbourJ]);
                }
            }
        }

        return neighbours;
    }

    /**
     * Récupération des cellules voisines à une cellule choisie, en fonction de la cellule elle-même.
     *
     * @param cell La cellule dont on veut les cellules voisines.
     * @return Les cellules voisines à la position donnée en entrée.
     */
    public Set<Cell> getNeighbours(Cell cell) {
        if (cell == null) {
            throw new IllegalArgumentException("cell should not be null");
        }

        return getNeighbours(cell.getI(), cell.getJ());
    }
}
