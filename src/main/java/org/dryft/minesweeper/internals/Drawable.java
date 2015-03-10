package org.dryft.minesweeper.internals;

/**
 * Définition d'un marqueur d'objet dessinable.
 *
 * @param <T> Le type de l'objet une fois dessiné.
 */
public interface Drawable<T> {

    T draw();
}
