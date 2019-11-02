package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.commons.core.Messages.MESSAGE_INVALID_CONTEXT;
import static seedu.exercise.commons.util.CollectionUtil.areListsEmpty;
import static seedu.exercise.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CONFLICT_INDEX;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;

import seedu.exercise.MainApp;
import seedu.exercise.commons.core.Messages;
import seedu.exercise.commons.core.State;
import seedu.exercise.commons.core.index.Index;
import seedu.exercise.commons.core.index.IndexUtil;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.Model;
import seedu.exercise.model.UniqueResourceList;
import seedu.exercise.model.conflict.Conflict;
import seedu.exercise.model.property.Name;
import seedu.exercise.model.resource.Regime;
import seedu.exercise.ui.ListResourceType;

/**
 * Represents a Resolve Command that resolves scheduling conflicts.
 */
public class ResolveCommand extends Command {

    public static final String COMMAND_WORD = "resolve";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Resolves a scheduling conflict. \n"
            + "USAGE 1: Take one regime completely:\n"
            + "Parameters: \n"
            + PREFIX_NAME + "SCHEDULED_OR_CONFLICTING\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "n/scheduled\nUSAGE 2: Take some exercise from both schedule.\n"
            + "Parameters: \n"
            + PREFIX_NAME + "NEW_REGIME_NAME "
            + "[" + PREFIX_INDEX + "INDEX_OF_SCHEDULED_EXERCISES" + "]"
            + "[" + PREFIX_CONFLICT_INDEX + "INDEX_OF_CONFLICTING_EXERCISES" + "]"
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_NAME + "cardio new "
            + PREFIX_INDEX + "1 "
            + PREFIX_INDEX + "3 "
            + PREFIX_CONFLICT_INDEX + "1 "
            + PREFIX_CONFLICT_INDEX + "4 ";

    public static final String TAKE_FROM_SCHEDULED = "scheduled";
    public static final String TAKE_FROM_CONFLICTING = "conflicting";
    public static final String MESSAGE_SUCCESS = "Successfully resolved conflict between regime %1$s and regime %2$s";
    public static final String MESSAGE_DUPLICATE_NAME = "Regime name %1$s already exists. Try another name";
    public static final String MESSAGE_INVALID_NAME = "Name provided is neither " + TAKE_FROM_SCHEDULED
            + " nor is it " + TAKE_FROM_CONFLICTING + ". Please input " + PREFIX_NAME + TAKE_FROM_SCHEDULED
            + " or " + PREFIX_NAME + TAKE_FROM_CONFLICTING + ".";
    public static final String MESSAGE_DUPLICATE_EXERCISE_SELECTED =
            "You have selected some exercises that are the same from both schedules.\n"
            + "You only have to select one of them.";

    private Name scheduledOrConflicting;
    private Conflict conflict;
    private List<Index> indexToTakeFromSchedule;
    private List<Index> indexToTakeFromConflict;

    public ResolveCommand(Name scheduledOrConflicting, List<Index> indexToTakeFromSchedule,
                          List<Index> indexToTakeFromConflict) {
        requireAllNonNull(scheduledOrConflicting, indexToTakeFromConflict, indexToTakeFromSchedule);
        this.scheduledOrConflicting = scheduledOrConflicting;
        this.indexToTakeFromSchedule = indexToTakeFromSchedule;
        this.indexToTakeFromConflict = indexToTakeFromConflict;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        checkIfProgramStateIsValid();

        conflict = getConflictFromModel(model);

        checkValidIndexes();
        if (!areListsEmpty(indexToTakeFromSchedule, indexToTakeFromConflict)) {
            checkNonDuplicateRegimeNameFromModel(model);
            checkSelectedIndexesDoNotHaveDuplicatesFromModel(model);
        } else {
            checkNameIsScheduledOrConflicting();
        }

        resolveConflict(model);
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                conflict.getScheduledName(),
                conflict.getConflictedName()), ListResourceType.SCHEDULE);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        //instanceof handles nulls
        if (!(other instanceof ResolveCommand)) {
            return false;
        }

        ResolveCommand otherCommand = (ResolveCommand) other;
        return scheduledOrConflicting.equals(otherCommand.scheduledOrConflicting)
                && indexToTakeFromConflict.equals(otherCommand.indexToTakeFromConflict)
                && indexToTakeFromSchedule.equals(otherCommand.indexToTakeFromSchedule);
    }

    private void resolveConflict(Model model) {
        requireNonNull(model);
        model.resolveConflict(scheduledOrConflicting, indexToTakeFromSchedule, indexToTakeFromConflict);
        MainApp.setState(State.NORMAL);
    }

    private Conflict getConflictFromModel(Model model) {
        requireNonNull(model);
        return model.getConflict();
    }

    /**
     * Checks if indexes passed into the {@code Command} are valid and not out of bounds
     */
    private void checkValidIndexes() throws CommandException {
        if (IndexUtil.areIndexesOutOfBounds(indexToTakeFromSchedule, conflict.getScheduledExerciseList())
            || IndexUtil.areIndexesOutOfBounds(indexToTakeFromConflict, conflict.getConflictedExerciseList())) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
        }
    }

    private void checkIfProgramStateIsValid() throws CommandException {
        if (MainApp.getState() != State.IN_CONFLICT) {
            throw new CommandException(String.format(MESSAGE_INVALID_CONTEXT, getClass().getSimpleName()));
        }
    }

    private void checkSelectedIndexesDoNotHaveDuplicatesFromModel(Model model) throws CommandException {
        if (model.isSelectedIndexesFromRegimeDuplicate(indexToTakeFromSchedule, indexToTakeFromConflict)) {
            throw new CommandException(MESSAGE_DUPLICATE_EXERCISE_SELECTED);
        }
    }

    /**
     * Checks if there are duplicate {@code regime names} in the {@code model}.
     * Method is to be called only if there are indexes provided to the Regime Command.
     */
    private void checkNonDuplicateRegimeNameFromModel(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasRegime(new Regime(scheduledOrConflicting, new UniqueResourceList<>()))) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_NAME, scheduledOrConflicting.toString()));
        }
    }

    /**
     * Checks if the name passed in is either {@code conflicting} or {@code scheduled}.
     */
    private void checkNameIsScheduledOrConflicting() throws CommandException {
        String name = scheduledOrConflicting.toString();
        if (!name.equals(TAKE_FROM_SCHEDULED) && !name.equals(TAKE_FROM_CONFLICTING)) {
            throw new CommandException(MESSAGE_INVALID_NAME);
        }
    }
}
