package seedu.exercise.testutil.builder;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.exercise.logic.commands.EditExerciseDescriptor;
import seedu.exercise.model.property.Calories;
import seedu.exercise.model.property.Date;
import seedu.exercise.model.property.Muscle;
import seedu.exercise.model.property.Name;
import seedu.exercise.model.property.Quantity;
import seedu.exercise.model.property.Unit;
import seedu.exercise.model.resource.Exercise;

/**
 * A utility class to help with building EditExerciseDescriptor objects.
 */
public class EditExerciseDescriptorBuilder {

    private EditExerciseDescriptor descriptor;

    public EditExerciseDescriptorBuilder() {
        descriptor = new EditExerciseDescriptor();
    }

    public EditExerciseDescriptorBuilder(EditExerciseDescriptor descriptor) {
        this.descriptor = new EditExerciseDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditExerciseDescriptor} with fields containing {@code exercise}'s details
     */
    public EditExerciseDescriptorBuilder(Exercise exercise) {
        descriptor = new EditExerciseDescriptor();
        descriptor.setName(exercise.getName());
        descriptor.setCalories(exercise.getCalories());
        descriptor.setDate(exercise.getDate());
        descriptor.setQuantity(exercise.getQuantity());
        descriptor.setMuscles(exercise.getMuscles());
    }

    /**
     * Sets the {@code Name} of the {@code EditExerciseDescriptor} that we are building.
     */
    public EditExerciseDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Calories} of the {@code EditExerciseDescriptor} that we are building.
     */
    public EditExerciseDescriptorBuilder withCalories(String calories) {
        descriptor.setCalories(new Calories(calories));
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditExerciseDescriptor} that we are building.
     */
    public EditExerciseDescriptorBuilder withDate(String date) {
        descriptor.setDate(new Date(date));
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code EditExerciseDescriptor} that we are building.
     */
    public EditExerciseDescriptorBuilder withQuantity(String quantity) {
        descriptor.setQuantity(new Quantity(quantity));
        return this;
    }

    /**
     * Sets the {@code Unit} of the {@code EditExerciseDescriptor} that we are building.
     */
    public EditExerciseDescriptorBuilder withUnit(String unit) {
        descriptor.setUnit(new Unit(unit));
        return this;
    }

    /**
     * Parses the {@code muscles} into a {@code Set<Muscle>} and set it to the {@code EditExerciseDescriptor}
     * that we are building.
     */
    public EditExerciseDescriptorBuilder withMuscles(String... muscles) {
        Set<Muscle> muscleSet = Stream.of(muscles).map(Muscle::new).collect(Collectors.toSet());
        descriptor.setMuscles(muscleSet);
        return this;
    }

    public EditExerciseDescriptor build() {
        return descriptor;
    }
}
