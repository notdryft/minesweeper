package org.dryft.minesweeper.commands;

import org.dryft.minesweeper.internals.Action;

/**
 * Définition d'une commande lancée à partir d'un terminal.
 */
public interface Command {

    /**
     * @return Le nombre de paramètres de cette commande.
     */
    int getMaxParameters();

    /**
     * Positionnage des paramètres de cette commande.
     *
     * @param args Les arguments à positionner.
     * @return Vrai si l'opération s'est bien déroulée, faux sinon.
     */
    boolean setParameters(String[] args);

    /**
     * Exécution réelle de la commande.
     *
     * @return Une {@link Action} représentant le succès ou l'échec de l'exécution de la commande.
     */
    Action execute();
}
