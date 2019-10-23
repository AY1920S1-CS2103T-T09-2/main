package seedu.exercise.logic.commands;

import static seedu.exercise.logic.commands.CustomAddCommand.MESSAGE_USAGE_CUSTOM_ADD;
import static seedu.exercise.logic.commands.CustomRemoveCommand.MESSAGE_USAGE_CUSTOM_REMOVE;

/**
 * Represents a CustomCommand with hidden internal logic and the ability to be executed.
 */
public abstract class CustomCommand extends Command {
    public static final String COMMAND_WORD = "custom";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds or removes a custom property for the exercises\n"
        + "ADD: " + MESSAGE_USAGE_CUSTOM_ADD + "\n"
        + "REMOVE: " + MESSAGE_USAGE_CUSTOM_REMOVE;

}
