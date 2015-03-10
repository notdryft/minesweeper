package org.dryft.minesweeper.wrappers.impl;

import org.dryft.minesweeper.constants.StringsAndCharacters;
import org.dryft.minesweeper.models.Cell;
import org.dryft.minesweeper.models.impl.EmptyCell;
import org.dryft.minesweeper.models.impl.Grid;
import org.dryft.minesweeper.models.impl.Mine;
import org.dryft.minesweeper.wrappers.GridWrapper;

import java.util.*;
import java.util.stream.Collectors;

import static org.dryft.minesweeper.constants.Ints.TEN;

/**
 * Implémentation d'un décorateur de grille avec les opérations de jeu principales.
 */
public class GridWrapperImpl implements GridWrapper {

    /** La grille interne. */
    private Grid grid;

    /** {@inheritDoc} */
    @Override
    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    // Tools

    /**
     * Positionnement des cellules de manière aléatoire. L'algorithme défini une probabilité p d'apparition des
     * mines au sein de la grille, et continue de la parcourir jusqu'à épuisement du compte de mines.
     * En entrée le premier couple (ligne, colonne) choisi par l'utilisateur pour faire en sorte que cette cellule
     * ne contienne jamais de mines.
     *
     * @param firstI La première ligne choisi par l'utilisateur.
     * @param firstJ La première colonne choisi par l'utilisateur.
     */
    private void randomizeMines(int firstI, int firstJ) {
        int minesLeft = grid.getMinesCount();
        double p = (double) grid.getMinesCount() / (grid.getWidth() * grid.getHeight());

        Random random = new Random();
        while (minesLeft > 0) {
            for (int i = 0; i < grid.getHeight(); i++) {
                for (int j = 0; j < grid.getWidth(); j++) {
                    if (minesLeft > 0 // There is still some mines left
                            && !(i == firstI && j == firstJ) // This is not the first cell chosen by the player
                            && grid.peekAt(i, j) == null // The cell is not yet defined
                            && random.nextDouble() < p) { // Probability is with us.
                        grid.setAt(i, j, new Mine(i, j));

                        minesLeft -= 1;
                    }
                }
            }
        }
    }

    /**
     * Les cellules restantes après répartition des mines doivent être vides.
     */
    private void considerLeftCellsAsEmpty() {
        for (int i = 0; i < grid.getHeight(); i++) {
            for (int j = 0; j < grid.getWidth(); j++) {
                if (grid.peekAt(i, j) == null) {
                    grid.setAt(i, j, new EmptyCell(i, j));
                }
            }
        }
    }

    /**
     * Une fois la grille remplie, on peut calculer le nombre de mines voisines à chaque cellule de la grille.
     */
    private void computeNeighboursMines() {
        for (int i = 0; i < grid.getHeight(); i++) {
            for (int j = 0; j < grid.getWidth(); j++) {
                Set<Cell> neighbours = grid.getNeighbours(i, j);
                long neighboursMines = neighbours.stream()
                        .filter(Cell::hasMine)
                        .count();

                Cell cell = grid.peekAt(i, j);
                cell.setNeighbourMines((int) neighboursMines);
            }
        }
    }

    /**
     * Initialisation de la grille avec le nombre de mines souhaités par l'utilisateur.
     *
     * @param firstI La première ligne choisi par l'utilisateur.
     * @param firstJ La première colonne choisi par l'utilisateur.
     */
    void initializeGrid(int firstI, int firstJ) {
        randomizeMines(firstI, firstJ);
        considerLeftCellsAsEmpty();

        computeNeighboursMines();
    }

    /**
     * Force la découverte de toutes les mines de la grille.
     */
    private void discoverMines() {
        for (int i = 0; i < grid.getHeight(); i++) {
            for (int j = 0; j < grid.getWidth(); j++) {
                Cell cell = grid.peekAt(i, j);

                if (cell.hasMine()) {
                    cell.discover();
                }
            }
        }
    }

    /**
     * Quand la cellule est vide, il faut propager la découverte aux cellules voisines.
     * C'est une version dépliée d'un algorithme récursif terminal.
     *
     * @param cellsLeftToTreat Les cellules restants à traiter.
     * @return L'ensemble des cellules à découvrir.
     */
    private Set<Cell> propagateDiscoveryToNeighboursOf(Deque<Cell> cellsLeftToTreat) {
        Set<Cell> cellsToDiscover = new HashSet<>();
        while (!cellsLeftToTreat.isEmpty()) {
            Cell origin = cellsLeftToTreat.pop();
            cellsToDiscover.add(origin);

            if (origin.getNeighbourMines() == 0) {
                cellsLeftToTreat.addAll(grid.getNeighbours(origin)
                        .stream()
                        .filter(cell -> !cellsLeftToTreat.contains(cell))
                        .filter(cell -> !cellsToDiscover.contains(cell))
                        .collect(Collectors.toList()));
            }
        }

        return cellsToDiscover;
    }

    /**
     * Propagation de la découverte de la cellule aux cellules voisines.
     *
     * @param origin L'origine de la découverte.
     */
    void propagateDiscoveryToNeighboursOf(Cell origin) {
        Deque<Cell> cellsLeftToTreat = new ArrayDeque<>();
        cellsLeftToTreat.add(origin);

        Set<Cell> cellsToDiscover = propagateDiscoveryToNeighboursOf(cellsLeftToTreat);
        cellsToDiscover.stream()
                .forEach(Cell::discover);
    }

    /** {@inheritDoc} */
    @Override
    public Cell uncover(int i, int j) {
        Cell cell = grid.peekAt(i, j);
        if (cell == null) { // Grid not initialized, hence we assume this is the first move
            initializeGrid(i, j);
        }

        cell = grid.peekAt(i, j);
        cell.discover();

        if (cell.hasMine()) {
            discoverMines();
        } else if (cell.getNeighbourMines() == 0) {
            propagateDiscoveryToNeighboursOf(cell);
        }

        return cell;
    }

    /** {@inheritDoc} */
    @Override
    public boolean areAllEmptyCellsDiscovered() {
        int actualDiscoveredCount = 0;
        for (int i = 0; i < grid.getHeight(); i++) {
            for (int j = 0; j < grid.getWidth(); j++) {
                Cell cell = grid.peekAt(i, j);
                if (cell == null) {
                    return false;
                }

                if (cell.isEmpty() && !cell.isUndiscovered()) {
                    actualDiscoveredCount += 1;
                }
            }
        }

        int expectedDiscoveredCount =
                grid.getWidth() * grid.getHeight() - grid.getMinesCount();

        return actualDiscoveredCount == expectedDiscoveredCount;
    }

    // Draw

    /**
     * Construction de l'en-tête de la grille.
     *
     * @param sb         String builder utilisé pour contruire la représentation de la grille.
     * @param leftMargin La marge gauche utilisée pour afficher les numéros de lignes.
     */
    private void buildHeader(StringBuilder sb, int leftMargin) {
        // Tens
        sb.append(String.format(" %" + leftMargin + "s  ", StringsAndCharacters.EMPTY));
        for (int j = 0; j < grid.getWidth(); j++) {
            if (j != 0 && j % TEN == 0) {
                sb.append(String.format("%10d", j / TEN));
            }
        }
        // Units
        sb.append(StringsAndCharacters.LINE_SEPARATOR);
        sb.append(String.format(" %" + leftMargin + "s ", StringsAndCharacters.EMPTY));
        for (int j = 0; j < grid.getWidth(); j++) {
            sb.append(j % TEN);
        }
        sb.append(StringsAndCharacters.LINE_SEPARATOR);
    }

    /**
     * Construction d'une des lignes de la grille.
     *
     * @param sb         String builder utilisé pour contruire la représentation de la grille.
     * @param leftMargin La marge gauche utilisée pour afficher les numéros de lignes.
     * @param currentRow Le numéro de la ligne courante.
     */
    private void buildRow(StringBuilder sb, int leftMargin, int currentRow) {
        // Margin
        sb.append(String.format(" %" + leftMargin + "d ", currentRow));

        // Row cells
        for (int j = 0; j < grid.getWidth(); j++) {
            Cell cell = grid.peekAt(currentRow, j);
            if (cell == null || cell.isUndiscovered()) {
                sb.append(StringsAndCharacters.UNDISCOVERED);
            } else {
                sb.append(grid.peekAt(currentRow, j).draw());
            }
        }
        sb.append(StringsAndCharacters.LINE_SEPARATOR);
    }

    /** {@inheritDoc} */
    @Override
    public String draw() {
        int leftMargin = String.valueOf(grid.getWidth()).length();

        StringBuilder sb = new StringBuilder();

        buildHeader(sb, leftMargin);
        for (int i = 0; i < grid.getHeight(); i++) {
            buildRow(sb, leftMargin, i);
        }

        return sb.toString();
    }
}
