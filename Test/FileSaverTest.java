package core;

import core.util.FileSaver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FileSaverTest {

    private Data data;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        data = new Data();
        // Setup your data object with some test data
        data.createAGoal("Read more books", 10, "Education");
        HashSet<String> habits = new HashSet<>();
        habits.add("Reading 30 minutes daily");
        data.addHabits("Read more books", new ArrayList<>(habits), 5);
    }

    @Test
    void saveDataSuccessfully() throws IOException {
        File tempFile = tempDir.resolve("testData.txt").toFile();
        assertTrue(FileSaver.save(tempFile, data), "Data should be saved successfully");

        // Verify the file is not empty
        String content = Files.readString(tempFile.toPath());
        assertFalse(content.isEmpty(), "File should contain saved data");
    }

    @Test
    void saveWithNoData() throws IOException {
        Data emptyData = new Data(); // Create an instance of Data with no goals/habits
        File tempFile = tempDir.resolve("emptyData.txt").toFile();
        assertTrue(FileSaver.save(tempFile, emptyData), "Saving should succeed even with no data");

        // Verify the file has only the headers or is empty depending on implementation
        String content = Files.readString(tempFile.toPath());
        assertTrue(content.isEmpty() || content.contains("Goals") || content.contains("Habits"), "File should be empty or only contain headers");
    }

    @Test
    void saveToNonExistentDirectory() throws IOException {
        File tempFile = tempDir.resolve("nonExistentDir/testData.txt").toFile();
        boolean expectedOutcome = false; // Set to true if your implementation should create the directory and save successfully
        assertEquals(expectedOutcome, FileSaver.save(tempFile, data), "Saving to a file in a non-existent directory should match the expected outcome");
    }


    @AfterEach
    void tearDown() {
        // Clean up any resources or temporary files if needed
    }
}
