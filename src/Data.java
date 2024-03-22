import core.objects.Goal;
import core.objects.Habit;
import core.objects.Habits;

import java.io.PrintWriter;
import java.util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 */

public class Data {

    /**
     * @description This hashmap stores the goals and ideal counts defined in MenuCreateGoal.
     * @author Tania
     */

    protected static  ArrayList<Integer> choicesArrayList2;
//    public static final HashMap<String, Integer> GoalAndIdealCount = new HashMap<>();
    protected static HashSet<Goal> goals;
    protected static HashSet<Habit> habits; // Hashset for habits for one goal
    protected static HashMap<String, ArrayList<String>> matrix;

    protected static HashMap<String, ArrayList<String>> fields = new HashMap<>();

    protected static final ArrayList<Object[]> GoalHabitSetup = new ArrayList<>(); // Contains goal, its habits and idealcount


    protected static HashMap<Goal, HashSet<Habit>> tracker = new HashMap<>();
        // Arraylist (habitsList). The habitslist contains the Habit objects.

    protected static final int INDEX_GOALNAME = 0;
    protected static final int INDEX_HABITSLIST = 1;

    protected static final HashMap<String, Integer> habitAndICounts = new HashMap<>();
    protected static final  HashMap<String, Integer> habitAndECounts = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);
    protected static HashMap<String, Integer> GoalAndIdealCount; // placeholder

    public Data() {
        this.choicesArrayList2 = new ArrayList<>();
        this.goals = new HashSet<>();
        this.habits = new HashSet<>();
        this.matrix = new HashMap<>();


    }

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
            return false;
        } else{
            goals.add(goal);
            System.out.printf("Goal added successfully!\nYour goal is: " + goal.getGoal() + " and your ideal count is: " + goal.getIdealCount() + "\n");
            return true;
         }
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

        boolean goalFound = false;

        for (Goal goal : goals) {
            if (goal.getGoal().equals(goalToDelete)){
                System.out.println("Successfully deleted: " + goal.getGoal());
                goals.remove(goal);
                goalFound = true;
                return true;
            }
        }

        if (!goalFound) {
            System.out.println("Please enter a valid goal for deletion.");
            return false;
        }

        return false;
    }



    /**
     * @description This function allows a user to add habits to their goal
     * @param goalName
     * @param habitsList
     * @return boolean
     */

    public static void addHabits(String goalName, ArrayList<String> habitsList) {

        // Go through each goal until we find goalName
        // convert each string to a habit object
        // place the object in a hashset (prevent duplicates)
        // Add the habit object to tracker, next to the goal

        HashSet<Habit> allHabits = new HashSet<>();

        for (Goal goal : goals) {
            if (goal.getGoal().equals(goalName)) {
                for (String habit : habitsList) {
                    Habit individualHabit = new Habit(goal.getGoal(), goal.getIdealCount(), goal.getCategory(), 0, habit);
                    allHabits.add(individualHabit);
                }
                // Add hashset to tracker
                tracker.put(goal, allHabits);
            }
        }

    }


    /**
     * This function takes the name of all goal objects, and places them in an ArrayList.
     * This arraylist is used for the Eisenhower matrix.
     * @return ArrayList containing String goal names
     * @author Tania
     */

    public static ArrayList<String> getGoalsArrayList() {
        ArrayList<String> goalsArrayList = new ArrayList<>();

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

    public static void deleteHabitsFromGoal(String goalName, String habitToDelete) {

        // Iterate through each goal in tracker until our goal is found
        // Iterate through the habits hashset of the goal until our habit is found
        // Remove the habit

        Goal goalOfInterest = null;

        // Locate the goal
        for (Goal goal : goals) {
            if (goal.getGoal().equals(goalName)) {
                goalOfInterest = goal;
                break;
            }
        }

        HashSet<Habit> habitsSet = tracker.get(goalOfInterest); // Get hashset for our goal

//        for

//        for (Map.Entry<Goal, HashSet<Habit>> entry : tracker.entrySet()) {
//            if (entry.getKey().getGoal().equals(goalName)) {
//                System.out.println("Goal found in tracker: " + goalName);
//                HashSet<Habit> setOfHabits = tracker.get(entry.getKey());
//
//                for (Habit habit : entry.getValue()) {
//                    if (habit.getHabit().equals(habitToDelete)) {
//                        System.out.println("Habit detected in hashset: " + habitToDelete + "tracker: " + habit.getHabit());
//
//                        setOfHabits.remove(habit);
//                        tracker.put(entry.getKey(), setOfHabits);
//                    }
//                }
//            }
//        }

        System.out.println("Here is a list of your goals, and the habits");
        for (Map.Entry<Goal, HashSet<Habit>> entry : tracker.entrySet()) {
            System.out.println("Goal: " + entry.getKey().getGoal());
            for (Habit habit : entry.getValue()) {
                System.out.println("Habit: " + habit.getHabit() + " Ideal Count: " + habit.getIdealCount());
            }
        }



//        if (goalExists(goalName)) {
//            for (Object[] goalInfo : GoalHabitSetup) {
//                if (goalInfo[INDEX_GOALNAME].equals(goalName)) {
//                    ArrayList<String> habitsList = (ArrayList<String>) goalInfo[INDEX_HABITSLIST]; // Add string casting
//                    if (habitsList.contains(habitToDelete)) {
//                        habitsList.remove(habitToDelete);
//                        System.out.println("Habit deleted!");
//                        return true;
//                    } else {
//                        System.out.println("Habit is not valid. Retry.");
//                        return false;
//                    }
//                }
//            }
//        } else {
//            return false;
//        }
//        return false;
    }
    public Habit findHabitByName(String habitName) {
        for (HashSet<Habit> habits : tracker.values()) {
            for (Habit habit : habits) {
                if (habit.getHabit().equalsIgnoreCase(habitName)) {
                    return habit;
                }
            }
        }
        return null;
    }

    public void updateHabitCompletion(String habitName) {
        Habit habit = findHabitByName(habitName);
        if (habit != null) {
            habit.incrementCurrentCount();
            System.out.println("Habit '" + habitName + "' updated. New count: " + habit.getCurrentCount());
        } else {
            System.out.println("Invalid habit. Please try again.");
        }
    }

    public static Map<String, Double> calculateWeeklyCompletionRates() {
        Map<String, Double> rates = new HashMap<>();
        for (HashSet<Habit> habits : tracker.values()) {
            for (Habit habit : habits) {
                double rate = (habit.getIdealCount() == 0) ? 0.0 :
                        ((double) habit.getCurrentCount() / habit.getIdealCount()) * 100;
                rates.put(habit.getHabit(), rate);
            }
        }
        return rates;
    }

    public static List<Habit> getTop3HabitsByCompletionRate() {
        List<Habit> sortedHabits = new ArrayList<>(getAllHabits());
        sortedHabits.sort(new core.comparators.WeeklyCompletionRateComparator());
        return sortedHabits.stream().limit(3).collect(Collectors.toList());
    }

    public static List<Habit> getAllHabits() {
        List<Habit> allHabits = new ArrayList<>();
        for (HashSet<Habit> habitSet : tracker.values()) {
            allHabits.addAll(habitSet);
        }
        return allHabits;
    }

    public static HashSet<Goal> getAllGoals() {
        return new HashSet<>(goals); // Return a copy to avoid external modifications
    }

    public static void resetCsvFile(String filePath) {
        try {
            // Option 1: Clear the file's contents
            new PrintWriter(filePath).close();

            // Option 2: Delete the file (uncomment if preferred)
            // new File(filePath).delete();

            System.out.println("CSV file has been reset.");
        } catch (Exception e) {
            System.out.println("Error resetting CSV file: " + e.getMessage());
        }
    }

    public static void resetAllData() {
        // Clear all collections
        goals.clear();
        matrix.clear();
        fields.clear();
        GoalHabitSetup.clear();
        tracker.clear();
        habitAndICounts.clear();
        habitAndECounts.clear();
        GoalAndIdealCount.clear();

        // Reset CSV File
        resetCsvFile("path/to/your/test.csv"); //

        System.out.println("All data and CSV file have been reset to their default state.");
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
        ArrayList<String> goalsArrayList2 = Data.getGoalsArrayList();
        String[] categories2 = {"1) Finance", "2) Work", "3) School", "4) Emotional", "5) Spiritual", "6) Social"};

        // Create another for loop to iterate thru each goal object in the hashset Goals
        //  Assign category to goal / get category for a goal
        int count = 0;
        for(Goal goal : goals){
            if (choicesArrayList2.get(count) == 1){
                goal.setCategory("Finance");
                count++;
            }
            if (choicesArrayList2.get(count) == 2){
                goal.setCategory("Work");
                count++;
            }
            if (choicesArrayList2.get(count) == 1){
                goal.setCategory("School");
                count++;
            }
            if (choicesArrayList2.get(count) == 1){
                goal.setCategory("Emotional");
                count++;
            }
            if (choicesArrayList2.get(count) == 1){
                goal.setCategory("Spiritual");
                count++;
            }
            if (choicesArrayList2.get(count) == 1){
                goal.setCategory("Social");
                count++;
            }
        }

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




