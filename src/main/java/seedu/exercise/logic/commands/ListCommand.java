package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_SUGGEST_TYPE;
import static seedu.exercise.model.Model.PREDICATE_SHOW_ALL_EXERCISES;

import seedu.exercise.model.Model;
import seedu.exercise.ui.ListResourceType;

/**
 * Lists all exercises in the exercise book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": List items.\n"
            + "Parameters: "
            + PREFIX_CATEGORY + "LIST_TYPE" + "\n"
            + "\t\tExample: "
            + COMMAND_WORD + " "
            + PREFIX_CATEGORY + "schedule";
    public static final String MESSAGE_SUCCESS = "Listed all %1$s items";

    private String listType;

    public ListCommand(String listType) {
        this.listType = listType;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredExerciseList(PREDICATE_SHOW_ALL_EXERCISES);
        return new CommandResult(MESSAGE_SUCCESS, ListResourceType.EXERCISE);
    }

}
