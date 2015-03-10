package org.dryft.minesweeper;

import org.dryft.minesweeper.commands.Interpreter;
import org.dryft.minesweeper.commands.Receiver;
import org.dryft.minesweeper.game.ConsoleGame;
import org.dryft.minesweeper.wrappers.GridWrapper;
import org.dryft.minesweeper.wrappers.impl.GridWrapperImpl;

/**
 * Implémentation d'un démineur en mode console.
 */
public class Main {

    /**
     * Résolution des dépendances au jeu.
     *
     * @return Le jeu préparé et prêt à être exécuté.
     */
    private static ConsoleGame newConsoleGameWithResolvedDependencies() {
        GridWrapper gridWrapper = new GridWrapperImpl();
        Receiver receiver = new Receiver(gridWrapper);
        Interpreter interpreter = new Interpreter(receiver);

        return new ConsoleGame(interpreter, receiver);
    }

    public static void main(String[] args) {
        ConsoleGame game = newConsoleGameWithResolvedDependencies();

        game.start();
    }
}
