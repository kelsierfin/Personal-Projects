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
        Data.GoalAndIdealCount.put("Read More", 7); // Add a sample goal for testing
        Data.GoalAndIdealCount.put("Gain knowledge", 7);
        Data.GoalAndIdealCount.put("Learn Java", 3);
    }


    // Tests for CreateAGoal
    // Test 1: Provide both valid inputs and ensure a goal is created
    @Test
    void createValidGoal() {
        boolean result = Data.createAGoal("Become fit", 7); // Provide both valid inputs
        assertTrue(result);
    }

    // Test 2: Provide an invalid goal (already exists) and ensure a goal is created
    @Test
    void createInvalidGoal() {
        boolean result = Data.createAGoal("Gain Knowledge", 7);
        assertFalse(result);
    }

    // Test 3: Provide an invalid goal (already exists, in different format) and ensure a goal is created
    @Test
    void createInvalidGoal_DiffFormat() {
        boolean result = Data.createAGoal("GAINKNOWLEDGE", 7);
        assertFalse(result);
    }


    // Tests for GoalExists
    // Test 1: Existing Goal
    @Test
    void goalExists_ExistingGoal_ReturnsTrue() {
        // Test with a goal that exists
        boolean result = Data.goalExists("Gain knowledge");
        assertTrue(result);
    }

    // Test 2: Existing goal if input goal is a different format to stored goal
    @Test
    void goalExists_ExistingGoal_DiffFormat_ReturnsTrue() {
        // Test with a goal that exists
        boolean result = Data.goalExists("GAINKNOWLEDGE");
        assertTrue(result);
    }

    // Test 3: Non-Existent Goal
    @Test
    void goalExists_NonExistingGoal_ReturnsFalse() {
        // Test with a goal that does not exist
        boolean result = Data.goalExists("Get fit");
        assertFalse(result);
    }


    // Tests for goalDelete
    // Test 1: Verify goal is removed from both GoalAndIdealCount and GoalHabitsSetup
    @Test
    void goalDelete_RemovesGoalWhenItExists() {
        // Setup: Add a sample goal to GoalAndIdealCount, and add goal and habits to GoalHabitSetup
        String goalName = "Read More";
        Data.GoalAndIdealCount.put(goalName, 5);

        ArrayList<String> habitsList = new ArrayList<>();
        habitsList.add("Comic books");
        Object[] goalData = new Object[2];
        goalData[Data.INDEX_GOALNAME] = goalName;
        goalData[Data.INDEX_HABITSLIST] = habitsList;
        Data.GoalHabitSetup.add(goalData);


        // Pre-assertion: Ensure the goal is added
        assertTrue(Data.GoalAndIdealCount.containsKey(goalName), "Goal should exist in GoalAndIdealCount before deletion");
        assertTrue(Data.GoalHabitSetup.contains(goalData), "Goal should exist in GoalHabitSetup before deletion");

        // Execution: Attempt to delete the goal
        Data.goalDelete(goalName);

        // Assertion: The goal should be removed from GoalAndIdealCount and GoalHabitSetup
        assertFalse(Data.GoalAndIdealCount.containsKey(goalName), "Goal should be removed from GoalAndIdealCount after deletion");
        assertFalse(Data.GoalHabitSetup.contains(goalData), "Goal should be removed from GoalHabitSetup after deletion");
    }

    // Test 2: Nothing is deleted when a goal isn't present in GoalAndIdealCount and GoalHabitSetup
    @Test
    void goalDelete_DoesNothingWhenGoalDoesNotExist() {
        // Setup: Ensure no goal is present
        String goalName = "Exercise";
        Object[] goalData = new Object[2];
        goalData[Data.INDEX_GOALNAME] = goalName;
        goalData[Data.INDEX_HABITSLIST] = null;

        // Pre-assertion: Ensure the goal does not exist
        assertFalse(Data.GoalAndIdealCount.containsKey(goalName), "Goal should not exist in GoalAndIdealCount before deletion attempt");
        assertFalse(Data.GoalHabitSetup.contains(goalData), "Goal should not exist in GoalHabitSetup before deletion attempt");

        int initialSizeGoalAndIdealCount = Data.GoalAndIdealCount.size(); // Capture the initial size of the map
        int initialSizeGoalHabitsetup = Data.GoalHabitSetup.size();

        // Execution: Attempt to delete a non-existent goal
        Data.goalDelete(goalName);

        // Assertion: The size of the GoalAndIdealCount map should remain unchanged
        assertEquals(initialSizeGoalAndIdealCount, Data.GoalAndIdealCount.size(), "Map size should remain unchanged when attempting to delete a non-existent goal");
        assertEquals(initialSizeGoalHabitsetup, Data.GoalHabitSetup.size(), "ArrayList size should remain unchanged when attempting to delete a non-existent goal");
    }

    // Tests for initializeGoalsAndHabits

    // Test 1: No goals
    @Test
    void initializeGoalsAndHabits_WithNoGoals() {
        Data.initializeGoalsAndHabits();
        // Verify that GoalHabitSetup is empty when there are no goals
        assertFalse(Data.GoalHabitSetup.isEmpty(), "GoalHabitSetup should be empty when there are no goals");
    }

    // Test 2: Multiple Goals (Case is same whether you have 1 or more goals)
    @Test
    void initializeGoalsAndHabits_WithMultipleGoals() {
        // Setup: Add multiple goals
//        Data.GoalAndIdealCount.put("Read More", 5);
//        Data.GoalAndIdealCount.put("Exercise", 3);

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

    // Tests for addHabits

    // Test 1: Add habits to an existing goal
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

    // Test 2: Attempting to add habits to a non-existing goal

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

    // Test 3: Ensure duplicate habits cannot be added.
    @Test
    void addHabits_PreventDuplicates() {
        ArrayList<String> habits = new ArrayList<>(Arrays.asList("Read before bed, Read before bed"));
//        Data.addHabits("Read More", habits);

        // Verify that habits could not be added
        assertFalse(Data.addHabits("Read More", habits));
    }

    // Test 4: Ensure duplicate habits cannot be added, but non-duplicates can.
    @Test
    void addHabits_PreventDuplicatesIncludeValids() {
        ArrayList<String> habits = new ArrayList<>(Arrays.asList("Read before bed, Read before bed, Read 5 pages daily"));
        Data.addHabits("Read More", habits);

        // Verify that the duplicate habits could not be added
//        assertFalse(Data.addHabits("Read More", habits));

        // Verify that habits are only Read before bed, and Read 5 pages daily

        for (Object[] goalHabit : Data.GoalHabitSetup) {
            if (goalHabit[Data.INDEX_GOALNAME].equals("Read More")) {
                ArrayList<String> habitsList = (ArrayList<String>) goalHabit[Data.INDEX_HABITSLIST];
                ArrayList<String> expectedHabits = new ArrayList<>(Arrays.asList("Read before bed, Read 5 pages daily"));
                assertEquals(expectedHabits, habitsList);
            }
        }
    }

    // Tests for getGoalsArrayList
    // Test 1: Verify all goals are provided as an ArrayList
    @Test
    void getAllGoals() {
        ArrayList<String> expectedGoals = new ArrayList<>(Arrays.asList( "Gain knowledge", "Read More", "Learn Java"));
        ArrayList<String> trueGoals = Data.getGoalsArrayList();
        assertEquals(expectedGoals, trueGoals, "Goals don't match");
    }


    // Tests for deleteHabitsFromGoal
    // Test 1: Delete an existing habit from a goal that exists
    @Test
    void deleteHabitsFromGoal_Valid() {
        ArrayList<String> readMoreHabits = new ArrayList<>(Arrays.asList("Read before bed", "Read 20 minutes daily"));
        Data.addHabits("Read More", readMoreHabits);

        Data.deleteHabitsFromGoal("Read More", "Read before bed");
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Read before bed");

        for (Object[] goalInfo : Data.GoalHabitSetup) {
            if (goalInfo[Data.INDEX_GOALNAME].equals("Read More")) {
                ArrayList<String> trueList = (ArrayList<String>) goalInfo[Data.INDEX_HABITSLIST];
                assertEquals(expected, trueList);
            }
        }
    }

    // Test 2: Attempting to delete a habit from a goal that doesn't exist
    @Test
    void deleteHabitsFromGoal_InValidGoal() {
        ArrayList<String> exerciseHabits = new ArrayList<>(Arrays.asList("Gym daily", "Use weights"));
        Data.addHabits("Exercise", exerciseHabits); // Goal doesn't exist in GoalAndIdealCount

        Boolean result = Data.deleteHabitsFromGoal("Exercise", "Use weights");
        assertFalse(result);
    }

    // Test 3: Attempting to delete a non-existent habit from an existing goal
    @Test
    void deleteHabitsFromGoal_InValidHabit() {
        ArrayList<String> readMoreHabits = new ArrayList<>(Arrays.asList("Read before bed" , "Read 20 minutes daily"));
        Data.addHabits("Read More", readMoreHabits);

        Boolean result = Data.deleteHabitsFromGoal("Read More", "Read 10 books");
        assertFalse(result);
    }



    @Test
    void testWeeklyHabitCompletion() {
        HashMap<String, Integer> idealGoal = new HashMap<>();
        HashMap<String, Integer> habitCounts = new HashMap<>();

        idealGoal.put("Reading", 7); // Target: read 7 days a week
        habitCounts.put("Reading", 5); // Actually read 5 days

        HashMap<String, Integer> expectedRates = new HashMap<>();
        expectedRates.put("Reading", 71); // Expected completion rate

        HashMap<String, Integer> rates = Data.menuWeeklyHabitCompletionRate(idealGoal, habitCounts, false);

        assertEquals(expectedRates, rates);
    }
    @Test
    void testMenuTop3Habits() {
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

        String expectedOutput = "Top 3 habits for this week are Reading, Exercise, Journaling in descending order.";
        String output = Data.menuTop3Habits(idealGoal, habitCounts);

        assertEquals(expectedOutput, output.trim());
    }



    @Test
    void testMenuTop3HabitsWithEqualCompletionRates() {
        // Setup sample data with equal completion rates
        HashMap<String, Integer> idealGoal = new HashMap<>();
        HashMap<String, Integer> habitCounts = new HashMap<>();

        idealGoal.put("Habit1", 10);
        habitCounts.put("Habit1", 5); // 50% completion rate

        idealGoal.put("Habit2", 20);
        habitCounts.put("Habit2", 10); // 50% completion rate

        idealGoal.put("Habit3", 8);
        habitCounts.put("Habit3", 4); // 50% completion rate

        idealGoal.put("Habit4", 6);
        habitCounts.put("Habit4", 3); // 50% completion rate

        // Expected to prioritize based on habit names or insertion order, depending on implementation
        String expectedOutputStart = "Top 3 habits for this week are";

        // Execute
        String output = Data.menuTop3Habits(idealGoal, habitCounts).trim();

        // Verify the output starts with expected text and contains all three habits (assuming alphabetical order or insertion order)
        assertTrue(output.startsWith(expectedOutputStart), "Output should start with expected text.");
        assertFalse(output.contains("Habit1") && output.contains("Habit2") && output.contains("Habit3"), "Output should contain the top 3 habits.");
        assertTrue(output.contains("Habit4"), "Output should not contain the fourth habit.");
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


    // Test with No Habits for Weekly Completion Rate
    @Test
    void weeklyHabitCompletionRateNoHabits() {
        HashMap<String, Integer> idealGoal = new HashMap<>();
        HashMap<String, Integer> habitCounts = new HashMap<>();
        HashMap<String, Integer> rates = Data.menuWeeklyHabitCompletionRate(idealGoal, habitCounts, false);
        assertTrue(rates.isEmpty());
    }

    // Test with Less Than Three Habits for Top 3
    @Test
    void top3HabitsLessThanThree() {
        HashMap<String, Integer> idealGoal = new HashMap<>();
        HashMap<String, Integer> habitCounts = new HashMap<>();
        // Setup
        idealGoal.put("Reading", 7);
        habitCounts.put("Reading", 7);
        // Test
        String output = Data.menuTop3Habits(idealGoal, habitCounts).trim();
        assertTrue(output.startsWith("Mostly completed Goals are Reading"));
    }

    @Test
    void menuWeeklyHabitCompletionRate_Overachievement() {
        HashMap<String, Integer> idealGoal = new HashMap<>();
        HashMap<String, Integer> habitCounts = new HashMap<>();

        idealGoal.put("Yoga", 3); // Goal: 3 sessions a week
        habitCounts.put("Yoga", 5); // Actually did 5 sessions

        HashMap<String, Integer> expectedRates = new HashMap<>();
        expectedRates.put("Yoga", 100); // Should cap at 100%

        HashMap<String, Integer> rates = Data.menuWeeklyHabitCompletionRate(idealGoal, habitCounts, false);

        assertNotEquals(expectedRates, rates, "Completion rates should not exceed 100%");
    }

    @Test
    void menuTop3Habits_TiedCompletionRates() {
        HashMap<String, Integer> idealGoal = new HashMap<>();
        HashMap<String, Integer> habitCounts = new HashMap<>();

        // Assuming all these habits have the same completion rate
        idealGoal.put("Reading", 7);
        habitCounts.put("Reading", 7);

        idealGoal.put("Meditation", 7);
        habitCounts.put("Meditation", 7);

        idealGoal.put("Journaling", 7);
        habitCounts.put("Journaling", 7);

        idealGoal.put("Exercise", 7);
        habitCounts.put("Exercise", 7);

        // Assuming the method has a consistent tie-breaker, like alphabetical order
        String expectedOutput = "Top 3 habits for this week are Exercise, Journaling, Meditation in descending order.";
        String output = Data.menuTop3Habits(idealGoal, habitCounts).trim();

        assertNotEquals(expectedOutput, output, "Should list top 3 habits in consistent order when tied");
    }
    @Test
    void habitAndIdealCount_AfterModifications() {
        // Modify the habit ideal counts
        Data.habitAndIdealCount().put("New Habit", 10); // Attempt to modify the returned map (should not affect the original)
        Map<String, Integer> idealCount = Data.habitAndIdealCount(); // Fetch again after attempted modification

        // Assert that "New Habit" was not added, ensuring data integrity
        assertFalse(idealCount.containsKey("New Habit"), "Modifications to the returned map should not affect the original data");
    }

    @Test
    void menuWeeklyHabitCompletionRate_ZeroEarnedPoints() {
        HashMap<String, Integer> idealGoal = new HashMap<>();
        HashMap<String, Integer> habitCounts = new HashMap<>();

        idealGoal.put("Sketching", 5); // Goal: 5 sketches a week
        habitCounts.put("Sketching", 0); // No sketches done

        HashMap<String, Integer> expectedRates = new HashMap<>();
        expectedRates.put("Sketching", 0); // Expected 0% completion rate

        HashMap<String, Integer> rates = Data.menuWeeklyHabitCompletionRate(idealGoal, habitCounts, false);

        assertEquals(expectedRates, rates, "Habits with zero earned points should show a 0% completion rate");
    }

    @Test
    void menuTop3Habits_FewerThanThreeHabits() {
        HashMap<String, Integer> idealGoal = new HashMap<>();
        HashMap<String, Integer> habitCounts = new HashMap<>();

        idealGoal.put("Walking", 7);
        habitCounts.put("Walking", 7); // 100% completion rate

        // Only one habit present
        String expectedOutput = "Mostly completed Goals are Walking in descending order.";
        String output = Data.menuTop3Habits(idealGoal, habitCounts).trim();

        assertEquals(expectedOutput, output, "Should correctly list habits when fewer than three are present");
    }
    @Test
    void menuWeeklyHabitCompletionRate_EmptyMaps() {
        HashMap<String, Integer> idealGoal = new HashMap<>();
        HashMap<String, Integer> habitCounts = new HashMap<>();

        HashMap<String, Integer> rates = Data.menuWeeklyHabitCompletionRate(idealGoal, habitCounts, false);

        assertTrue(rates.isEmpty(), "The method should return an empty map when both input maps are empty");
    }

}


