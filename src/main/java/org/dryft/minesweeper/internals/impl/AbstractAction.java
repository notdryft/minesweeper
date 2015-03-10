package org.dryft.minesweeper.internals.impl;

import org.dryft.minesweeper.internals.Action;

/**
 * Abstraction d'une action pouvant soit réussir, soit échouer.
 */
public abstract class AbstractAction implements Action {

    /** {@inheritDoc} */
    @Override
    public boolean hasSucceeded() {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasFailed() {
        return false;
    }
}
