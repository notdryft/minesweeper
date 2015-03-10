package org.dryft.minesweeper.game;

import org.dryft.minesweeper.commands.Command;
import org.dryft.minesweeper.commands.Interpreter;
import org.dryft.minesweeper.commands.Receiver;
import org.dryft.minesweeper.internals.Action;
import org.dryft.minesweeper.internals.impl.Failure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Implémentation de la logique de jeu.
 */
public class ConsoleGame {

    /** Interpréteur de commandes. */
    private Interpreter interpreter;
    /** Implémentation réelle des commandes disponibles du jeu. */
    private Receiver receiver;

    /**
     * Constructeur.
     *
     * @param interpreter {@link ConsoleGame#interpreter}
     * @param receiver    {@link ConsoleGame#receiver}
     */
    public ConsoleGame(Interpreter interpreter, Receiver receiver) {
        this.interpreter = interpreter;
        this.receiver = receiver;
    }

    /**
     * Lecture d'une ligne de l'entrée standard, puis analyse.
     *
     * @param console Reader bufferisé de l'entrée standard.
     * @return La commande interprétée.
     * @throws IOException En cas d'erreur lors de la lecturde de l'entrée standard.
     */
    private Command readLine(BufferedReader console) throws IOException {
        System.out.print("> ");

        String line = console.readLine();

        return interpreter.parse(line);
    }

    /**
     * Affiche le message d'erreur d'une action échouée sur l'erreur standard.
     *
     * @param action Une action à vérifier.
     */
    private void checkIfLastActionFailed(Action action) {
        if (action.hasFailed()) {
            Failure failure = (Failure) action;

            System.err.println(failure.getMessage());
        }
    }

    /**
     * Boucle de contrôle d'initialisation de la grille.
     *
     * @param console Reader bufferisé de l'entrée standard.
     * @throws IOException En cas d'erreur lors de la lecture de l'entrée standard.
     */
    void loopUntilGridIsInitialized(BufferedReader console) throws IOException {
        while (!receiver.isGridInitialized() && receiver.isGameRunning()) {
            Command command = readLine(console);

            Action action = command.execute();
            checkIfLastActionFailed(action);
        }
    }

    /**
     * Boucle contrôlant la logique du jeu après initialisation de la grille.
     *
     * @param console Reader bufferisé de l'entrée standard.
     * @throws IOException En cas d'erreur lors de la lecture de l'entrée standard.
     */
    void loopWhileGameIsRunning(BufferedReader console) throws IOException {
        while (receiver.isGameRunning()) {
            System.out.println(receiver.getGrid());

            Command command = readLine(console);

            Action action = command.execute();
            checkIfLastActionFailed(action);
        }

        if (receiver.isGridInitialized()) {
            System.out.println(receiver.getGrid());
        }
    }

    /**
     * Implémentation réelle du lancement du jeu.
     *
     * @param console Reader bufferisé de l'entrée standard.
     * @throws IOException En cas d'erreur lors de la lecture de l'entrée standard.
     */
    void start0(BufferedReader console) throws IOException {
        System.out.println(receiver.getIntroduction());

        loopUntilGridIsInitialized(console);
        loopWhileGameIsRunning(console);

        System.out.println(receiver.getExitMessage());
    }

    /**
     * Lancement du jeu.
     */
    public void start() {
        try (BufferedReader console =
                     new BufferedReader(new InputStreamReader(System.in))) {
            start0(console);
        } catch (IOException e) {
            System.err.println("Error while reading console: " + e.getMessage());
        }
    }
}
