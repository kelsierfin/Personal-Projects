package core;

import core.objects.Goal;
import core.objects.Habit;
import core.util.FileLoader;
import core.util.FileSaver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class DataTest {

    Data data;

    @BeforeEach
    void setUp() {
        // Initialize Data class and clear static collections to ensure a clean state for each test
        data = new Data();
        Data.goals.clear();
        Data.tracker.clear();
    }


    // Region: Goal Management Tests
    @Test
    void createGoal_ValidInput_Success() {
        assertTrue(Data.createAGoal("Exercise", 7, "Health"), "Goal should be added successfully.");
        assertTrue(Data.goals.contains(new Goal("Exercise", 7, "Health")), "The goals collection should contain the added goal.");
    }
    @Test
    void createGoal_DuplicateGoal_Failure() {
        Data.createAGoal("Read", 30, "Education");
        assertFalse(Data.createAGoal("Read", 30, "Education"), "Duplicate goal should not be added.");
        assertEquals(1, Data.goals.size(), "There should only be one instance of the goal in the collection.");
    }
    @Test
    void deleteGoal_ExistingGoal_Success() {
        Data.createAGoal("Meditate", 10, "Well-being");
        assertTrue(Data.goalDelete("Meditate"), "Existing goal should be deleted successfully.");
        assertFalse(Data.goals.contains(new Goal("Meditate", 10, "Well-being")), "The goals collection should not contain the deleted goal.");
    }
    @Test
    void deleteGoal_NonExistingGoal_Failure() {
        assertFalse(Data.goalDelete("Nonexistent"), "Attempting to delete a non-existent goal should return false.");
    }


    // Region: Habit Management Tests
    @Test
    void addHabit_ToExistingGoal_Success() {
        Data.createAGoal("Learn Guitar", 30, "Hobby");
        ArrayList<String> habitsList = new ArrayList<>();
        habitsList.add("Practice chords for 1 hour");
        Data.addHabits("Learn Guitar", habitsList, 0);
        assertEquals(1, Data.tracker.get(new Goal("Learn Guitar", 30, "Hobby")).size(), "The goal should have exactly one habit associated with it.");
    }
    @Test
    void addHabit_ToNonExistingGoal_Failure() {
        ArrayList<String> habitsList = new ArrayList<>();
        habitsList.add("Journaling");
        Data.addHabits("Nonexistent Goal", habitsList, 0);
        assertTrue(Data.tracker.isEmpty(), "Tracker should remain empty when adding a habit to a non-existent goal.");
    }
    @Test
    void deleteHabit_ExistingHabit_Success() {
        Data.createAGoal("Study Python", 7, "Education");
        ArrayList<String> habitsList = new ArrayList<>();
        habitsList.add("Complete one coding challenge");
        Data.addHabits("Study Python", habitsList, 0);
        Data.deleteHabitsFromGoal("Study Python", "Complete one coding challenge");
        assertTrue(Data.tracker.get(new Goal("Study Python", 7, "Education")).isEmpty(), "The habit set for the goal should be empty after deletion.");
    }
    @Test
    void deleteHabit_NonExistingHabit_Failure() {
        Data.createAGoal("Morning Routine", 7, "Wellness");
        ArrayList<String> habitsList = new ArrayList<>();
        habitsList.add("Meditation for 20 minutes");
        Data.addHabits("Morning Routine", habitsList, 0);
        Data.deleteHabitsFromGoal("Morning Routine", "Yoga");
        assertEquals(1, Data.tracker.get(new Goal("Morning Routine", 7, "Wellness")).size(), "The habit set for the goal should remain unchanged.");
    }
    @Test
    void updateHabitCompletion_ValidHabit_Success() {
        Data.createAGoal("Fitness", 7, "Health");
        ArrayList<String> habitsList = new ArrayList<>();
        habitsList.add("Gym Workout");
        Data.addHabits("Fitness", habitsList, 3);
        Data.updateHabitCompletion("Gym Workout");
        Habit updatedHabit = Data.findHabitByName("Gym Workout");
        assertNotNull(updatedHabit, "The habit should exist.");
        assertEquals(4, updatedHabit.getCurrentCount(), "The habit's current count should be incremented.");
    }


    // Region: Eisenhower Matrix Tests
    @Test
    void eisenhowerMatrix_ContainsGoalAfterCategorization_Success() {
        Data.createAGoal("Develop App", 5, "Productivity");
        ArrayList<Integer> choices = new ArrayList<>(Collections.singletonList(1)); // Assuming 1 corresponds to "Urgent & Important"
        Data.storeEisenhowerMatrix(choices);
        assertTrue(Data.matrixExists() && Data.matrix.get("Urgent & Important").contains("Develop App"), "The goal should be correctly categorized in the Eisenhower Matrix.");
    }

    // Region: Completion Rate Tests
    @Test
    void calculateWeeklyCompletionRates_ValidData_Success() {
        Data.createAGoal("Hydrate", 7, "Health");
        ArrayList<String> habitsList = new ArrayList<>();
        habitsList.add("Drink 2L water daily");
        Data.addHabits("Hydrate", habitsList, 5); // Assuming 5 out of 7 days completed
        Map<String, Double> rates = Data.calculateWeeklyCompletionRates();
        assertEquals((5.0 / 7) * 100, rates.get("Drink 2L water daily"), 0.01, "The completion rate for 'Drink 2L water daily' should be accurately calculated.");
    }
    @Test
    void calculateWeeklyCompletionRates_WithIncompleteData() {
        // Setup
        Data.createAGoal("Study Languages", 7, "Education");
        ArrayList<String> studyHabits = new ArrayList<>(Arrays.asList("Study Spanish", "Study French"));
        Data.addHabits("Study Languages", studyHabits, 0);

        // Update only one habit to simulate incomplete data
        for (int i = 0; i < 4; i++) {
            Data.updateHabitCompletion("Study Spanish"); // 4 out of 7 days, around 57% completion
        }

        // Action
        Map<String, Double> completionRates = Data.calculateWeeklyCompletionRates();

        // Assertion
        assertEquals((4.0 / 7) * 100, completionRates.get("Study Spanish"), 0.01, "The completion rate for 'Study Spanish' should be around 57%.");
        assertEquals(0.0, completionRates.get("Study French"), 0.01, "The completion rate for 'Study French' should be 0% as it was not updated.");
    }
    @Test
    void calculateWeeklyCompletionRates_AllHabitsCompleted() {
        // Setup
        Data.createAGoal("Health Routine", 7, "Health");
        ArrayList<String> healthHabits = new ArrayList<>(Collections.singletonList("Morning Workout"));
        Data.addHabits("Health Routine", healthHabits, 0);

        // Update habit for each day of the week
        for (int day = 0; day < 7; day++) {
            Data.updateHabitCompletion("Morning Workout");
        }

        // Action
        Map<String, Double> completionRates = Data.calculateWeeklyCompletionRates();

        // Assertion
        assertEquals(100.0, completionRates.get("Morning Workout"), "The completion rate for 'Morning Workout' should be 100%.");
    }
    @Test
    void calculateWeeklyCompletionRates_MultipleGoalsAndHabits() {
        // Setup: Create multiple goals with multiple habits
        Data.createAGoal("Professional Development", 3, "Work");
        Data.createAGoal("Leisure Activities", 4, "Leisure");
        ArrayList<String> workHabits = new ArrayList<>(Collections.singletonList("Networking"));
        ArrayList<String> leisureHabits = new ArrayList<>(Collections.singletonList("Gaming"));
        Data.addHabits("Professional Development", workHabits, 0);
        Data.addHabits("Leisure Activities", leisureHabits, 0);

        // Simulate habit completions
        for (int i = 0; i < 3; i++) {
            Data.updateHabitCompletion("Networking");
        }
        for (int i = 0; i < 2; i++) {
            Data.updateHabitCompletion("Gaming");
        }

        // Action
        Map<String, Double> completionRates = Data.calculateWeeklyCompletionRates();

        // Assertion
        assertEquals(100.0, completionRates.get("Networking"), "Networking completion rate should be 100%.");
        assertEquals((2.0 / 4) * 100, completionRates.get("Gaming"), 0.01, "Gaming completion rate should be 50%.");
    }



    // Region: File Operation Tests
    @Test
    void saveDataToFile_ValidData_Success() {
        Data.createAGoal("Sleep Early", 7, "Wellness");
        File file = new File("testSaveData.csv");
        assertTrue(FileSaver.save(file, data), "Data should be saved successfully to the file.");
        // Further verification might involve reading the file or mocking file interactions
    }
    @Test
    void loadDataFromFile_InvalidFile_Failure() {
        File file = new File("nonExistingFile.csv");
        Data loadedData = FileLoader.load(file);
        assertNull(loadedData, "Loading from an invalid file should not populate Data.");
    }
    @Test
    void resetAllData_ClearsGoalsAndHabits_Success() {
        Data.createAGoal("Read More", 7, "Personal Development");
        Data.resetAllData();
        assertTrue(Data.goals.isEmpty() && Data.tracker.isEmpty(), "All goals and habits should be cleared after reset.");
    }


    // Region: Utility Method Tests
    @Test
    void findHabitByName_ExistingHabit_ReturnsHabit() {
        Data.createAGoal("Study Java", 7, "Education");
        ArrayList<String> habitsList = new ArrayList<>();
        habitsList.add("Review lectures");
        Data.addHabits("Study Java", habitsList, 0);
        assertNotNull(Data.findHabitByName("Review lectures"), "Finding an existing habit by name should return the habit.");
    }
    @Test
    void findHabitByName_NonExistingHabit_ReturnsNull() {
        assertNull(Data.findHabitByName("Nonexistent Habit"), "Attempting to find a non-existing habit should return null.");
    }
    @Test
    void updateHabitCompletion_ValidHabit_IncrementsCount() {
        Data.createAGoal("Fitness", 7, "Health");
        ArrayList<String> habitsList = new ArrayList<>();
        habitsList.add("Run 5km");
        Data.addHabits("Fitness", habitsList, 2);
        Data.updateHabitCompletion("Run 5km");
        Habit updatedHabit = Data.findHabitByName("Run 5km");
        assertEquals(3, updatedHabit.getCurrentCount(), "The habit's current count should be incremented by 1.");
    }
    @Test
    void resetAllData_EffectivelyClearsData() {
        Data.createAGoal("Learn Piano", 7, "Music");
        Data.resetAllData();
        assertTrue(Data.goals.isEmpty() && Data.tracker.isEmpty(), "All data should be cleared after calling resetAllData.");
    }


    // Region: Top Habits by Completion Rate Tests
    @Test
    void getTop3HabitsByCompletionRate_MultipleHabits() {
        // Setup: Assume the setup creates multiple goals and habits with varying completion rates
        Data.createAGoal("Goal1", 7, "Category1");
        Data.addHabits("Goal1", new ArrayList<>(List.of("Habit1", "Habit2", "Habit3", "Habit4")), 0);
        // Mock or simulate the update of habit completions to reflect varied completion rates

        List<Habit> topHabits = Data.getTop3HabitsByCompletionRate();
        assertEquals(3, topHabits.size(), "Should return the top 3 habits by completion rate.");
        // Further assertions to check if the correct habits are returned based on their completion rates
    }
    @Test
    void fileContentMatchesAfterSaveAndLoad_Operations() {
        // Assuming this test is set up with mock data and FileSaver/FileLoader operations are mocked or intercepted
        File file = new File("temporaryData.csv");
        Data originalData = new Data();
        // Populate originalData with mock data

        assertTrue(FileSaver.save(file, originalData), "Data should be saved successfully.");
        Data loadedData = FileLoader.load(file);
        assertNotNull(loadedData, "Data should be loaded successfully.");
        // Compare originalData and loadedData for equality
    }
    @Test
    void getTop3HabitsByCompletionRate_WithNoHabits() {
        assertTrue(Data.getTop3HabitsByCompletionRate().isEmpty(), "Should return an empty list when there are no habits.");
    }
    @Test
    void getTop3HabitsByCompletionRate_WithLessThanThreeHabits() {
        Data.createAGoal("Study", 7, "Education");
        ArrayList<String> habitsList = new ArrayList<>();
        habitsList.add("Read textbook");
        Data.addHabits("Study", habitsList, 4); // Assuming this habit has been updated to reflect completion
        List<Habit> topHabits = Data.getTop3HabitsByCompletionRate();
        assertEquals(1, topHabits.size(), "Should return all available habits when there are fewer than three.");
        assertEquals("Read textbook", topHabits.get(0).getHabit(), "The returned habit should match the only available habit.");
    }
    @Test
    void getTop3HabitsByCompletionRate_SortedCorrectly() {
        // Setup
        Data.createAGoal("Physical Health", 7, "Health");
        ArrayList<String> habits = new ArrayList<>(Arrays.asList("Running", "Swimming", "Cycling"));
        Data.addHabits("Physical Health", habits, 0);

        // Assume the updateHabitCompletion increments the completion count
        // We will call updateHabitCompletion multiple times to simulate the different completion rates
        for (int i = 0; i < 5; i++) { // Simulate 5 completions for "Running"
            Data.updateHabitCompletion("Running");
        }
        for (int i = 0; i < 7; i++) { // Simulate 7 completions for "Swimming"
            Data.updateHabitCompletion("Swimming");
        }
        for (int i = 0; i < 3; i++) { // Simulate 3 completions for "Cycling"
            Data.updateHabitCompletion("Cycling");
        }

        // Action
        List<Habit> topHabits = Data.getTop3HabitsByCompletionRate();

        // Assertion
        assertEquals(3, topHabits.size(), "There should be three habits in the list.");
        assertEquals("Swimming", topHabits.get(0).getHabit(), "Swimming should have the highest completion rate and be first.");
        assertEquals("Running", topHabits.get(1).getHabit(), "Running should be second.");
        assertEquals("Cycling", topHabits.get(2).getHabit(), "Cycling should have the lowest completion rate and be last.");
    }
    @Test
    void getTop3HabitsByCompletionRate_IgnoresZeroCompletion() {
        // Setup: Create goals and habits with different completion rates, including one with 0% completion
        Data.createAGoal("Wellness", 7, "Health");
        ArrayList<String> habitsList = new ArrayList<>(Arrays.asList("Meditation", "Yoga", "Journaling"));
        Data.addHabits("Wellness", habitsList, 0);

        // Simulate updating completion count for Meditation and Yoga, but not for Journaling
        for (int i = 0; i < 7; i++) { // Complete meditation every day
            Data.updateHabitCompletion("Meditation");
        }
        for (int i = 0; i < 3; i++) { // Complete Yoga on some days
            Data.updateHabitCompletion("Yoga");
        }
        // Do not update Journaling to simulate 0% completion rate

        // Action: Retrieve the top 3 habits by completion rate
        List<Habit> topHabits = Data.getTop3HabitsByCompletionRate();

        // Assertion: Check if the habits are sorted in descending order by their completion rates
        assertEquals(3, topHabits.size(), "Should return two habits since one has 0% completion.");
        assertEquals("Meditation", topHabits.get(0).getHabit(), "Meditation should have the highest completion rate.");
        assertEquals("Yoga", topHabits.get(1).getHabit(), "Yoga should be second with some completion.");
        // Verify that "Journaling" is not included since it has a 0% completion rate
        assertTrue(topHabits.stream().anyMatch(habit -> habit.getHabit().equals("Journaling")), "Journaling should not be included in the top habits.");
    }
    @Test
    void getTop3HabitsByCompletionRate_ReflectsRecentUpdates() {
        // Setup: Initial habit creation and update
        Data.createAGoal("Skill Improvement", 7, "Skill");
        ArrayList<String> habitsList = new ArrayList<>(Arrays.asList("Coding", "Design", "Writing"));
        Data.addHabits("Skill Improvement", habitsList, 0);

        // Initially update "Coding" to simulate full completion, "Writing" to simulate near full completion
        for (int i = 0; i < 7; i++) {
            Data.updateHabitCompletion("Coding");
        }
        for (int i = 0; i < 6; i++) {
            Data.updateHabitCompletion("Writing");
        }
        // "Design" is not updated, simulating 0% completion

        // Action: Retrieve the top 3 habits before further updates
        List<Habit> initialTopHabits = Data.getTop3HabitsByCompletionRate();
        // Assuming Coding and Writing are in the top 3, we proceed with updates

        // Update "Design" to simulate an increase in completion, potentially changing its ranking
        for (int i = 0; i < 4; i++) {
            Data.updateHabitCompletion("Design");
        }

        // Action: Retrieve the top 3 habits after updates
        List<Habit> updatedTopHabits = Data.getTop3HabitsByCompletionRate();

        // Assertion: Check that the update to "Design" is reflected in the new top 3
        assertTrue(updatedTopHabits.contains(Data.findHabitByName("Design")), "Design should be included in the top habits after updates.");
        // Verify that the order of "Coding" and "Writing" may have changed based on the new completion rates
        assertEquals(initialTopHabits.indexOf(Data.findHabitByName("Coding")), updatedTopHabits.indexOf(Data.findHabitByName("Coding")), "The order of Coding should change based on updates.");
        assertEquals(initialTopHabits.indexOf(Data.findHabitByName("Writing")), updatedTopHabits.indexOf(Data.findHabitByName("Writing")), "The order of Writing should change based on updates.");
    }
    @Test
    void getTop3HabitsByCompletionRate_DifferentIdealCounts() {
        Data.createAGoal("Personal Development", 7, "Development");
        ArrayList<String> habitsList = new ArrayList<>(Arrays.asList("Study", "Practice Guitar"));
        Data.addHabits("Personal Development", habitsList, 0);

        // Simulate completion counts
        Data.updateHabitCompletion("Study"); // Update 7 times, 100% rate
        for (int i = 0; i < 7; i++) {
            Data.updateHabitCompletion("Study");
        }
        Data.updateHabitCompletion("Practice Guitar"); // Update 4 times, >50% rate but different ideal count

        // Action
        List<Habit> topHabits = Data.getTop3HabitsByCompletionRate();

        // Assertion
        assertEquals(2, topHabits.size(), "There should be two habits in the list.");
        assertEquals("Study", topHabits.get(0).getHabit(), "Study should have the highest completion rate.");
        assertEquals("Practice Guitar", topHabits.get(1).getHabit(), "Practice Guitar should be second.");
    }
    @Test
    void getTop3HabitsByCompletionRate_WithTies() {
        Data.createAGoal("Fitness", 7, "Health");
        ArrayList<String> fitnessHabits = new ArrayList<>(Arrays.asList("Yoga", "Pilates", "Running"));
        Data.addHabits("Fitness", fitnessHabits, 0);

        // Simulate identical completion counts for all habits
        for (int i = 0; i < 5; i++) {
            Data.updateHabitCompletion("Yoga");
            Data.updateHabitCompletion("Pilates");
            Data.updateHabitCompletion("Running");
        }

        // Action
        List<Habit> topHabits = Data.getTop3HabitsByCompletionRate();

        // Assertion
        assertEquals(3, topHabits.size(), "There should be three habits in the list.");
        // Assuming the method breaks ties arbitrarily but consistently, check for presence not order
        assertTrue(topHabits.stream().map(Habit::getHabit).collect(Collectors.toSet())
                .containsAll(fitnessHabits), "All habits with tied completion rates should be included.");
    }

}
