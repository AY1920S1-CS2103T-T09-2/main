package seedu.exercise.logic.commands;

import static seedu.exercise.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.commons.core.Messages;
import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.UserPrefs;
import seedu.exercise.model.resource.Schedule;
import seedu.exercise.model.util.DefaultPropertyBookUtil;
import seedu.exercise.testutil.TypicalIndexes;
import seedu.exercise.testutil.TypicalSchedule;
import seedu.exercise.testutil.exercise.TypicalExercises;

/**
 * Contains integration test for {@code ScheduleCompleteCommand}.
 */
public class ScheduleCompleteCommandTest {

    private Model model;

    private final ScheduleCommand scheduleCompleteCommandWithOutOfBoundsIndex =
            new ScheduleCompleteCommand(TypicalIndexes.INDEX_VERY_LARGE_NUMBER);
    private final ScheduleCommand scheduleCompleteCommandWithValidIndex =
            new ScheduleCompleteCommand(TypicalIndexes.INDEX_ONE_BASED_FIRST);

    @BeforeEach
    public void setUp() {
         model = new ModelManager(TypicalExercises.getTypicalExerciseBook(),
                new ReadOnlyResourceBook<>(), new ReadOnlyResourceBook<>(), TypicalSchedule.getTypicalScheduleBook(),
                new UserPrefs(), DefaultPropertyBookUtil.getDefaultPropertyBook());
    }

    @Test
    public void execute_outOfBoundsIndex_throwsCommandException() {
        String expectedMessage = Messages.MESSAGE_INVALID_SCHEDULE_DISPLAYED_INDEX;
        CommandTestUtil.assertCommandFailure(scheduleCompleteCommandWithOutOfBoundsIndex, model, expectedMessage);
    }

    @Test
    public void execute_validCommand_success() {
        String expectedMessage = String.format(ScheduleCompleteCommand.MESSAGE_SUCCESS,
                Integer.toString(TypicalIndexes.INDEX_ONE_BASED_FIRST.getOneBased()));

        Schedule toComplete = model.getFilteredScheduleList().get(0);
        Model expectedModel = new ModelManager(model.getExerciseBookData(),
                new ReadOnlyResourceBook<>(), new ReadOnlyResourceBook<>(), model.getAllScheduleData(),
                new UserPrefs(), DefaultPropertyBookUtil.getDefaultPropertyBook());
        expectedModel.completeSchedule(toComplete);

        CommandTestUtil.assertCommandSuccess(scheduleCompleteCommandWithValidIndex, model,
                expectedMessage, expectedModel);
    }

    @Test
    public void constructor_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ScheduleCompleteCommand(null));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> scheduleCompleteCommandWithValidIndex.execute(null));
    }
}
