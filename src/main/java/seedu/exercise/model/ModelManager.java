package seedu.exercise.model;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.commons.util.AppUtil.requireMainAppState;
import static seedu.exercise.commons.util.CollectionUtil.append;
import static seedu.exercise.commons.util.CollectionUtil.areListsEmpty;
import static seedu.exercise.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.exercise.model.property.PropertyBook.getCustomProperties;
import static seedu.exercise.model.util.DefaultPropertyBookUtil.getDefaultPropertyBook;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.exercise.commons.core.GuiSettings;
import seedu.exercise.commons.core.LogsCenter;
import seedu.exercise.commons.core.State;
import seedu.exercise.commons.core.index.Index;
import seedu.exercise.logic.commands.statistic.Statistic;
import seedu.exercise.logic.commands.statistic.StatsFactory;
import seedu.exercise.logic.parser.Prefix;
import seedu.exercise.model.conflict.Conflict;
import seedu.exercise.model.property.CustomProperty;
import seedu.exercise.model.property.Name;
import seedu.exercise.model.property.PropertyBook;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.model.resource.Regime;
import seedu.exercise.model.resource.Schedule;
import seedu.exercise.model.util.DateChangerUtil;

/**
 * Represents the in-memory model of the exercise book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ReadOnlyResourceBook<Exercise> exerciseBook;
    private final ReadOnlyResourceBook<Regime> regimeBook;
    private final ReadOnlyResourceBook<Exercise> databaseBook;
    private final ReadOnlyResourceBook<Schedule> scheduleBook;
    private final UserPrefs userPrefs;
    private final PropertyBook propertyBook;
    private final FilteredList<Exercise> filteredExercises;
    private final FilteredList<Regime> filteredRegimes;
    private final FilteredList<Schedule> filteredSchedules;
    private final ObservableList<Exercise> suggestions = FXCollections.observableArrayList();
    private final Statistic statistic;
    private Conflict conflict;

    /**
     * Initializes a ModelManager with the given exerciseBook and userPrefs.
     */
    public ModelManager(ReadOnlyResourceBook<Exercise> exerciseBook, ReadOnlyResourceBook<Regime> regimeBook,
                        ReadOnlyResourceBook<Exercise> databaseBook, ReadOnlyResourceBook<Schedule> scheduleBook,
                        ReadOnlyUserPrefs userPrefs, PropertyBook propertyBook) {
        super();
        requireAllNonNull(exerciseBook, regimeBook, databaseBook, scheduleBook, userPrefs, propertyBook);

        logger.fine("Initializing with exercise book: " + exerciseBook + " and user prefs " + userPrefs);

        this.exerciseBook = new ReadOnlyResourceBook<>(exerciseBook);
        this.databaseBook = new ReadOnlyResourceBook<>(databaseBook);
        this.regimeBook = new ReadOnlyResourceBook<>(regimeBook);
        this.scheduleBook = new ReadOnlyResourceBook<>(scheduleBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredExercises = new FilteredList<>(this.exerciseBook.getResourceList());
        filteredRegimes = new FilteredList<>(this.regimeBook.getResourceList());
        filteredSchedules = new FilteredList<>(this.scheduleBook.getResourceList());
        StatsFactory statsFactory = new StatsFactory(exerciseBook, "linechart", "calories", null, null);
        this.statistic = statsFactory.getDefaultStatistic();

        this.propertyBook = propertyBook;
        this.propertyBook.updatePropertyPrefixes();

        conflict = null;
    }

    public ModelManager() {
        this(new ReadOnlyResourceBook<>(), new ReadOnlyResourceBook<>(), new ReadOnlyResourceBook<>(),
            new ReadOnlyResourceBook<>(), new UserPrefs(), getDefaultPropertyBook());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getExerciseBookFilePath() {
        return userPrefs.getExerciseBookFilePath();
    }

    @Override
    public void setExerciseBookFilePath(Path exerciseBookFilePath) {
        requireNonNull(exerciseBookFilePath);
        userPrefs.setExerciseBookFilePath(exerciseBookFilePath);
    }

    @Override
    public Path getRegimeBookFilePath() {
        return userPrefs.getRegimeBookFilePath();
    }

    @Override
    public void setRegimeBookFilePath(Path regimeBookFilePath) {
        requireNonNull(regimeBookFilePath);
        userPrefs.setRegimeBookFilePath(regimeBookFilePath);
    }

    //=========== ExerciseBook ================================================================================

    @Override
    public void setExerciseBook(ReadOnlyResourceBook<Exercise> anotherBook) {
        this.exerciseBook.resetData(anotherBook);
    }

    @Override
    public ReadOnlyResourceBook<Exercise> getExerciseBookData() {
        return exerciseBook;
    }

    @Override
    public boolean hasExercise(Exercise exercise) {
        requireNonNull(exercise);
        return exerciseBook.hasResource(exercise);
    }

    @Override
    public void deleteExercise(Exercise target) {
        exerciseBook.removeResource(target);
    }

    /**
     * Adds an {@code Exercise} object into the exercise book.
     */
    public void addExercise(Exercise exercise) {
        exerciseBook.addResource(exercise);
        updateFilteredExerciseList(PREDICATE_SHOW_ALL_EXERCISES);
    }

    public void setExercise(Exercise target, Exercise editedExercise) {
        requireAllNonNull(target, editedExercise);
        exerciseBook.setResource(target, editedExercise);
    }

    //===================RegimeBook==============================================================================

    @Override
    public void setRegimeBook(ReadOnlyResourceBook<Regime> anotherBook) {
        this.regimeBook.resetData(anotherBook);
    }

    @Override
    public ReadOnlyResourceBook<Regime> getAllRegimeData() {
        return regimeBook;
    }

    /**
     * Adds a {@code Regime} object into the regime book.
     */
    @Override
    public void addRegime(Regime regime) {
        regimeBook.addResource(regime);
    }

    @Override
    public void deleteRegime(Regime target) {
        regimeBook.removeResource(target);
    }

    @Override
    public void setRegime(Regime target, Regime editedRegime) {
        regimeBook.setResource(target, editedRegime);
    }

    @Override
    public boolean hasRegime(Regime regime) {
        requireNonNull(regime);
        return regimeBook.hasResource(regime);
    }

    @Override
    public int getRegimeIndex(Regime regime) {
        return regimeBook.getResourceIndex(regime);
    }

    //===================ReadOnlyResourceBook<Schedule>===============================================================
    @Override
    public boolean hasSchedule(Schedule schedule) {
        requireNonNull(schedule);
        return scheduleBook.hasResource(schedule);
    }

    @Override
    public void addSchedule(Schedule schedule) {
        requireNonNull(schedule);
        scheduleBook.addResource(schedule);
    }

    @Override
    public void removeSchedule(Schedule schedule) {
        requireNonNull(schedule);
        scheduleBook.removeResource(schedule);
    }

    @Override
    public void completeSchedule(Schedule schedule) {
        requireNonNull(schedule);

        scheduleBook.removeResource(schedule);
        Collection<Exercise> scheduledExercises = DateChangerUtil
            .changeAllDate(schedule.getExercises(), schedule.getDate());
        for (Exercise exercise : scheduledExercises) {
            if (!exerciseBook.hasResource(exercise)) {
                exerciseBook.addResource(exercise);
            }
        }
    }

    @Override
    public ReadOnlyResourceBook<Schedule> getAllScheduleData() {
        return scheduleBook;
    }

    //===================Conflicts===============================================================

    @Override
    public void resolveConflict(Name regimeName, List<Index> indexFromSchedule, List<Index> indexFromConflict) {
        requireAllNonNull(regimeName, indexFromSchedule, indexFromConflict);
        requireMainAppState(State.IN_CONFLICT);

        removeOldSchedule();

        if (areListsEmpty(indexFromConflict, indexFromSchedule)) {
            Regime regime = new Regime(regimeName, new UniqueResourceList<>());
            addResolvedSchedule(conflict.getScheduleByRegime(regime));
        } else {
            UniqueResourceList<Exercise> resolvedExercises =
                getResolvedExerciseList(indexFromSchedule, indexFromConflict);
            Schedule resolvedSchedule = getResolvedSchedule(regimeName, resolvedExercises);
            addResolvedSchedule(resolvedSchedule);
            addCombinedRegime(resolvedSchedule.getRegime());
        }
    }

    @Override
    public Conflict getConflict() {
        requireMainAppState(State.IN_CONFLICT);

        return conflict;
    }

    @Override
    public void setConflict(Conflict conflict) {
        requireMainAppState(State.IN_CONFLICT);
        requireNonNull(conflict);

        this.conflict = conflict;
    }

    //=========== Filtered Exercise List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Exercise} backed by the internal list of
     * {@code exerciseBook}.
     */
    @Override
    public ObservableList<Exercise> getFilteredExerciseList() {
        return filteredExercises;
    }

    @Override
    public void updateFilteredExerciseList(Predicate<Exercise> predicate) {
        requireNonNull(predicate);
        filteredExercises.setPredicate(predicate);
    }

    //=========== Filtered Regime List Accessors ===============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Regime} backed by the internal list of
     * {@code regimeBook}.
     */
    public ObservableList<Regime> getFilteredRegimeList() {
        return filteredRegimes;
    }

    @Override
    public void updateFilteredRegimeList(Predicate<Regime> predicate) {
        requireNonNull(predicate);
        filteredRegimes.setPredicate(predicate);
    }

    //=========== Filtered Schedule List Accessors ===============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Schedule} backed by the internal list of
     * {@code scheduleBook}
     */
    public ObservableList<Schedule> getFilteredScheduleList() {
        return filteredSchedules;
    }

    //=========== Property Manager Accessors =============================================================

    public PropertyBook getPropertyBook() {
        return propertyBook;
    }

    public boolean isPrefixUsed(Prefix prefix) {
        return propertyBook.isPrefixUsed(prefix);
    }

    public boolean isFullNameUsed(String fullName) {
        return propertyBook.isFullNameUsed(fullName);
    }

    public void addCustomProperty(CustomProperty customProperty) {
        propertyBook.addCustomProperty(customProperty);
    }

    public void removeCustomProperty(String fullName) {
        propertyBook.removeCustomProperty(fullName);
    }

    //=========== ExerciseDatabase ===============================================================

    @Override
    public ReadOnlyResourceBook<Exercise> getDatabaseBook() {
        return databaseBook;
    }

    public ReadOnlyResourceBook<Exercise> getExerciseDatabaseData() {
        return databaseBook;
    }

    //=========== Suggested Exercise Accessors ===============================================================

    @Override
    public ObservableList<Exercise> getSuggestedExerciseList() {
        return suggestions;
    }

    @Override
    public void setSuggestions(List<Exercise> suggestions) {
        this.suggestions.setAll(suggestions);
    }

    @Override
    public void updateSuggestedExerciseList(Predicate<Exercise> predicate) {
        requireNonNull(predicate);
        List<Exercise> allSuggestions = generateAllSuggestions();
        List<Exercise> filteredSuggestions = generateAllSuggestions().filtered(predicate);
        setSuggestions(filteredSuggestions);
    }

    /**
     * Returns an unmodifiable view of the list of {@code suggestions} backed by the internal list of
     * {@code exerciseBook} and {@code databaseBook}
     */
    private ObservableList<Exercise> generateAllSuggestions() {
        ObservableList<Exercise> allSuggestions = FXCollections.observableArrayList();
        List<Exercise> trackedExercises = getExerciseBookData().getResourceList();
        List<Exercise> databaseExercises = getDatabaseBook().getResourceList();
        allSuggestions.addAll(trackedExercises);
        allSuggestions.addAll(databaseExercises);
        return allSuggestions;
    }

    @Override
    public void updateStatistic() {
        ReadOnlyResourceBook<Exercise> exercises = getExerciseBookData();
        Statistic outdatedStatistic = getStatistic();
        StatsFactory statsFactory = new StatsFactory(exercises, outdatedStatistic.getChart(),
                outdatedStatistic.getCategory(), outdatedStatistic.getStartDate(), outdatedStatistic.getEndDate());
        Statistic statistic = statsFactory.generateStatistic();
        this.statistic.resetData(statistic);
    }

    @Override
    public void setStatistic(Statistic statistic) {
        this.statistic.resetData(statistic);
    }

    @Override
    public Statistic getStatistic() {
        return statistic;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return exerciseBook.equals(other.exerciseBook)
            && regimeBook.equals(other.regimeBook)
            && scheduleBook.equals(other.scheduleBook)
            && userPrefs.equals(other.userPrefs)
            && filteredExercises.equals(other.filteredExercises)
            && filteredRegimes.equals(other.filteredRegimes)
            && filteredSchedules.equals(other.filteredSchedules)
            && databaseBook.equals(other.databaseBook)
            && suggestions.equals(other.suggestions)
            && propertyBook.equals(other.propertyBook);
    }

    private UniqueResourceList<Exercise> getResolvedExerciseList(List<Index> indexFromSchedule,
                                                                 List<Index> indexFromConflict) {
        Regime scheduledRegime = conflict.getScheduledRegime();
        Regime conflictRegime = conflict.getConflictingRegime();
        List<Exercise> exercisesToAddFromScheduled = scheduledRegime.getRegimeExercises()
            .getAllResourcesIndex(indexFromSchedule);
        List<Exercise> exercisesToAddFromConflicted = conflictRegime.getRegimeExercises()
            .getAllResourcesIndex(indexFromConflict);
        List<Exercise> resolvedExercises = append(exercisesToAddFromScheduled, exercisesToAddFromConflicted);
        UniqueResourceList<Exercise> uniqueResolveList = new UniqueResourceList<>();
        uniqueResolveList.setAll(resolvedExercises);
        return uniqueResolveList;
    }

    private Schedule getResolvedSchedule(Name regimeName, UniqueResourceList<Exercise> exerciseList) {
        Regime regime = new Regime(regimeName, exerciseList);
        return new Schedule(regime, conflict.getConflictDate());
    }

    private void removeOldSchedule() {
        scheduleBook.removeResource(conflict.getScheduled());
    }

    private void addResolvedSchedule(Schedule resolvedSchedule) {
        scheduleBook.addResource(resolvedSchedule);
    }

    private void addCombinedRegime(Regime regime) {
        regimeBook.addResource(regime);
    }

}
