import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Menu2Test {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void testTop3Habits1() {
        HashMap<String, Integer> goalPoints = new HashMap<>();
        goalPoints.put("Reading", 10);
        goalPoints.put("Swimming", 5);
        goalPoints.put("Editing", 7);

        HashMap<String, Integer> earnedPoints = new HashMap<>();
        earnedPoints.put("Reading", 8);
        earnedPoints.put("Swimming", 2);
        earnedPoints.put("Editing", 7); // This makes Editing 100% completed.

        // Call the method
        Menu2.top3Habits(goalPoints, earnedPoints);

        // Correct the expected output based on the completion rates
        String expectedOutput = "Top 3 habits for this week are Editing, Reading, Swimming in descending order.";
        assertTrue(outputStreamCaptor.toString().trim().contains(expectedOutput));
    }


    @Test
    void testTop3Habits2() {
        // Your existing second test case remains unchanged
    }

    @Test
    void testTop3HabitsWithLessThanThree() {
        HashMap<String, Integer> goalPoints = new HashMap<>();
        goalPoints.put("Reading", 20);
        goalPoints.put("Swimming", 10);

        HashMap<String, Integer> earnedPoints = new HashMap<>();
        earnedPoints.put("Reading", 15);
        earnedPoints.put("Swimming", 5);

        // Call the method
        Menu2.top3Habits(goalPoints, earnedPoints);

        // Verify the output
        String expectedOutput = "Mostly completed habits are Reading, Swimming in descending order.";
        assertTrue(outputStreamCaptor.toString().trim().contains(expectedOutput));
    }

    @Test
    void testTop3HabitsWithTiedCompletionRates() {
        HashMap<String, Integer> goalPoints = new HashMap<>();
        goalPoints.put("Reading", 30);
        goalPoints.put("Swimming", 20);
        goalPoints.put("Editing", 30);

        HashMap<String, Integer> earnedPoints = new HashMap<>();
        earnedPoints.put("Reading", 15); // 50%
        earnedPoints.put("Swimming", 10); // 50%
        earnedPoints.put("Editing", 15); // 50%

        // Call the method
        Menu2.top3Habits(goalPoints, earnedPoints);

        // Verify that all three habits are mentioned without specifying the order due to tied rates
        String expectedOutput = "Top 3 habits for this week are ";
        assertTrue(outputStreamCaptor.toString().trim().contains(expectedOutput));
        assertTrue(outputStreamCaptor.toString().trim().contains("Reading"));
        assertTrue(outputStreamCaptor.toString().trim().contains("Swimming"));
        assertTrue(outputStreamCaptor.toString().trim().contains("Editing"));
    }

    @Test
    void testCompletionRates() {
        HashMap<String, Integer> goalPoints = new HashMap<>();
        goalPoints.put("Reading", 100);
        goalPoints.put("Swimming", 50);
        goalPoints.put("Editing", 75);

        HashMap<String, Integer> earnedPoints = new HashMap<>();
        earnedPoints.put("Reading", 50); // 50%
        earnedPoints.put("Swimming", 25); // 50%
        earnedPoints.put("Editing", 37); // 49%

        // Call the method without printing the rates
        HashMap<String, Integer> completionRates = Menu2.calculateCompletionRates(goalPoints, earnedPoints, false);

        // Verify the completion rates
        assertEquals(50, completionRates.get("Reading"));
        assertEquals(50, completionRates.get("Swimming"));
        assertEquals(49, completionRates.get("Editing"));
    }

    @Test
    void testCompletionRateWithZeroCompletion() {
        HashMap<String, Integer> goalPoints = new HashMap<>();
        goalPoints.put("Reading", 100);

        HashMap<String, Integer> earnedPoints = new HashMap<>();
        earnedPoints.put("Reading", 0); // 0%

        // Call the method without printing the rates
        HashMap<String, Integer> completionRates = Menu2.calculateCompletionRates(goalPoints, earnedPoints, false);

        // Verify the completion rate for 0% completion
        assertEquals(0, completionRates.get("Reading"));
    }

    @Test
    void testCompletionRateWithZeroGoal() {
        HashMap<String, Integer> goalPoints = new HashMap<>();
        goalPoints.put("Reading", 0); // Goal is 0, should handle division by zero

        HashMap<String, Integer> earnedPoints = new HashMap<>();
        earnedPoints.put("Reading", 10); // Attempted to earn points against a 0 goal

        // Call the method without printing the rates
        HashMap<String, Integer> completionRates = Menu2.calculateCompletionRates(goalPoints, earnedPoints, false);

        // Verify that the method handles 0 goal gracefully
        // Assuming the desired behavior is to treat 0 goal as 0% completion or some other logic
        assertEquals(0, completionRates.get("Reading")); // Adjust based on your method's intended behavior for 0 goals
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}
