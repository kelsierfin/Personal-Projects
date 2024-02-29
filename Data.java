import java.util.*;
public class Data {

    public static final HashMap<String, Integer> GoalAndIdealCount = new HashMap<>(); // ArrayList to store all goals and idealcounts

    public static final ArrayList<Object[]> GoalHabitSetup = new ArrayList<>(); // Contains goal, its habits and idealcount
    public static final int INDEX_GOALNAME = 0;
    //    public static final int INDEX_GOALIDEALCOUNt = 1;
    public static final int INDEX_HABITSLIST = 1;

    public static boolean createAGoal(String goalName, Integer goalIdealCount) {

        if (!goalExists(goalName)) {
            GoalAndIdealCount.put(goalName, goalIdealCount);
            System.out.printf("Goal added successfully!\nYour goal is: " + goalName + " and your ideal count is: " + goalIdealCount + "\n");
            return true;
        } else {
            System.out.println("Your goal (" + goalName + ") already exists.");
            return false;
        }
    }

    /**
     * This function checks if the goal entered already exists.
     *
     * @param goalName - name of goal to check if it exists
     * @return boolean
     * @author Tania
     */

    public static boolean goalExists(String goalName) {
        if (GoalAndIdealCount.containsKey(goalName)) {
            return true;
        }
        return false;
    }

    /**
     * This function removes an input goal from the GoalAndIdealCount hashmap.
     *
     * @param goalToDelete - name for goal to remove
     * @return true if a goal has been deleted
     */

    public static void goalDelete(String goalToDelete) {

        if (GoalAndIdealCount.containsKey(goalToDelete)) {
            GoalAndIdealCount.remove(goalToDelete);
            System.out.println("Your goal " + goalToDelete + " has been removed successfully.");
            System.out.println("Your updated goals are: " + GoalAndIdealCount.entrySet());
        } else {
            System.out.println("Please enter a valid goal.");
        }
    }


    public static void initializeGoalsAndHabits () {
        for (String key : GoalAndIdealCount.keySet()) {
            Object[] GoalHabitStorage = new Object[2];
            GoalHabitStorage[INDEX_GOALNAME] = key; // Store the goal into our GoalHabitStorage object
            GoalHabitStorage[INDEX_HABITSLIST] = new ArrayList<String>(); // Assign empty arraylist
            GoalHabitSetup.add(GoalHabitStorage); // Add this object to the GoalHabitStorage arraylist
        }
    }

    public static void addHabits (String goalName, ArrayList < String > habitsList){

        if (goalExists(goalName)) {
            for (Object[] goalInfo : GoalHabitSetup) {
                if (goalInfo[INDEX_GOALNAME].equals(goalName)) {
                    ArrayList<String> existingHabits = (ArrayList<String>) goalInfo[INDEX_HABITSLIST];
                    existingHabits.addAll(habitsList);
                    System.out.println("The goal: " + goalName + " has been assigned habits: " + existingHabits);
//                    return true;
                }
            }
        } else {
            System.out.println("Invalid goal. Retry");
        }
//        return true;
    }


    /**
     * This function takes a specific goal and returns the habits for it. It is to be used with other functions.
     * @author Tania
     * @param goalName
     * @return ArrayList containing habits
     */

    public static ArrayList<String> getHabitsForGoal (String goalName){
        if (goalExists(goalName)) {
            for (Object[] goalInfo : GoalHabitSetup) {
                if (goalInfo[INDEX_GOALNAME].equals(goalName)) {
                    return (ArrayList<String>) goalInfo[INDEX_HABITSLIST];
                }
            }
        } else {
            System.out.println("Goal invalid. Returning empty ArrayList.");
        }
        return new ArrayList<>();
    }


    public static HashMap<String, Integer> menuAddPointsToHabit () {
        HashMap<String, Integer> nth = new HashMap<>();
        nth.put("Education", 10);
        nth.put("Exercise", 5);
        nth.put("Mental Health", 7);
        nth.put("Personal Development", 30);
        return nth;
    }


    /**
     * Calculates and optionally prints the goal completion rate for each habit.
     *
     * @param idealGoal A map of habit names to their target completion counts (Target Points).
     * @param habitCounts A map of habit names to their actual completion counts (Earned Points).
     * @param shouldPrint A boolean indicating whether to print each habit's completion status.
     * @return A map with each habit's name as the key and its completion rate as the value.
     */
    public static HashMap<String, Integer> menuWeeklyGoalCompletionRate(HashMap<String, Integer> idealGoal, HashMap<String, Integer> habitCounts, boolean shouldPrint) {

        // Initialize a map to store the completion rates for each habit
        HashMap<String, Integer> rates = new HashMap<>();
        // Use StringBuilder for efficient string concatenation in potential logging
        StringBuilder output = new StringBuilder();

        // Iterate over each habit goal in the idealGoal map
        for (String goal : idealGoal.keySet()) {
            int goalPoints = idealGoal.get(goal);  // Get the target points for the habit
            int earned = habitCounts.get(goal); // Get the earned points for the habit
            int completionRate = 0; // Initialize the rate to 0

            completionRate = (int) (((double) earned / goalPoints) * 100); // Calculate the percentage
            rates.put(goal, completionRate);// Store the calculated rate in the rates map

            // If shouldPrint is true, append the habit's completion status to the output StringBuilder
            if (shouldPrint) {
                output.append(String.format("%s Habit is %d%% completed according to this weekly target.\n", goal, completionRate));
            }
        }

        // If shouldPrint is true, print the concatenated habit completion statuses
        if (shouldPrint) {
            System.out.println(output);
        }

        return rates; //return rates(Hashmap) which stores Goal as keys and Completion Rates as values
    }

    /**
     * Generates a string listing the top 3 goals based on their weekly completion rates.
     * If there are fewer than three goals, it lists all of them.
     *
     * @author Phone
     * @param idealGoal A map of goal names to their ideal completion counts.
     * @param habitCounts A map of goal names to their actual completion counts.
     * @return A string listing the top goals in descending order of their completion rates.
     */
    public static String menuTop3Goals(HashMap<String, Integer> idealGoal, HashMap<String, Integer> habitCounts) {
        // Calculate completion rates for each goal
        // Assumes menuWeeklyGoalCompletionRate() accurately computes these rates
        HashMap<String, Integer> rates = menuWeeklyGoalCompletionRate(idealGoal, habitCounts, false);

        // Sort the completion rates in descending order
        List<Map.Entry<String, Integer>> sortedRates = new ArrayList<>(rates.entrySet());
        sortedRates.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // Generate and return the formatted string of the top 3 or fewer goals
        StringBuilder output = getStringBuilder(sortedRates);
        return output.toString();
    }

    /**
     * Helper function to generate a StringBuilder with formatted text listing the top 3 or fewer goals.
     *
     * @author Phone
     * @param sortedRates A list of Map.Entry objects representing goals and their completion rates, sorted in descending order.
     * @return StringBuilder containing the formatted text.
     */
    private static StringBuilder getStringBuilder(List<Map.Entry<String, Integer>> sortedRates) {
        // Create a new StringBuilder to construct the output string
        StringBuilder output = new StringBuilder();

        // Append a header depending on the number of goals
        output.append(sortedRates.size() >= 3 ? "Top 3 Goals for this week are " : "Mostly completed Goals are ");

        // Determine the number of goals to list (up to 3)
        int limit = Math.min(sortedRates.size(), 3);
        for (int i = 0; i < limit; i++) {
            // Retrieve each goal entry
            Map.Entry<String, Integer> entry = sortedRates.get(i);
            // Append the goal name to the output
            output.append(entry.getKey());
            // If this is not the last goal to list, append a comma
            if (i < limit - 1) {
                output.append(", ");
            }
        }

        // Append a closing remark
        output.append(" in descending order.");
        return output;
    }

    /**
     * Resets all account data by clearing goal and habit information
     *
     * @author Phone
     * @return A confirmation messsage indicating the data reset
     */
    public static String menuResetData(){
        GoalAndIdealCount.clear();
        GoalHabitSetup.clear();
//        habitcounts.clear();
        return "Your account data has been reset.";
    }
}



