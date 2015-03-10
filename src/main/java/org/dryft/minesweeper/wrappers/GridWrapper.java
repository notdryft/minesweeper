package org.dryft.minesweeper.wrappers;

import org.dryft.minesweeper.internals.Drawable;
import org.dryft.minesweeper.models.Cell;
import org.dryft.minesweeper.models.impl.Grid;

/**
 * Définition d'un décorateur de grille avec les opérations de jeu principales.
 */
public interface GridWrapper extends Drawable<String> {

    /**
     * Positionnage de la grille, à priori non initialisée avec des cellules.
     *
     * @param grid La grille à positionner.
     */
    public void setGrid(Grid grid);

    /**
     * Découverte d'une cellule de la grille courante.
     * Pour faire en sorte que la première cellule choisie soit toujours vide, et ainsi rendre le jeu plus intéressant,
     * c'est cette méthode qui se charge d'initialiser la grille lors de son premier appel.
     *
     * @param i La ligne de la cellule à découvrir.
     * @param j La colonne de la cellule à découvrir.
     * @return La cellule découverte.
     */
    public Cell uncover(int i, int j);

    /**
     * @return Vrai si tous les cellules vides de la grille courante ont été découvertes.
     */
    public boolean areAllEmptyCellsDiscovered();
}
