package seedu.exercise.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.exercise.commons.core.GuiSettings;
import seedu.exercise.commons.core.index.Index;
import seedu.exercise.model.exercise.Exercise;
import seedu.exercise.model.regime.Regime;
import seedu.exercise.model.schedule.Schedule;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Exercise> PREDICATE_SHOW_ALL_EXERCISES = unused -> true;

    /** {@code Predicate} that always evaluate to true*/
    Predicate<Regime> PREDICATE_SHOW_ALL_REGIMES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' exercise book file path.
     */
    Path getExerciseBookFilePath();

    /**
     * Sets the user prefs' exercise book file path.
     */
    void setExerciseBookFilePath(Path exerciseBookFilePath);

    /**
     * Replaces exercise book data with the data in {@code anotherBook}.
     */
    void setExerciseBook(ReadOnlyExerciseBook anotherBook);

    /** Returns the data in the exercise book */
    ReadOnlyExerciseBook getAllExerciseData();

    /**
     * Returns true if an exercise with the same identity as {@code exercise} exists in the exercise book.
     */
    boolean hasExercise(Exercise exercise);

    /**
     * Deletes the given exercise.
     * The exercise must exist in the exercise book.
     */
    void deleteExercise(Exercise target);

    /**
     * Adds the given exercise.
     * {@code exercise} must not already exist in exercise book.
     */
    void addExercise(Exercise exercise);

    /**
     * Replaces the given exercise {@code target} with {@code editedExercise}.
     * {@code target} must exist in exercise book.
     * The exercise identity of {@code editedExercise} must not be the same as another existing exercise in
     * the exercise book.
     */
    void setExercise(Exercise target, Exercise editedExercise);

    /** Returns an unmodifiable view of the filtered exercise list */
    ObservableList<Exercise> getFilteredExerciseList();

    /** Returns an unmodifiable view of the filtered regime list */
    ObservableList<Regime> getFilteredRegimeList();

    /** Returns an unmodifiable view of the filtered schedule list */
    ObservableList<Schedule> getFilteredScheduleList();

    /**
     * Updates the filter of the filtered exercise list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredExerciseList(Predicate<Exercise> predicate);


    /**
     * Returns the user prefs' regime book file path.
     */
    Path getRegimeBookFilePath();

    /**
     * Sets the user prefs' regime book file path.
     */
    void setRegimeBookFilePath(Path regimeBookFilePath);

    /**
     * Replaces regime book data with the data in {@code anotherBook}.
     */
    void setRegimeBook(ReadOnlyRegimeBook anotherBook);

    /** Returns the data in the regime book */
    ReadOnlyRegimeBook getAllRegimeData();

    /**
     * Returns true if an regime with the same identity as {@code regime} exists in the regime book.
     */
    boolean hasRegime(Regime regime);

    /**
     * Adds the given regime.
     * {@code regime} must not already exist in regime book.
     */
    void addRegime(Regime regime);

    /**
     * Replaces the given regime {@code target} with {@code editedRegime}.
     * {@code target} must exist in regime book.
     * The regime identity of {@code editedRegime} must not be the same as another existing regime in
     * the regime book.
     */
    void setRegime(Regime target, Regime editedRegime);

    /**
     * Deletes the given regime.
     * The regime must exist in the regime book.
     */
    void deleteRegime(Regime regime);

    /**
     * Returns the index of regime in regime book.
     */
    int getRegimeIndex(Regime regime);

    /**
     * Updates the filter of the filtered exercise list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredRegimeList(Predicate<Regime> predicate);

    /**
     * Returns true if another schedule has been scheduled on the same date as {@code schedule}.
     */
    boolean hasSchedule(Schedule schedule);

    /**
     * Schedules a {@code schedule} for the user.
     * It must be guranteed that there is no existing schedule in the {@code ScheduleBook}
     */
    void addSchedule(Schedule schedule);

    /** Returns the data in the regime book */
    ReadOnlyScheduleBook getAllScheduleData();

    /**
     * Completes a regime and adds it to {@code ExerciseBook} for tracking.
     * @param index of the regime to complete
     */
    void completeRegime(Index index);
}
