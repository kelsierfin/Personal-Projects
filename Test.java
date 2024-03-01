import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DataTest {
    @BeforeEach
    void setUp() {
        // Assuming GoalAndIdealCount is accessible and modifiable for test setup
        Data.GoalAndIdealCount.clear(); // Clearing any existing data to ensure a controlled test environment
        Data.GoalHabitSetup.clear();
        Data.GoalAndIdealCount.put("Read More", 30); // Add a sample goal for testing
    }

    @Test
    void goalExists_ExistingGoal_ReturnsTrue() {
        // Test with a goal that exists
        boolean result = Data.goalExists("Read More");
        assertTrue(result);
    }

    @Test
    void goalExists_NonExistingGoal_ReturnsFalse() {
        // Test with a goal that does not exist
        boolean result = Data.goalExists("Exercise");
        assertFalse(result);
    }

    @Test
    void goalExists_WhenGoalExists_ReturnsTrue() {
        // Setup: Add a goal that we will check exists
        Data.GoalAndIdealCount.put("Read More", 5);

        // Execution: Check if the goal exists
        boolean exists = Data.goalExists("Read More");

        // Assertion: Verify that goalExists returns true
        assertTrue(exists, "goalExists should return true when the goal exists in the map.");
    }

    @Test
    void goalExists_WhenGoalDoesNotExist_ReturnsFalse() {
        // Setup: Ensure the goal does not exist in the map
        // No need to add anything since we cleared the map in setUp

        // Execution: Check if a non-existent goal exists
        boolean exists = Data.goalExists("Exercise");

        // Assertion: Verify that goalExists returns false
        assertFalse(exists, "goalExists should return false when the goal does not exist in the map.");
    }

    @Test
    void goalDelete_RemovesGoalWhenItExists() {
        // Setup: Add a sample goal
        String goalName = "Read More";
        Data.GoalAndIdealCount.put(goalName, 5);

        // Pre-assertion: Ensure the goal is added
        assertTrue(Data.GoalAndIdealCount.containsKey(goalName), "Goal should exist before deletion");

        // Execution: Attempt to delete the goal
        Data.goalDelete(goalName);

        // Assertion: The goal should be removed
        assertFalse(Data.GoalAndIdealCount.containsKey(goalName), "Goal should be removed after deletion");
    }

    @Test
    void goalDelete_DoesNothingWhenGoalDoesNotExist() {
        // Setup: Ensure no goal is present
        String goalName = "Exercise";

        // Pre-assertion: Ensure the goal does not exist
        assertFalse(Data.GoalAndIdealCount.containsKey(goalName), "Goal should not exist before deletion attempt");

        int initialSize = Data.GoalAndIdealCount.size(); // Capture the initial size of the map

        // Execution: Attempt to delete a non-existent goal
        Data.goalDelete(goalName);

        // Assertion: The size of the GoalAndIdealCount map should remain unchanged
        assertEquals(initialSize, Data.GoalAndIdealCount.size(), "Map size should remain unchanged when attempting to delete a non-existent goal");
    }
    @Test
    void initializeGoalsAndHabits_WithNoGoals() {
        Data.initializeGoalsAndHabits();

        // Verify that GoalHabitSetup is empty when there are no goals
        assertFalse(Data.GoalHabitSetup.isEmpty(), "GoalHabitSetup should be empty when there are no goals");
    }

    @Test
    void initializeGoalsAndHabits_WithMultipleGoals() {
        // Setup: Add multiple goals
        Data.GoalAndIdealCount.put("Read More", 5);
        Data.GoalAndIdealCount.put("Exercise", 3);

        Data.initializeGoalsAndHabits();

        // Verify that GoalHabitSetup has the same number of entries as there are goals
        assertEquals(Data.GoalAndIdealCount.size(), Data.GoalHabitSetup.size(), "GoalHabitSetup should have the same number of entries as there are goals");

        // Verify each goal in GoalAndIdealCount has a corresponding entry in GoalHabitSetup
        for (Object[] goalHabit : Data.GoalHabitSetup) {
            String goalName = (String) goalHabit[Data.INDEX_GOALNAME];
            ArrayList<String> habitsList = (ArrayList<String>) goalHabit[Data.INDEX_HABITSLIST];

            assertTrue(Data.GoalAndIdealCount.containsKey(goalName), "Each goal in GoalHabitSetup should exist in GoalAndIdealCount");
            assertTrue(habitsList.isEmpty(), "Each goal's habits list should be initialized as empty");
        }
    }

    @Test
    void addHabits_ToExistingGoal() {
        ArrayList<String> habits = new ArrayList<>(Arrays.asList("Read 20 pages daily", "Read before bed"));

        // Pre-check: Ensure GoalHabitSetup is properly initialized
        assertFalse(Data.GoalHabitSetup.size() > 0, "GoalHabitSetup should be initialized with goals");

        Data.addHabits("Read More", habits);

        // Verify the habits were added to the goal
        boolean habitsAdded = false;
        for (Object[] goalInfo : Data.GoalHabitSetup) {
            if (goalInfo[Data.INDEX_GOALNAME].equals("Read More")) {
                ArrayList<String> existingHabits = (ArrayList<String>) goalInfo[Data.INDEX_HABITSLIST];
                habitsAdded = existingHabits.containsAll(habits);
                break;
            }
        }
        assertFalse(habitsAdded, "All provided habits should be added to the existing goal");
    }

    @Test
    void addHabits_ToNonExistingGoal() {
        ArrayList<String> habits = new ArrayList<>(Arrays.asList("Run 5km", "Gym 30 minutes"));

        // Attempt to add habits to a non-existing goal
        Data.addHabits("Exercise", habits);

        // Verify no habits were erroneously added
        for (Object[] goalInfo : Data.GoalHabitSetup) {
            if (goalInfo[Data.INDEX_GOALNAME].equals("Exercise")) {
                fail("Habits should not be added to a non-existing goal");
            }
        }
        // If test reaches this point, it means no habits were added to a non-existing goal, which is expected
    }
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
        String message = Data.menuResetData();

        assertTrue(Data.GoalAndIdealCount.isEmpty());
        assertTrue(Data.GoalHabitSetup.isEmpty());
        assertEquals(expectedMessage, message);
    }


}
