package org.dryft.minesweeper.internals;

/**
 * Définition d'une action pouvant soit réussir, soit échouer.
 */
public interface Action {

    /**
     * @return Une valeur quelconque en cas de réussite.
     */
    Object get();

    /**
     * @return Si l'action à échouée ou non.
     */
    boolean hasFailed();

    /**
     * @return Si l'action à réussie ou non.
     */
    boolean hasSucceeded();
}
