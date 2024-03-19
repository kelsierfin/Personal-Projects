import core.objects.Goal;
import core.objects.Habit;

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
//    public static final HashMap<String, Integer> GoalAndIdealCount = new HashMap<>();
    public static final HashSet<Goal> goals = new HashSet<>();
    public static HashMap<String, ArrayList<String>> matrix = new HashMap<>();
    public static HashMap<String, ArrayList<String>> fields = new HashMap<>();

    public static final ArrayList<Object[]> GoalHabitSetup = new ArrayList<>(); // Contains goal, its habits and idealcount

    public static final ArrayList<Habit> habitsList = new ArrayList<>();

    public static final HashMap<Goal, ArrayList> tracker = new HashMap<>();
        // Arraylist (habitsList). The habitslist contains the Habit objects.

    public static final int INDEX_GOALNAME = 0;
    public static final int INDEX_HABITSLIST = 1;

    private static HashMap<String, Integer> habitAndICounts = new HashMap<>();
    private static HashMap<String, Integer> habitAndECounts = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);
    protected static HashMap<String, Integer> GoalAndIdealCount; // placeholder


    /**
     * @param goalName
     * @param goalIdealCount
     * @return
     * @description This function adds a goal and idealcount from the user into the GoalAndIdealCount hashmap.
     * The goal is the key, and ideal count is the value.
     * @author Tania
     */
    public static boolean createAGoal(String goalName, Integer goalIdealCount) {

        Goal goal = new Goal(goalName, goalIdealCount, null);

        if(goals.contains(goal)){
            System.out.println("Your goal (" + goalName + ") already exists.");
        } else{
            goals.add(goal);
            System.out.printf("Goal added successfully!\nYour goal is: " + goal.getGoal() + " and your ideal count is: " + goal.getIdealCount() + "\n");
            return true;
         }
        return false;
    }

    /**
     * This function checks if the goal entered already exists.
     * NOTE: THIS METHOD MAY NOT BE NEEDED ANYMORE.
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
            Integer goalIdealCount = GoalAndIdealCount.get(goalName);
            for (Object[] goalInfo : GoalHabitSetup) {
                if (goalInfo[INDEX_GOALNAME].equals(goalName)) {
                    ArrayList<String> existingHabits = (ArrayList<String>) goalInfo[INDEX_HABITSLIST];

                    // Check for duplicate habits before adding them. If ANY habit is duplicated, return false.
                    for (String habit : habitsList) {
                        if (!existingHabits.contains(habit)) {
                            existingHabits.add(habit);
                            System.out.println("The goal: " + goalName + " has been assigned habits: " + existingHabits);

                            // Map each new habit to the goal's ideal count
                            habitAndICounts.put(habit, goalIdealCount);
                            // Initialize each new habit's earned count to 0
                            habitAndECounts.put(habit, 0);
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
//        // Take all keys from GoalandIdealCount HashMap and turn into Arraylist
        ArrayList<String> goalsArrayList = new ArrayList<>();
//        for (String key : GoalAndIdealCount.keySet()) { // Iterate through each key
//            goalsArrayList.add(key);
//        }

        for (Goal goal : goals) {
            goalsArrayList.add(goal.getGoal());
        }
        System.out.println(goalsArrayList);
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
    /**
     * This method is used to categorize the given goals into different quadrants of the Eisenhower matrix.
     * @author Sanbeer Shafin
     * @param goals This is the list of goals.
     * @return HashMap This returns a map of quadrants in the Eisenhower matrix  and their corresponding goals.
     */

//    public static HashMap<String, ArrayList<String>> menuEisenhowerMatrix(ArrayList<String> goalsArrayList) {
//        // Create a scanner object for user input
//        Scanner scanner = new Scanner(System.in);
//
//        // Define the categories
//        String[] categories = {"Urgent & Important", "Urgent & Not Important", "Important & Not Urgent",
//                "Not Important & Not Urgent"};
//
//        // Create a HashMap to store the categorized goals
//        HashMap<String, ArrayList<String>> prioritizedGoals = new HashMap<>();
//
//        // Initialize the HashMap with empty lists for each category
//        for (String category : categories) {
//            prioritizedGoals.put(category, new ArrayList<>());
//        }
//
//        // Loop through each goal
//        for (String goal : goals) {
//            // Prompt the user to categorize the goal
//            System.out.println("Goal: " + goal);
//            System.out.println("Please choose a category for this goal:");
//            for (int i = 0; i < categories.length; i++) {
//                System.out.println((i + 1) + ". " + categories[i]);
//            }
//
//            // Get the user's choice
//            int categoryIndex = scanner.nextInt() - 1;
//
//            // Add the goal to the chosen category
//            prioritizedGoals.get(categories[categoryIndex]).add(goal);
//        }
//        scanner.close();
//        // Return the categorized goals
//        for (HashMap.Entry<String, ArrayList<String>> entry : prioritizedGoals.entrySet()) {
//            System.out.println("Quadrant: " + prioritizedGoals.getKey() + ", Value: " + entry.getValue());
//        }
//        return prioritizedGoals;
//    }

//    public static HashMap<String, ArrayList<String>> menuCategorizeGoals (ArrayList<String> goals) {
//        // Define the categories inside the function
//        String[] categories = {"Finance", "Work", "School", "Emotional", "Spiritual", "Social"};
//
//        // Initialize the HashMap to store the categorized goals
//        HashMap<String, ArrayList<String>> categorizedGoals = new HashMap<>();
//
//        // Initialize the scanner to get user input
//        Scanner scanner = new Scanner(System.in);
//
//        // Loop through all the goals
//        for (String goal : goals) {
//            System.out.println("Goal: " + goal);
//            System.out.println("Please select a category for this goal:");
//
//            // Display the categories
//            for (int i = 0; i < categories.length; i++) {
//                System.out.println((i + 1) + ". " + categories[i]);
//            }
//
//            // Get the user's choice
//            int choice = scanner.nextInt();
//
//            // Check if the chosen category is valid
//            while (choice < 1 || choice > categories.length) {
//                System.out.println("Invalid category. Please choose a valid category:");
//                choice = scanner.nextInt();
//            }
//
//            // Get the chosen category
//            String category = categories[choice - 1];
//
//            // If the category is not in the HashMap, add it
//            if (!categorizedGoals.containsKey(category)) {
//                categorizedGoals.put(category, new ArrayList<>());
//            }
//            scanner.close();
//            // Add the goal to the chosen category
//            categorizedGoals.get(category).add(goal);
//        }
//        for (HashMap.Entry<String, ArrayList<String>> entry : categorizedGoals.entrySet()) {
//            System.out.println("Category: " + categorizedGoals.getKey() + ", Habit: " + entry.getValue());
//        }
//        // Return the categorized goals
//        return categorizedGoals;
//    }




    /**
     * @description: Retrieves the ideal count for each habit.
     * This method returns a {@link HashMap} that maps each habit to its ideal completion count.
     * The ideal count represents the target number of times a habit should be completed.
     *
     * @param: None
     * @return A {@link HashMap} where keys are habit names and values are their corresponding ideal counts.
     * @author Phone
     */
    public static HashMap<String,Integer> habitAndIdealCount(){
        // Clone the habitAndICounts to avoid manipulation of the original map
        HashMap<String,Integer> idealCount = new HashMap<>(habitAndICounts);
        return idealCount;
    }


    /**
     * Retrieves the earned count for each habit.
     * <p>
     * This method returns a {@link HashMap} that maps each habit to the number of times it has been
     * actually completed by the user. The earned count reflects the user's progress towards achieving
     * their habit goals.
     *
     * @return A {@link HashMap} where keys are habit names and values are their actual completion counts.
     */
    public static HashMap<String,Integer> habitsAndEarned(){
        // Clone the habitAndECounts to prevent direct modifications to the original map
        HashMap<String,Integer> earnedCount = new HashMap<>(habitAndECounts);
        return earnedCount;
    }

    /**
     * @description: Updates the completion counts for a specific habit
     *
     *This method prompts the user to enter the name of a habit they wish to update. If the habit exists,
     * it increments the habit's completion count by one and asks if the user wants to update another habit.
     * The process continues until the user chooses to stop.
     *
     * @param: None
     * @return: None
     * @author: Phone
     */

    public static void updateHabitCompletionCounts() {
        boolean continueUpdating = true;

        while (continueUpdating) {
            System.out.println("Please enter the habit you would like to update: ");
            String habitToUpdate = scanner.nextLine();

            // Check if the input is valid
            if (habitAndECounts.containsKey(habitToUpdate)) {
                // Update the count associated with that habit
                int newCount = habitAndECounts.get(habitToUpdate) + 1;
                habitAndECounts.put(habitToUpdate, newCount);

                System.out.println("Habit '" + habitToUpdate + "' is completed 1 more time. Total completions: " + newCount);

                // Ask if they want to update another habit
                System.out.println("Do you want to update another habit? (yes/no)");
                String answer = scanner.nextLine().trim().toLowerCase();

                if (!answer.equals("yes")) {
                    continueUpdating = false;
                }
            } else {
                // Invalid input
                System.out.println("Invalid input. Please enter a valid habit.");
            }
        }

        // Optionally, you can print or return the updated hashmap here
        // System.out.println("Updated habit counts: " + habitAndECounts);
    }

    /**
     * @description: Calculates and optionally prints the goal completion rate for each habit.
     *
     * @param habitAndGoals A map of habit names to their target completion counts (Target Points).
     * @param habitsAndEarned A map of habit names to their actual completion counts (Earned Points).
     * @param shouldPrint A boolean indicating whether to print each habit's completion status.
     * @return A map with each habit's name as the key and its completion rate as the value.
     * @author: Phone
     */
    public static HashMap menuWeeklyHabitCompletionRate(HashMap<String,Integer>habitAndGoals,HashMap<String,Integer>habitsAndEarned,Boolean shouldPrint){

        HashMap<String, Integer> rates = new HashMap<>();// Initialize a map to store the completion rates for each habit

        StringBuilder output = new StringBuilder(); // Use StringBuilder for efficient string concatenation in potential logging

        // Iterate over each habit goal in the idealGoal map
        for (String goal : habitAndGoals.keySet()) {
            int goalPoints = habitAndGoals.get(goal);  // Get the target points for the habit
            int earned = habitsAndEarned.get(goal); // Get the earned points for the habit
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
     * @description: Generates a string listing the top 3 habits based on their weekly completion rates.
     * If there are fewer than three habits, it lists all of them.
     *
     * @param habitAndGoals A map of habit names to their ideal completion counts.
     * @param habitsAndEarned A map of habit names to their actual completion counts.
     * @return A string listing the top goals in descending order of their completion rates.
     * @author Phone
     */

    public static String menuTop3Habits(HashMap<String,Integer> habitAndGoals, HashMap<String,Integer> habitsAndEarned){

        // Assumes menuWeeklyGoalCompletionRate() accurately computes these rates
        HashMap<String,Integer> rates = menuWeeklyHabitCompletionRate(habitAndGoals,habitsAndEarned,false);

        // Sort the completion rates in descending order
        List<Map.Entry<String, Integer>> sortedRates = new ArrayList<>(rates.entrySet());
        sortedRates.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // Generate and return the formatted string of the top 3 or fewer goals
        StringBuilder output = getStringBuilder(sortedRates);
        return output.toString();
    }

    /**
     * @description: Helper function to generate a StringBuilder with formatted text listing the top 3 or fewer habits.
     *
     * @param sortedRates A list of Map.Entry objects representing goals and their completion rates, sorted in descending order.
     * @return StringBuilder containing the formatted text.
     * @author: Phone
     */
    private static StringBuilder getStringBuilder(List<Map.Entry<String, Integer>> sortedRates) {
        // Create a new StringBuilder to construct the output string
        StringBuilder output = new StringBuilder();

        // Append a header depending on the number of goals
        output.append(sortedRates.size() >= 3 ? "Top 3 habits for this week are " : "Mostly completed Goals are ");

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
     * @description: Resets all account data by clearing goal and habit information
     *
     * @return A confirmation messsage indicating the data reset
     * @author: Phone
     */
    public static String menuResetData(){
        GoalHabitSetup.clear();
        GoalAndIdealCount.clear();
        return "Your account data has been reset.";
    }
    /**@description: The organizes the data from menu & goalsArrayList into a HashMap
     *
     *
     *
     * @param: choicesArrayList - this is found from menu
     * @return: A HashMap with organized data
     * @author: Sanbeer
     */

    public static HashMap<String,ArrayList<String>>storeEisenhowerMatrix(ArrayList<Integer> choicesArrayList) {
        ArrayList<String> goalsArrayList = Data.getGoalsArrayList();

        String[] categories = {"Urgent & Important", "Urgent & Not Important", "Important & Not Urgent",
                "Not Important & Not Urgent"};

        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();
        ArrayList<String> list3 = new ArrayList<>();
        ArrayList<String> list4 = new ArrayList<>();


        for (int i = 0; goalsArrayList.size() > i; i++){
            if (choicesArrayList.get(i) == 1){
                list1.add(goalsArrayList.get(i));
            }
            if (choicesArrayList.get(i) == 2){
                list2.add(goalsArrayList.get(i));
            }
            if (choicesArrayList.get(i) == 3){
                list3.add(goalsArrayList.get(i));
            }
            if (choicesArrayList.get(i) == 4){
                list4.add(goalsArrayList.get(i));
            }
        }

        matrix.put("Urgent & Important", list1);
        matrix.put("Urgent & Not Important", list2);
        matrix.put("Important & Not Urgent", list3);
        matrix.put("Not Important & Not Urgent", list4);

        return matrix;
    }
    public static boolean matrixExists() {
        boolean matrixExist = false;
        if (matrix.containsKey("Urgent & Important")){
            matrixExist = true;
        }
        return matrixExist;
    }

    public static HashMap<String,ArrayList<String>> storeCategorizeGoals(ArrayList<Integer> choicesArrayList2) {
        HashMap<String, ArrayList<String>> fields = new HashMap<>();
        ArrayList<String> goalsArrayList2 = Data.getGoalsArrayList();
        String[] categories2 = {"1) Finance", "2) Work", "3) School", "4) Emotional", "5) Spiritual", "6) Social"};

        // Create another for loop to iterate thru each goal object in the hashset Goals
        //  Assign category to goal / get category for a goal

        ArrayList<String> list11 = new ArrayList<>();
        ArrayList<String> list22 = new ArrayList<>();
        ArrayList<String> list33 = new ArrayList<>();
        ArrayList<String> list44 = new ArrayList<>();
        ArrayList<String> list55 = new ArrayList<>();
        ArrayList<String> list66 = new ArrayList<>();

        for (int i = 0; goalsArrayList2.size() > i; i++){
            if (choicesArrayList2.get(i) == 1){
                list11.add(goalsArrayList2.get(i));
            }
            if (choicesArrayList2.get(i) == 2){
                list22.add(goalsArrayList2.get(i));
            }
            if (choicesArrayList2.get(i) == 3){
                list33.add(goalsArrayList2.get(i));
            }
            if (choicesArrayList2.get(i) == 4){
                list44.add(goalsArrayList2.get(i));
            }
            if (choicesArrayList2.get(i) == 5){
                list55.add(goalsArrayList2.get(i));
            }
            if (choicesArrayList2.get(i) == 6){
                list66.add(goalsArrayList2.get(i));
            }

        }
        fields.put("Finance" , list11);
        fields.put("Work" , list22);
        fields.put("School" , list33);
        fields.put("Emotional" , list44);
        fields.put("Spiritual" , list55);
        fields.put("Social" , list66);

        return fields;
    }
    public static boolean categoryExists() {
        boolean fieldExist = false;
        if (fields.containsKey("Finance")) {
            fieldExist = true;
        }
        return fieldExist;
    }
    }




