package seedu.exercise.logic.commands;

import static seedu.exercise.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.exercise.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.exercise.testutil.TypicalExercises.getTypicalExerciseBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;
import seedu.exercise.model.RegimeBook;
import seedu.exercise.model.UserPrefs;
import seedu.exercise.model.exercise.Exercise;
import seedu.exercise.testutil.ExerciseBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddECommand}.
 */
public class AddECommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalExerciseBook(), new RegimeBook(), new UserPrefs());
    }

    @Test
    public void execute_newExercise_success() {
        Exercise validExercise = new ExerciseBuilder().build();

        Model expectedModel = new ModelManager(model.getAllExerciseData(), new RegimeBook(), new UserPrefs());
        expectedModel.addExercise(validExercise);

        assertCommandSuccess(new AddECommand(validExercise), model,
                String.format(AddECommand.MESSAGE_SUCCESS, validExercise), expectedModel);
    }

    @Test
    public void execute_duplicateExercise_throwsCommandException() {
        Exercise exerciseInList = model.getAllExerciseData().getExerciseList().get(0);
        assertCommandFailure(new AddECommand(exerciseInList), model, AddECommand.MESSAGE_DUPLICATE_EXERCISE);
    }

}
