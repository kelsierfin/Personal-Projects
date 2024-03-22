package core;

import core.util.FileLoader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class FileLoaderTest {

    @TempDir
    Path tempDir;

    @Test
    void loadSuccessfullyFromWellFormattedFile() throws Exception {
        Path file = tempDir.resolve("wellFormatted.txt");
        Files.writeString(file, "Goals\nRead more books,20,Education\nHabits\nRead more books,20,Education,5,Reading 30 minutes daily");

        Data data = FileLoader.load(file.toFile());
        assertNotNull(data, "Data should not be null after loading from a well-formatted file");

        // Verify that the loaded data matches expectations
        // This assertion may need to be adjusted based on your Data class's structure and methods
        assertTrue(Data.getGoalsArrayList().contains("Read more books"), "Loaded data should contain the 'Read more books' goal");
    }

    @Test
    void loadFromMalformedFile() throws Exception {
        Path file = tempDir.resolve("malformedData.txt");
        Files.writeString(file, "This is not correctly formatted data");

        Data data = FileLoader.load(file.toFile());
        assertNull(data, "Data should be null when loading from a malformed file");
    }
    @Test
    void loadFromNonExistentFile() {
        Data data = FileLoader.load(new File(tempDir.resolve("nonExistentFile.txt").toString()));
        assertNull(data, "Data should be null when attempting to load from a non-existent file");
    }

    @Test
    void loadFromIncompleteFile() throws Exception {
        Path file = tempDir.resolve("incompleteData.txt");
        Files.writeString(file, "Goals\nRead more books,20"); // Incomplete data line

        Data data = FileLoader.load(file.toFile());
        assertNull(data, "Data should be null or empty when loading from an incomplete file");
    }

}
