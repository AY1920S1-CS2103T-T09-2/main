package seedu.exercise.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.exercise.commons.core.LogsCenter;
import seedu.exercise.commons.exceptions.DataConversionException;
import seedu.exercise.model.ReadOnlyExerciseBook;
import seedu.exercise.model.ReadOnlyRegimeBook;
import seedu.exercise.model.ReadOnlyUserPrefs;
import seedu.exercise.model.UserPrefs;

/**
 * Manages storage of ExerciseBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ExerciseBookStorage exerciseBookStorage;
    private ExerciseBookStorage allExerciseBookStorage;
    private RegimeBookStorage regimeBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(ExerciseBookStorage exerciseBookStorage, RegimeBookStorage regimeBookStorage,
                          ExerciseBookStorage allExerciseBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.exerciseBookStorage = exerciseBookStorage;
        this.allExerciseBookStorage = allExerciseBookStorage;
        this.regimeBookStorage = regimeBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ ExerciseBook methods ==============================

    @Override
    public Path getExerciseBookFilePath() {
        return exerciseBookStorage.getExerciseBookFilePath();
    }

    @Override
    public Optional<ReadOnlyExerciseBook> readExerciseBook() throws DataConversionException, IOException {
        return readExerciseBook(exerciseBookStorage.getExerciseBookFilePath());
    }

    @Override
    public Optional<ReadOnlyExerciseBook> readExerciseBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return exerciseBookStorage.readExerciseBook(filePath);
    }

    @Override
    public void saveExerciseBook(ReadOnlyExerciseBook exerciseBook) throws IOException {
        saveExerciseBook(exerciseBook, exerciseBookStorage.getExerciseBookFilePath());
    }

    @Override
    public void saveExerciseBook(ReadOnlyExerciseBook exerciseBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        exerciseBookStorage.saveExerciseBook(exerciseBook, filePath);
    }

    // ================ AllExerciseBook methods ==============================

    @Override
    public Path getAllExerciseBookFilePath() {
        return allExerciseBookStorage.getExerciseBookFilePath();
    }

    @Override
    public Optional<ReadOnlyExerciseBook> readAllExerciseBook() throws DataConversionException, IOException {
        return readExerciseBook(allExerciseBookStorage.getExerciseBookFilePath());
    }

    @Override
    public Optional<ReadOnlyExerciseBook> readAllExerciseBook(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return allExerciseBookStorage.readExerciseBook();
    }

    //===============RegimeBook methods=============================================
    @Override
    public Path getRegimeBookFilePath() {
        return regimeBookStorage.getRegimeBookFilePath();
    }

    @Override
    public Optional<ReadOnlyRegimeBook> readRegimeBook() throws DataConversionException, IOException {
        return readRegimeBook(regimeBookStorage.getRegimeBookFilePath());
    }

    @Override
    public Optional<ReadOnlyRegimeBook> readRegimeBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return regimeBookStorage.readRegimeBook(filePath);
    }

    @Override
    public void saveRegimeBook(ReadOnlyRegimeBook regimeBook) throws IOException {
        saveRegimeBook(regimeBook, regimeBookStorage.getRegimeBookFilePath());
    }

    @Override
    public void saveRegimeBook(ReadOnlyRegimeBook regimeBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        regimeBookStorage.saveRegimeBook(regimeBook, filePath);
    }
}
