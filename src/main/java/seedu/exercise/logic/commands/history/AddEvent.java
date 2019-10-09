package seedu.exercise.logic.commands.history;

import seedu.exercise.model.Model;
import seedu.exercise.model.exercise.Exercise;

/**
 * Represents a particular add event that can be redone or or undone.
 */
public class AddEvent implements Event {

    private static final String EVENT_DESCRIPTION = "Add exercise: %1$s";

    /**
     * The exercise that has been added during the event.
     */
    private final Exercise exercise;

    /**
     * Creates an AddEvent to store the particular event of an exercise being added to the exercise book.
     *
     * @param exercise the exercise that has been added in this instance of AddEvent.
     */
    AddEvent(Exercise exercise) {
        this.exercise = exercise;
    }

    @Override
    public void undo(Model model) {
        model.deleteExercise(exercise);
    }

    @Override
    public void redo(Model model) {
        model.addExercise(exercise);
    }

    /**
     * Returns the exercise that was added.
     *
     * @return exercise that is passed into constructor of AddEvent
     */
    public Exercise getExercise() {
        return exercise;
    }

    @Override
    public String toString() {
        return String.format(EVENT_DESCRIPTION, exercise);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEvent // instanceof handles nulls
                && exercise.equals(((AddEvent) other).getExercise()));
    }

}
