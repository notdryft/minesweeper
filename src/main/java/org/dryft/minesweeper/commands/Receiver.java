package org.dryft.minesweeper.commands;

import org.dryft.minesweeper.constants.StringsAndCharacters;
import org.dryft.minesweeper.internals.Action;
import org.dryft.minesweeper.internals.impl.Failure;
import org.dryft.minesweeper.internals.impl.Success;
import org.dryft.minesweeper.models.Cell;
import org.dryft.minesweeper.models.impl.Grid;
import org.dryft.minesweeper.wrappers.GridWrapper;

/**
 * Implémentation réelle des commandes disponibles du jeu.
 */
public class Receiver {

    /** Décorateur de la grille. */
    private GridWrapper gridWrapper;

    /** Si la grille a déjà été initialisée. */
    private boolean gridInitialized;
    /**
     * Si le jeu est en train de tourner ou non. On considère qu'il tourne dès le départ, avant l'initialisation de la
     * grille.
     */
    private boolean gameRunning = true;

    /**
     * Constructeur.
     *
     * @param gridWrapper {@link Receiver#gridWrapper}
     */
    public Receiver(GridWrapper gridWrapper) {
        this.gridWrapper = gridWrapper;
    }

    // Getters

    /** @return {@link Receiver#gridInitialized} */
    public boolean isGridInitialized() {
        return gridInitialized;
    }

    /** @return {@link Receiver#gameRunning} */
    public boolean isGameRunning() {
        return gameRunning;
    }

    // Messages/drawing

    /** @return L'introduction à afficher au démarrage du jeu. */
    public String getIntroduction() {
        StringBuilder sb = new StringBuilder();
        sb.append("Welcome to Minesweeper.").append(StringsAndCharacters.LINE_SEPARATOR);
        sb.append("You can quit at anytime by typing \"exit\" or \"quit\"!").append(StringsAndCharacters.LINE_SEPARATOR);

        return sb.toString();
    }

    /** @return Le message à afficher en fin de jeu. */
    public String getExitMessage() {
        return "Bye!";
    }

    /** @return La grille à afficher dans son état courant. */
    public String getGrid() {
        return gridWrapper.draw();
    }

    // Actions

    /**
     * Choix de la taille de la grille ainsi que du nombre de mines souhaitées.
     *
     * @param width      La largeur de la grille.
     * @param height     La hauteur de la grille.
     * @param minesCount Le nombre de mines.
     * @return Un succès en cas de réussite, une erreur sinon.
     */
    public Action selectGridSize(int width, int height, int minesCount) {
        if (gridInitialized) {
            return new Failure("Grid already initialized!");
        }

        Grid grid;
        try {
            grid = new Grid(width, height, minesCount);
        } catch (IllegalArgumentException e) {
            return new Failure(e.getMessage());
        }

        gridWrapper.setGrid(grid);
        gridInitialized = true;

        return new Success();
    }

    /**
     * Choix d'une cellule à jouer.
     *
     * @param x L'abscisse à jouer.
     * @param y L'ordonnée à jouer.
     * @return Un succès en cas de réussite, une erreur en cas d'échec ou de mine.
     */
    public Action tryCellAt(int x, int y) {
        if (!gridInitialized) {
            return new Failure("Please chose the grid you want to play with first.");
        }

        Cell cell;
        try {
            cell = gridWrapper.uncover(y, x);
        } catch (IllegalArgumentException e) {
            return new Failure(e.getMessage());
        }

        if (cell.hasMine()) {
            gameRunning = false;

            return new Failure("You triggered a mine!");
        }

        if (gridWrapper.areAllEmptyCellsDiscovered()) {
            gameRunning = false;
        }

        return new Success(cell);
    }

    /**
     * Ordre de terminer le jeu, peu importe son état.
     *
     * @return Toujours un succès.
     */
    public Action stopGame() {
        gameRunning = false;

        return new Success();
    }
}
