package seedu.exercise.testutil.builder;

import static seedu.exercise.commons.core.CommonComparator.EXERCISE_DESCENDING_DATE_COMPARATOR;
import static seedu.exercise.commons.core.CommonComparator.REGIME_ASCENDING_NAME_COMPARATOR;

import java.util.List;

import seedu.exercise.model.SortedUniqueResourceList;
import seedu.exercise.model.property.Name;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.model.resource.Regime;

/**
 * Builder for {@code Regime}.
 */
public class RegimeBuilder {

    private static final String DEFAULT_NAME = "cardio";

    private Name regimeName;
    private SortedUniqueResourceList<Exercise> regimeExercises;

    public RegimeBuilder() {
        regimeName = new Name(DEFAULT_NAME);
        regimeExercises = new SortedUniqueResourceList<>(EXERCISE_DESCENDING_DATE_COMPARATOR);
    }

    /**
     * Initializes the RegimeBuilder with a deep copy of the data of {@code RegimeToCopy}.
     */
    public RegimeBuilder(Regime regimeToCopy) {
        regimeName = new Name(regimeToCopy.getRegimeName().toString());
        regimeExercises = new SortedUniqueResourceList<>(EXERCISE_DESCENDING_DATE_COMPARATOR);
        regimeExercises.setAll(regimeToCopy.getRegimeExercises());
    }

    /**
     * Parses and sets the name of the object we are building to {@code name}.
     */
    public RegimeBuilder withName(String name) {
        this.regimeName = new Name(name);
        return this;
    }

    /**
     * Sets the exercise list of the regime we are building to {@code regimeExercises}.
     */
    public RegimeBuilder withExerciseList(SortedUniqueResourceList<Exercise> regimeExercises) {
        this.regimeExercises.setAll(regimeExercises); ;
        return this;
    }


    /**
     * Sets the exercise list of the regime we are building to {@code regimeExercises}
     * using an alternative argument.
     */
    public RegimeBuilder withExerciseList(List<Exercise> exercises) {
        this.regimeExercises.setAll(exercises);
        return this;
    }

    /**
     * Adds an {@code Exercise} to the {@code Regime} that we are building.
     */
    public RegimeBuilder withExercise(Exercise exercise) {
        this.regimeExercises.add(exercise);
        return this;
    }

    /**
     * Deletes an {@code Exercise} from the {@code Regime} that we are building.
     */
    public RegimeBuilder deleteExercise(Exercise exercise) {
        this.regimeExercises.remove(exercise);
        return this;
    }

    public Regime build() {
        return new Regime(regimeName, regimeExercises);
    }
}
