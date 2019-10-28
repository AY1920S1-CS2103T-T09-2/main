package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.logic.commands.events.ClearEvent.KEY_EXERCISE_BOOK_CLEARED;

import seedu.exercise.logic.commands.events.EventHistory;
import seedu.exercise.logic.commands.events.EventPayload;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.Model;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.resource.Exercise;

/**
 * Clears the exercise book.
 */
public class ClearCommand extends Command implements UndoableCommand, PayloadCarrierCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Exercise book has been cleared!";

    private EventPayload<ReadOnlyResourceBook<Exercise>> eventPayload;

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ReadOnlyResourceBook<Exercise> exerciseBookCleared = new ReadOnlyResourceBook<>(model.getExerciseBookData());
        eventPayload = new EventPayload<>();
        eventPayload.put(KEY_EXERCISE_BOOK_CLEARED, exerciseBookCleared);
        EventHistory.getInstance().addCommandToUndoStack(this);
        model.setExerciseBook(new ReadOnlyResourceBook<>());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public String getUndoableCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public EventPayload<ReadOnlyResourceBook<Exercise>> getPayload() {
        return eventPayload;
    }

}
