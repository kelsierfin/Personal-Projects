import java.util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */

public class Data {

    /**
     * @description This hashmap stores the goals and ideal counts defined in MenuCreateGoal.
     * @author Tania
     */
    public static final HashMap<String, Integer> GoalAndIdealCount = new HashMap<>();

    public static final ArrayList<Object[]> GoalHabitSetup = new ArrayList<>(); // Contains goal, its habits and idealcount
    public static final int INDEX_GOALNAME = 0;
    public static final int INDEX_HABITSLIST = 1;


    /**
     * @param goalName
     * @param goalIdealCount
     * @return
     * @description This function adds a goal and idealcount from the user into the GoalAndIdealCount hashmap.
     * The goal is the key, and ideal count is the value.
     * @author Tania
     */
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
     * @param goalName (String)
     * @return boolean
     * @author Tania
     */
    public static boolean goalExists(String goalName) {

        String goalNameDuplicate = goalName.toLowerCase().replaceAll("\\s", "");
        for (String existingGoal : GoalAndIdealCount.keySet()) {
            if (goalNameDuplicate.equals(existingGoal.toLowerCase().replaceAll("\\s", ""))) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param goalToDelete - name for goal to remove
     * @return true if a goal has been deleted
     * @description This function removes an input goal from the GoalAndIdealCount hashmap.
     */

    public static boolean goalDelete(String goalToDelete) {

        if (goalExists(goalToDelete)) {
            GoalAndIdealCount.remove(goalToDelete); // Remove goal from GoalAndIdealCount

            ArrayList<Object[]> itemsToRemove = new ArrayList<>(); // Create ArrayList for items to remove. This prevents errors with goalExists.

            for (Object[] item : GoalHabitSetup) { // If goal is in GoalHabitSetup, remove goal from here too.
                if (item[INDEX_GOALNAME].equals(goalToDelete)) {
                    itemsToRemove.add(item);
//                    GoalHabitSetup.remove(item);
                }
            }
            GoalHabitSetup.removeAll(itemsToRemove);
            System.out.println("Your goal " + goalToDelete + " has been removed successfully.");
            return true;
        } else {
            System.out.println("Please enter a valid goal for deletion.");
            return false;
        }
    }


    /** @description This function initializes the GoalHabitSetup structure by assigning goals and an empty arraylist for habits
     * @author Tania
     * @params None
     * @return None
     */

    public static void initializeGoalsAndHabits() {
        for (String key : GoalAndIdealCount.keySet()) {
//            if (key.isEmpty()) {
            Object[] GoalHabitStorage = new Object[2];
            GoalHabitStorage[INDEX_GOALNAME] = key; // Store the goal into our GoalHabitStorage object
            GoalHabitStorage[INDEX_HABITSLIST] = new ArrayList<String>(); // Assign empty arraylist
            GoalHabitSetup.add(GoalHabitStorage); // Add this object to the GoalHabitStorage arraylist
//            }
        }
    }

    /**
     * @description This function allows a user to add habits to their goal
     * @param goalName
     * @param habitsList
     * @return boolean
     */


    public static boolean addHabits(String goalName, ArrayList<String> habitsList) {

        if (goalExists(goalName)) {
            for (Object[] goalInfo : GoalHabitSetup) {
                if (goalInfo[INDEX_GOALNAME].equals(goalName)) {
                    ArrayList<String> existingHabits = (ArrayList<String>) goalInfo[INDEX_HABITSLIST];

                    // Check for duplicate habits before adding them. If ANY habit is duplicated, return false.
                    for (String habit : habitsList) {
                        if (!existingHabits.contains(habit)) {
                            existingHabits.add(habit);
                            System.out.println("The goal: " + goalName + " has been assigned habits: " + existingHabits);
                        } else {
                            System.out.println("Duplicate habit. Retry.");
                            return false;
                        }
                        return true;
                    }
                }
            }
        } else {
            System.out.println("Invalid goal. Retry");
            return false;
        }
        return false;
    }


    /**
     * This function takes all the goals from goalAndIdealCount and creates an ArrayList. This is to be used in Sanbeer's functions
     * @return ArrayList containing goals
     * @author Tania
     */

    public static ArrayList<String> getGoalsArrayList() {
        // Take all keys from GoalandIdealCount HashMap and turn into Arraylist
        ArrayList<String> goalsArrayList = new ArrayList<>();
        for (String key : GoalAndIdealCount.keySet()) { // Iterate through each key
            goalsArrayList.add(key);
        }
        return goalsArrayList;
    }

    /**
     * This function takes all the goals from GoalHabitSetup, then takes all the habits and creates an ArrayList. This is to be used in Sanbeer's functions.
     * @return ArrayList containing habits
     * @author Tania
     */
//
//    public static ArrayList<String> getAllHabitsArrayList() {
//        // For each Goal, take all habits and add to our ArrayList
//        ArrayList<String> habitsArrayList = new ArrayList<>();
//
//        for (Object[] item : GoalHabitSetup) {
//            habitsArrayList.addAll((ArrayList<String>) item[INDEX_HABITSLIST]);
//        }
//        return habitsArrayList;
//    }


    /**
     * @param goalName
     * @param habitToDelete
     * @description This function removes habits from a given goal
     */

    public static boolean deleteHabitsFromGoal(String goalName, String habitToDelete) {

        if (goalExists(goalName)) {
            for (Object[] goalInfo : GoalHabitSetup) {
                if (goalInfo[INDEX_GOALNAME].equals(goalName)) {
                    ArrayList<String> habitsList = (ArrayList<String>) goalInfo[INDEX_HABITSLIST]; // Add string casting
                    if (habitsList.contains(habitToDelete)) {
                        habitsList.remove(habitToDelete);
                        System.out.println("Habit deleted!");
                        return true;
                    } else {
                        System.out.println("Habit is not valid. Retry.");
                        return false;
                    }
                }
            }
        } else {
            return false;
        }
        return false;
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




