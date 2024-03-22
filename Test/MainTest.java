package core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    void testRunWithNoArguments() {
        Main.main(new String[]{});
        // Assuming your application prints a welcome message on startup, adjust as necessary.
        String welcomeMessage = "Welcome to your personal habit and goal tracker!";
        assertTrue(outContent.toString().contains(welcomeMessage));
    }

    @Test
    void testRunWithValidFileArgument() throws IOException {
        // Create a temporary file to simulate valid input
        File tempFile = File.createTempFile("validData", ".txt");
        tempFile.deleteOnExit();
        Main.main(new String[]{tempFile.getAbsolutePath()});
        // Adjust the expected output based on your application's behavior
        String expectedOutput = "Loaded data from file";
        assertTrue(outContent.toString().contains(expectedOutput) || errContent.toString().contains(expectedOutput));
    }

    @Test
    void testRunWithInvalidFileArgument() {
        Main.main(new String[]{"nonExistentFile.txt"});
        String expectedError = "Cannot load from the file"; // Adjust based on actual error message
        assertTrue(errContent.toString().contains(expectedError));
    }
}
