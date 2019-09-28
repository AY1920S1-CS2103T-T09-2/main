package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalExercises.CLAP;
import static seedu.address.testutil.TypicalExercises.SLAP;
import static seedu.address.testutil.TypicalExercises.WALK;
import static seedu.address.testutil.TypicalExercises.getTypicalExerciseBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ExerciseBook;
import seedu.address.model.ReadOnlyExerciseBook;

public class JsonExerciseBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonExerciseBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readExerciseBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readExerciseBook(null));
    }

    private java.util.Optional<ReadOnlyExerciseBook> readExerciseBook(String filePath) throws Exception {
        return new JsonExerciseBookStorage(Paths.get(filePath)).readExerciseBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
            ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
            : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readExerciseBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readExerciseBook("notJsonFormatExerciseBook.json"));
    }

    @Test
    public void readExerciseBook_invalidExerciseBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readExerciseBook("invalidExerciseBook.json"));
    }

    @Test
    public void readExerciseBook_invalidAndValidExerciseBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readExerciseBook("invalidAndValidExerciseBook.json"));
    }

    @Test
    public void readAndSaveExerciseBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempExerciseBook.json");
        ExerciseBook original = getTypicalExerciseBook();
        JsonExerciseBookStorage jsonAddressBookStorage = new JsonExerciseBookStorage(filePath);

        // Save in new file and read back
        jsonAddressBookStorage.saveExerciseBook(original, filePath);
        ReadOnlyExerciseBook readBack = jsonAddressBookStorage.readExerciseBook(filePath).get();
        assertEquals(original, new ExerciseBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addExercise(CLAP);
        original.removeExercise(WALK);
        jsonAddressBookStorage.saveExerciseBook(original, filePath);
        readBack = jsonAddressBookStorage.readExerciseBook(filePath).get();
        assertEquals(original, new ExerciseBook(readBack));

        // Save and read without specifying file path
        original.addExercise(SLAP);
        jsonAddressBookStorage.saveExerciseBook(original); // file path not specified
        readBack = jsonAddressBookStorage.readExerciseBook().get(); // file path not specified
        assertEquals(original, new ExerciseBook(readBack));

    }

    @Test
    public void saveExerciseBook_nullExerciseBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveExerciseBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code exerciseBook} at the specified {@code filePath}.
     */
    private void saveExerciseBook(ReadOnlyExerciseBook exerciseBook, String filePath) {
        try {
            new JsonExerciseBookStorage(Paths.get(filePath))
                .saveExerciseBook(exerciseBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveExerciseBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveExerciseBook(new ExerciseBook(), null));
    }
}
