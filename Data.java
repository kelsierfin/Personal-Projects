import java.util.*;
public class Data {

    public static HashMap<String, Integer> GoalAndIdealCount = new HashMap<>(); // ArrayList to store all goals and idealcounts

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
            System.out.println("Your updated goals are: " + GoalAndIdealCount.entrySet()); // Dont need this. We already print goals
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
     * This function will give the goal completion rate for each specific habit
     * @param idealGoal (Target Points aka the times you set for each specific habit
     * @param habitCounts (Earned Points aka the times you already completed for each specific habit
     * @return rate, a hashmap which will be the base data for the another potential function call
     */
    public static HashMap<String, Integer> menuWeeklyGoalCompletionRate
    (HashMap < String, Integer > idealGoal, HashMap < String, Integer > habitCounts,boolean shouldPrint){
        HashMap<String, Integer> rates = new HashMap<>();
        StringBuilder output = new StringBuilder();

        for (String goal : idealGoal.keySet()) {
            int goalPoints = idealGoal.getOrDefault(goal, 0);
            int earned = habitCounts.getOrDefault(goal, 0);
            int rate = 0;
            if (goalPoints != 0) {
                rate = (int) (((double) earned / goalPoints) * 100);
            }
            rates.put(goal, rate);
            if (shouldPrint) {
                output.append(String.format("%s Habit is %d%% completed according to this weekly target.\n", goal, rate));
            }
        }

        if (shouldPrint) {
            System.out.println(output.toString());
        }

        return rates;
    }


    /**
     * This function will give the top 3 of the habits
     * @param idealGoal (Target Points aka the times you set for each specific habit
     * @param habitCounts (Earned Points aka the times you already completed for each specific habit
     * @return rate, a hashmap which will be the base data for the another potential function call
     */
    public static String menuTop3Goals
    (HashMap < String, Integer > idealGoal, HashMap < String, Integer > habitCounts){
        HashMap<String, Integer> rates = menuWeeklyGoalCompletionRate(idealGoal, habitCounts, false); // Assuming this method returns rates correctly
        List<Map.Entry<String, Integer>> list = new ArrayList<>(rates.entrySet());
        list.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        StringBuilder output = new StringBuilder();
        output.append(list.size() >= 3 ? "Top 3 Goals for this week are " : "Mostly completed Goals are ");

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
     * @return A confirmation messsage indicating the data reset
     */
    public static String menuResetData(){
        GoalAndIdealCount.clear();
        GoalHabitSetup.clear();
//        habitcounts.clear();String resetString = "Your account data has been reset.";
        return "Your account data has been reset.";
    }
}



