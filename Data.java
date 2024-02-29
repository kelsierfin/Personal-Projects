import java.util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    private static boolean goalExists(String goalName) {
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
//        System.out.println("Your updated goals are: " + GoalAndIdealCount.entrySet()); // Dont need this. We already print goals.
//            return true;
        } else {
            System.out.println("Please enter a valid goal.");
//            return false;
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


        public static HashMap<String, Integer> menuCheckingGoalsAndHabits () {
            HashMap<String, Integer> sth = new HashMap<>();
            sth.put("Reading", 100);
            sth.put("Swimming", 50);
            sth.put("Editing", 75);
            sth.put("Coding", 200);
            return sth;
        }

        public static HashMap<String, Integer> menuAddPointsToHabit () {
            HashMap<String, Integer> nth = new HashMap<>();
            nth.put("Reading", 10);
            nth.put("Swimming", 5);
            nth.put("Editing", 7);
            nth.put("Coding", 30);
            return nth;
        }


        /**
         * This function will give the goal completion rate for each specific habit
         * @param goalPoints (Target Points aka the times you set for each specific habit
         * @param habitCounts (Earned Points aka the times you already completed for each specific habit
         * @return rate, a hashmap which will be the base data for the another potential function call
         */
        public static HashMap<String, Integer> menuWeeklyGoalCompletionRate
        (HashMap < String, Integer > goalPoints, HashMap < String, Integer > habitCounts,boolean shouldPrint){
            HashMap<String, Integer> rates = new HashMap<>();
            StringBuilder output = new StringBuilder();

            for (String habit : goalPoints.keySet()) {
                int goal = goalPoints.getOrDefault(habit, 0);
                int earned = habitCounts.getOrDefault(habit, 0);
                int rate = 0;
                if (goal != 0) {
                    rate = (int) (((double) earned / goal) * 100);
                }
                rates.put(habit, rate);
                if (shouldPrint) {
                    output.append(String.format("%s Habit is %d%% completed according to this weekly target.\n", habit, rate));
                }
            }

            if (shouldPrint) {
                System.out.println(output.toString());
            }

            return rates;
        }


        /**
         * This function will give the top 3 of the habits
         * @param goalPoints (Target Points aka the times you set for each specific habit
         * @param habitCounts (Earned Points aka the times you already completed for each specific habit
         * @return rate, a hashmap which will be the base data for the another potential function call
         */
        public static String menuTop3Habits
        (HashMap < String, Integer > goalPoints, HashMap < String, Integer > habitCounts){
            HashMap<String, Integer> rates = menuWeeklyGoalCompletionRate(goalPoints, habitCounts, false); // Assuming this method returns rates correctly
            List<Map.Entry<String, Integer>> list = new ArrayList<>(rates.entrySet());
            list.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

            StringBuilder output = new StringBuilder();
            output.append(list.size() >= 3 ? "Top 3 habits for this week are " : "Mostly completed habits are ");

            int limit = Math.min(list.size(), 3);
            for (int i = 0; i < limit; i++) {
                Map.Entry<String, Integer> entry = list.get(i);
                output.append(entry.getKey());
                if (i < limit - 1) {
                    output.append(", ");
                }
            }

            output.append(" in descending order.");
            return output.toString();
        }

    private static void menuHabitSetup() {

    }
    /**
     * This method is used to add counts to different habits.
     * @author Sanbeer Shafin
     * @param habits This is the list of habits.
     * @return HashMap This returns a map of habits  and their corresponding counts.
     */

    public static HashMap<String, Integer> habitCounter(ArrayList<String> habits){
        HashMap<String, Integer> habitCounts = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        // the first two lines create the HashMap called habitCounts and sets up the scanner
        System.out.println("Which habit do you want to add a count to?");
        String habitInput = scanner.nextLine();
        // Gets the habit of choice and stores it in a variable
        // checks if the original arraylist already contains the habit or not
        if (habits.contains(habitInput)) {
            // checks if the HashMap already has the habit and count in it, if not it starts a new count
            if (habitCounts.contains(habitInput)){
                habitCounts.put(habitInput, habitCounts.get + 1);
            } else {
                habitCounts.put(habitInput,1);
            }
        } else {
            System.out.println("This habit is not on the list of already added habits.");
        }
        scanner.close();
        for (HashMap.Entry<String,Integer> entry : habitCounts.entrySet()) {
            System.out.println("Habit: " + habitCounts.getKey() + ", Counts/Points: " + entry.getValue());
        }
        return habitCounts;
    }
    /**
     * This method is used to categorize the given goals into different categories.
     * @author Sanbeer Shafin
     * @param goals This is the list of goals.
     * @return HashMap This returns a map of categories and their corresponding goals.
     */

    public static HashMap<String, ArrayList<String>> goalCategorization(ArrayList<String> goals) {
        // Define the categories inside the function
        String[] categories = {"Finance", "Work", "School", "Emotional", "Spiritual", "Social"};

        // Initialize the HashMap to store the categorized goals
        HashMap<String, ArrayList<String>> categorizedGoals = new HashMap<>();

        // Initialize the scanner to get user input
        Scanner scanner = new Scanner(System.in);

        // Loop through all the goals
        for (String goal : goals) {
            System.out.println("Goal: " + goal);
            System.out.println("Please select a category for this goal:");

            // Display the categories
            for (int i = 0; i < categories.length; i++) {
                System.out.println((i + 1) + ". " + categories[i]);
            }

            // Get the user's choice
            int choice = scanner.nextInt();

            // Check if the chosen category is valid
            while (choice < 1 || choice > categories.length) {
                System.out.println("Invalid category. Please choose a valid category:");
                choice = scanner.nextInt();
            }

            // Get the chosen category
            String category = categories[choice - 1];

            // If the category is not in the HashMap, add it
            if (!categorizedGoals.containsKey(category)) {
                categorizedGoals.put(category, new ArrayList<>());
            }
            scanner.close();
            // Add the goal to the chosen category
            categorizedGoals.get(category).add(goal);
        }
        for (HashMap.Entry<String, ArrayList<String>> entry : categorizedGoals.entrySet()) {
            System.out.println("Category: " + categorizedGoals.getKey() + ", Habit: " + entry.getValue());
        }
        // Return the categorized goals
        return categorizedGoals;
    }
    /**
     * This method is used to categorize the given goals into different quadrants of the Eisenhower matrix.
     * @author Sanbeer Shafin
     * @param goals This is the list of goals.
     * @return HashMap This returns a map of quadrants in the Eisenhower matrix  and their corresponding goals.
     */
    public static HashMap<String, ArrayList<String>> goalPrioritization(ArrayList<String> goals) {
        // Create a scanner object for user input
        Scanner scanner = new Scanner(System.in);

        // Define the categories
        String[] categories = {"Urgent & Important", "Urgent & Not Important", "Important & Not Urgent",
                "Not Important & Not Urgent"};

        // Create a HashMap to store the categorized goals
        HashMap<String, ArrayList<String>> prioritizedGoals = new HashMap<>();

        // Initialize the HashMap with empty lists for each category
        for (String category : categories) {
            prioritizedGoals.put(category, new ArrayList<>());
        }

        // Loop through each goal
        for (String goal : goals) {
            // Prompt the user to categorize the goal
            System.out.println("Goal: " + goal);
            System.out.println("Please choose a category for this goal:");
            for (int i = 0; i < categories.length; i++) {
                System.out.println((i + 1) + ". " + categories[i]);
            }

            // Get the user's choice
            int categoryIndex = scanner.nextInt() - 1;

            // Add the goal to the chosen category
            prioritizedGoals.get(categories[categoryIndex]).add(goal);
        }
        scanner.close();
        // Return the categorized goals
        for (HashMap.Entry<String, ArrayList<String>> entry : prioritizedGoals.entrySet()) {
            System.out.println("Quadrant: " + prioritizedGoals.getKey() + ", Value: " + entry.getValue());
        }
        return prioritizedGoals;
    }





}


