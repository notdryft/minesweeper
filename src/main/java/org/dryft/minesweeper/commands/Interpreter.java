package org.dryft.minesweeper.commands;

import org.dryft.minesweeper.commands.impl.CellCommand;
import org.dryft.minesweeper.commands.impl.ExitCommand;
import org.dryft.minesweeper.commands.impl.GridCommand;
import org.dryft.minesweeper.commands.impl.UnknownCommand;
import org.dryft.minesweeper.constants.StringsAndCharacters;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Implémentation d'un interpréteur de commande, à partir des lignes issues d'un terminal.
 */
public class Interpreter {

    /** Association des classes de commandes à des patterns (regex) particuliers. */
    private static Map<String, Class<? extends Command>> COMMANDS_BY_PATTERN = new LinkedHashMap<>();

    static {
        COMMANDS_BY_PATTERN.put("\\d+ \\d+ \\d+", GridCommand.class);
        COMMANDS_BY_PATTERN.put("\\d+ \\d+", CellCommand.class);
        COMMANDS_BY_PATTERN.put("exit|quit", ExitCommand.class);
    }

    /** L'instance d'un {@link Receiver} qui va réellement implémenter la commande. */
    private Receiver receiver;

    /**
     * Constructeur.
     *
     * @param receiver {@link Interpreter#receiver}
     */
    public Interpreter(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Préparation d'une instance de commande en fonction de la classe donnée en entrée.
     *
     * @param commandClass Le modèle à partir duquel contruire une commande.
     * @return La commande instanciée avec ses dépendances.
     */
    private Command prepareInstanceCommandClass(Class<? extends Command> commandClass) {
        try {
            Constructor<? extends Command> constructor = commandClass.getConstructor(Receiver.class);

            return constructor.newInstance(receiver);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            System.err.println("Error while preparing command: " + e.getMessage());

            return null;
        }
    }

    /**
     * Implémentation réelle de l'analyse. Les patterns sont analysés en chaîne jusqu'à trouver la bonne implémentation.
     *
     * @param trimmedLine Une ligne dont les espaces surperflus ont été préalablement retirés.
     * @return La commande la plus adaptée, commande inconnue sinon.
     */
    private Command parse0(String trimmedLine) {
        for (Map.Entry<String, Class<? extends Command>> commandPattern : COMMANDS_BY_PATTERN.entrySet()) {
            Pattern pattern = Pattern.compile(commandPattern.getKey());
            if (pattern.matcher(trimmedLine).matches()) {
                Command command = prepareInstanceCommandClass(commandPattern.getValue());
                if (command == null) {
                    return new UnknownCommand();
                }

                if (command.getMaxParameters() > 0) {
                    command.setParameters(trimmedLine.split(StringsAndCharacters.SPACE));
                }

                return command;
            }
        }

        return new UnknownCommand();
    }

    /**
     * Analyse d'une ligne pour en déduire la bonne implémentation de commande à utiliser.
     *
     * @param line Une ligne quelconque.
     * @return La commande la plus adaptée, commande inconnue sinon.
     */
    public Command parse(String line) {
        if (line == null) {
            return new UnknownCommand();
        }

        String trimmedLine = line.trim();
        if (trimmedLine.equals(StringsAndCharacters.EMPTY)) {
            return new UnknownCommand();
        }

        return parse0(trimmedLine);
    }
}
