package seedu.exercise.logic.commands;

import static seedu.exercise.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.exercise.logic.commands.CommandTestUtil.showExerciseAtIndex;
import static seedu.exercise.model.util.DefaultPropertyManagerUtil.getDefaultPropertyManager;
import static seedu.exercise.testutil.TypicalExercises.getTypicalExerciseBook;
import static seedu.exercise.testutil.TypicalIndexes.INDEX_FIRST_EXERCISE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;
import seedu.exercise.model.UserPrefs;
import seedu.exercise.model.book.ExerciseBook;
import seedu.exercise.model.book.RegimeBook;
import seedu.exercise.model.book.ScheduleBook;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalExerciseBook(), new RegimeBook(),
            new ExerciseBook(), new ScheduleBook(), new UserPrefs(), getDefaultPropertyManager());
        expectedModel = new ModelManager(model.getExerciseBookData(), new RegimeBook(),
            new ExerciseBook(), new ScheduleBook(), new UserPrefs(), getDefaultPropertyManager());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showExerciseAtIndex(model, INDEX_FIRST_EXERCISE);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
