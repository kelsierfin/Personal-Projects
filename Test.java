import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import java.util.*;

class DataTest {

    @Test
    void testMenuWeeklyGoalCompletionRate() {
        HashMap<String, Integer> idealGoal = new HashMap<>();
        HashMap<String, Integer> habitCounts = new HashMap<>();

        idealGoal.put("Reading", 7); // Target: read 7 days a week
        habitCounts.put("Reading", 5); // Actually read 5 days

        HashMap<String, Integer> expectedRates = new HashMap<>();
        expectedRates.put("Reading", 71); // Expected completion rate

        HashMap<String, Integer> rates = Data.menuWeeklyGoalCompletionRate(idealGoal, habitCounts, false);

        assertEquals(expectedRates, rates);
    }
    @Test
    void testMenuTop3Goals() {
        HashMap<String, Integer> idealGoal = new HashMap<>();
        HashMap<String, Integer> habitCounts = new HashMap<>();

        // Adding sample data
        idealGoal.put("Reading", 7);
        habitCounts.put("Reading", 7);

        idealGoal.put("Exercise", 5);
        habitCounts.put("Exercise", 5);

        idealGoal.put("Meditation", 7);
        habitCounts.put("Meditation", 3);

        idealGoal.put("Journaling", 7);
        habitCounts.put("Journaling", 6);

        String expectedOutput = "Top 3 Goals for this week are Reading, Exercise, Journaling in descending order.";
        String output = Data.menuTop3Goals(idealGoal, habitCounts);

        assertEquals(expectedOutput, output.trim());
    }
    @Test
    void testMenuDeleteData() {
        // Assuming GoalAndIdealCount and GoalHabitSetup are public for test, or use reflection if they are private
        Data.GoalAndIdealCount.put("Reading", 7);
        Data.GoalHabitSetup.add(new Object[]{"Reading", new ArrayList<>()});

        String expectedMessage = "Your account data has been reset.";
        String message = Data.menuDeleteData();

        assertTrue(Data.GoalAndIdealCount.isEmpty());
        assertTrue(Data.GoalHabitSetup.isEmpty());
        assertEquals(expectedMessage, message);
    }


}
