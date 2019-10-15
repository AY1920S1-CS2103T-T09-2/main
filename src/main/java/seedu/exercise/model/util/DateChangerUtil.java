package seedu.exercise.model.util;

import java.util.Collection;
import java.util.stream.Collectors;

import seedu.exercise.model.exercise.Date;
import seedu.exercise.model.exercise.Exercise;

/**
 * Contains utility method to change dates of exercises
 */
public class DateChangerUtil {

    /**
     * Changes all {@code exercise} to the date sepecified by {@code changedDate}.
     *
     * Operation will create a whole new list that is not backed by {@code exercises}.
     */
    public static Collection<Exercise> changeAllDate(Collection<Exercise> exercises, Date changedDate) {
        return exercises.stream()
                .map(exercise -> new Exercise(
                        exercise.getName(),
                        changedDate,
                        exercise.getCalories(),
                        exercise.getQuantity(),
                        exercise.getUnit(),
                        exercise.getMuscles()))
                .collect(Collectors.toList());
    }
}
