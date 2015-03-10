package org.dryft.minesweeper.constants;

/**
 * Chaînes de caractères et caractères souvents utilisés.
 */
public enum StringsAndCharacters {
    ; // Singleton

    /** Caractère représentant une cellule vide. */
    public static Character EMPTY_CELL = ' ';
    /** Caractère représentant une mine. */
    public static Character MINE = '¤';
    // \u2588 would be better, but it only works on 'true' monospaced fonts...
    /** Caractère représentant une cellule non découverte. */
    public static Character UNDISCOVERED = '@';

    /** Chaîne vide. */
    public static String EMPTY = "";
    /** Espace. */
    public static String SPACE = " ";
    /** Fin de ligne pour le système courant. */
    public static String LINE_SEPARATOR = System.getProperty("line.separator");
}
