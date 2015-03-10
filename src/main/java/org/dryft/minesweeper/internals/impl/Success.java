package org.dryft.minesweeper.internals.impl;

/**
 * Implémentation d'une action réussie.
 */
public class Success extends AbstractAction {

    /** Valeur souhaitée suite au succès de l'action. */
    private Object value;

    /**
     * Constructeur par défaut.
     */
    public Success() {
        this.value = null;
    }

    /**
     * Constructeur.
     *
     * @param value {@link Success#value}
     */
    public Success(Object value) {
        this.value = value;
    }

    /** {@inheritDoc} */
    @Override
    public Object get() {
        return value;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasSucceeded() {
        return true;
    }
}
