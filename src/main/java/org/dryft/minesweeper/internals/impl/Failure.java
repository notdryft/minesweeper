package org.dryft.minesweeper.internals.impl;

/**
 * Implémentation d'une action échouée.
 */
public class Failure extends AbstractAction {

    /** Message représentant l'origine de l'échec. */
    private String message;

    /**
     * Constructeur.
     *
     * @param message {@link Failure#message}
     */
    public Failure(String message) {
        this.message = message;
    }

    /** @return {@link Failure#message} */
    public String getMessage() {
        return message;
    }

    /** {@inheritDoc} */
    @Override
    public Object get() {
        throw new UnsupportedOperationException("You can't get() on a Failure");
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasFailed() {
        return true;
    }
}
