package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.logic.commands.events.EditEvent.KEY_EDITED_EXERCISE;
import static seedu.exercise.logic.commands.events.EditEvent.KEY_ORIGINAL_EXERCISE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CALORIES;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_MUSCLE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_UNIT;
import static seedu.exercise.logic.parser.predicate.PredicateUtil.PREDICATE_SHOW_ALL_EXERCISES;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import seedu.exercise.commons.core.Messages;
import seedu.exercise.commons.core.index.Index;
import seedu.exercise.commons.core.index.IndexUtil;
import seedu.exercise.commons.util.CollectionUtil;
import seedu.exercise.logic.commands.events.EventHistory;
import seedu.exercise.logic.commands.events.EventPayload;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.Model;
import seedu.exercise.model.property.Calories;
import seedu.exercise.model.property.Date;
import seedu.exercise.model.property.Muscle;
import seedu.exercise.model.property.Name;
import seedu.exercise.model.property.Quantity;
import seedu.exercise.model.property.Unit;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.ui.ListResourceType;

/**
 * Edits the details of an existing exercise in the exercise book.
 */
public class EditCommand extends Command implements UndoableCommand, PayloadCarrierCommand {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the exercise identified "
        + "by the index number used in the displayed exercise list. "
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: "
        + PREFIX_INDEX + "INDEX (must be a positive integer) "
        + "[" + PREFIX_NAME + "NAME] "
        + "[" + PREFIX_DATE + "DATE] "
        + "[" + PREFIX_CALORIES + "CALORIES] "
        + "[" + PREFIX_QUANTITY + "QUANTITY] "
        + "[" + PREFIX_UNIT + "UNIT] "
        + "[" + PREFIX_MUSCLE + "MUSCLE]...\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_INDEX + "1 "
        + PREFIX_DATE + "03/10/2019 "
        + PREFIX_CALORIES + "800";

    public static final String MESSAGE_EDIT_EXERCISE_SUCCESS = "Edited Exercise: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_EXERCISE = "This exercise already exists in the exercise book.";

    private final Index index;
    private final EditExerciseDescriptor editExerciseDescriptor;
    private EventPayload<Exercise> eventPayload;

    /**
     * @param index                  of the exercise in the filtered exercise list to edit
     * @param editExerciseDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditExerciseDescriptor editExerciseDescriptor) {
        requireNonNull(index);
        requireNonNull(editExerciseDescriptor);

        this.index = index;
        this.eventPayload = new EventPayload<>();
        this.editExerciseDescriptor = new EditExerciseDescriptor(editExerciseDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Exercise> lastShownList = model.getFilteredExerciseList();

        if (IndexUtil.isIndexOutOfBounds(index, lastShownList)) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
        }

        Exercise exerciseToEdit = lastShownList.get(index.getZeroBased());
        Exercise editedExercise = createEditedExercise(exerciseToEdit, editExerciseDescriptor);

        if (!exerciseToEdit.isSameResource(editedExercise) && model.hasExercise(editedExercise)) {
            throw new CommandException(MESSAGE_DUPLICATE_EXERCISE);
        }

        addToEventPayload(exerciseToEdit, editedExercise);
        model.setExercise(exerciseToEdit, editedExercise);
        EventHistory.getInstance().addCommandToUndoStack(this);
        model.updateFilteredExerciseList(PREDICATE_SHOW_ALL_EXERCISES);
        model.updateStatistic();
        return new CommandResult(String.format(MESSAGE_EDIT_EXERCISE_SUCCESS, editedExercise),
                ListResourceType.EXERCISE);
    }

    @Override
    public String getUndoableCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public EventPayload<Exercise> getPayload() {
        return eventPayload;
    }

    /**
     * Stores the various states of the exercise to the payload.
     *
     * @param exerciseToEdit the exercise before it is edited
     * @param editedExercise the exercise after it is edited
     */
    private void addToEventPayload(Exercise exerciseToEdit, Exercise editedExercise) {
        eventPayload.put(KEY_ORIGINAL_EXERCISE, exerciseToEdit);
        eventPayload.put(KEY_EDITED_EXERCISE, editedExercise);
    }

    /**
     * Creates and returns a {@code Exercise} with the details of {@code exerciseToEdit}
     * edited with {@code editExerciseDescriptor}.
     */
    private static Exercise createEditedExercise(
        Exercise exerciseToEdit, EditExerciseDescriptor editExerciseDescriptor) {
        assert exerciseToEdit != null;

        Name updatedName = editExerciseDescriptor.getName().orElse(exerciseToEdit.getName());
        Calories updatedCalories = editExerciseDescriptor.getCalories().orElse(exerciseToEdit.getCalories());
        Date updatedDate = editExerciseDescriptor.getDate().orElse(exerciseToEdit.getDate());
        Quantity updatedQuantity = editExerciseDescriptor.getQuantity().orElse(exerciseToEdit.getQuantity());
        Unit updatedUnit = editExerciseDescriptor.getUnit().orElse(exerciseToEdit.getUnit());
        Set<Muscle> updatedMuscles = editExerciseDescriptor.getMuscles().orElse(exerciseToEdit.getMuscles());
        Map<String, String> updatedCustomProperties = new TreeMap<>(exerciseToEdit.getCustomPropertiesMap());
        Map<String, String> newCustomProperties = editExerciseDescriptor.getCustomProperties()
            .orElse(new TreeMap<>());
        updatedCustomProperties.putAll(newCustomProperties);

        return new Exercise(updatedName, updatedDate, updatedCalories, updatedQuantity, updatedUnit,
            updatedMuscles, updatedCustomProperties);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
            && editExerciseDescriptor.equals(e.editExerciseDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditExerciseDescriptor {
        private Name name;
        private Calories calories;
        private Date date;
        private Quantity quantity;
        private Unit unit;
        private Set<Muscle> muscles;
        private Map<String, String> customProperties;

        public EditExerciseDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code muscles} is used internally.
         */
        public EditExerciseDescriptor(EditExerciseDescriptor toCopy) {
            setName(toCopy.name);
            setCalories(toCopy.calories);
            setDate(toCopy.date);
            setQuantity(toCopy.quantity);
            setUnit(toCopy.unit);
            setMuscles(toCopy.muscles);
            setCustomProperties(toCopy.customProperties);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, calories, date, quantity, unit, muscles, customProperties);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setCalories(Calories calories) {
            this.calories = calories;
        }

        public Optional<Calories> getCalories() {
            return Optional.ofNullable(calories);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        public void setQuantity(Quantity quantity) {
            this.quantity = quantity;
        }

        public Optional<Quantity> getQuantity() {
            return Optional.ofNullable(quantity);
        }

        public void setUnit(Unit unit) {
            this.unit = unit;
        }

        public Optional<Unit> getUnit() {
            return Optional.ofNullable(unit);
        }

        /**
         * Sets {@code muscles} to this object's {@code muscles}.
         * A defensive copy of {@code muscles} is used internally.
         */
        public void setMuscles(Set<Muscle> muscles) {
            this.muscles = (muscles != null) ? new HashSet<>(muscles) : null;
        }

        /**
         * Returns an unmodifiable muscle set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code muscles} is null.
         */
        public Optional<Set<Muscle>> getMuscles() {
            return (muscles != null) ? Optional.of(Collections.unmodifiableSet(muscles)) : Optional.empty();
        }

        /**
         * Returns an unmodifiable custom properties map, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code customProperties} is null.
         */
        public Optional<Map<String, String>> getCustomProperties() {
            return (customProperties != null) ? Optional.of(Collections.unmodifiableMap(customProperties))
                : Optional.empty();
        }

        /**
         * Sets {@code customProperties} to this object's {@code customProperties}.
         */
        public void setCustomProperties(Map<String, String> customProperties) {
            this.customProperties = (customProperties != null) ? new TreeMap<>(customProperties) : null;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditExerciseDescriptor)) {
                return false;
            }

            // state check
            EditExerciseDescriptor e = (EditExerciseDescriptor) other;

            return getName().equals(e.getName())
                && getCalories().equals(e.getCalories())
                && getDate().equals(e.getDate())
                && getQuantity().equals(e.getQuantity())
                && getUnit().equals(e.getUnit())
                && getMuscles().equals(e.getMuscles())
                && getCustomProperties().equals(e.getCustomProperties());
        }
    }
}
