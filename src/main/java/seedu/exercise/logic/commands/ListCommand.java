package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.logic.parser.predicate.PredicateUtil.PREDICATE_SHOW_ALL_EXERCISES;

import seedu.exercise.model.Model;
import seedu.exercise.ui.ListResourceType;

/**
 * Lists all exercises in the exercise book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all exercises";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS, ListResourceType.EXERCISE);
    }

}
