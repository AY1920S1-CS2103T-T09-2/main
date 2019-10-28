package seedu.exercise.logic.parser.predicate;

import java.util.Set;

import seedu.exercise.model.property.Muscle;
import seedu.exercise.model.resource.Exercise;

/**
 * Tests whether an {@code Exercise} matches the {@code predicate}
 */
public class ExerciseMusclePredicate implements BasePropertyPredicate {

    private final Set<Muscle> muscles;
    private final boolean isStrict;

    public ExerciseMusclePredicate(Set<Muscle> muscles, boolean isStrict) {
        this.muscles = muscles;
        this.isStrict = isStrict;
    }

    @Override
    public boolean test(Exercise exercise) {
        if (isStrict) {
            return testStrict(exercise);
        } else {
            return testLoose(exercise);
        }
    }

    /**
     * Returns true if a {@code exercise} has all the {@code Muscle} targeted
     */
    boolean testStrict(Exercise exercise) {
        Set<Muscle> exerciseMuscles = exercise.getMuscles();
        for (Muscle muscle : muscles) {
            if (!(exerciseMuscles.contains(muscle))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if a {@code exercise} has at least one {@code Muscle} targeted
     */
    boolean testLoose(Exercise exercise) {
        Set<Muscle> exerciseMuscles = exercise.getMuscles();
        for (Muscle muscle : exerciseMuscles) {
            if (muscles.contains(muscle)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
            || (other instanceof ExerciseMusclePredicate //instanceof handles null
            && muscles.equals(((ExerciseMusclePredicate) other).muscles)
            && isStrict == ((ExerciseMusclePredicate) other).isStrict);
    }



}
