package seedu.exercise.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.exercise.commons.core.GuiSettings;
import seedu.exercise.logic.commands.CommandResult;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.logic.parser.exceptions.ParseException;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.model.resource.Regime;
import seedu.exercise.model.resource.Schedule;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the ExerciseBook.
     *
     * @see seedu.exercise.model.Model#getExerciseBookData()
     */
    ReadOnlyResourceBook<Exercise> getExerciseBook();

    /**
     * Returns an unmodifiable view of the filtered list of exercises.
     */
    ObservableList<Exercise> getFilteredExerciseList();

    /**
     * Returns the RegimeBook.
     *
     * @see seedu.exercise.model.Model#getAllRegimeData()
     */
    ReadOnlyResourceBook<Regime> getRegimeBook();

    ObservableList<Regime> getFilteredRegimeList();

    /**
     * Returns an unmodifiable view of the filtered list of schedules
     */
    ObservableList<Schedule> getFilteredScheduleList();

    /**
     * Returns the user prefs' exercise book file path.
     */
    Path getExerciseBookFilePath();

    /**
     * Returns the user prefs' regime book file path.
     */
    Path getRegimeBookFilePath();

    /**
     * Returns an unmodifiable view of the suggested list of exercises.
     */
    ObservableList<Exercise> getSuggestedExerciseList();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

}
