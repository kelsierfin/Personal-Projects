import java.util.*;
import java.util.HashMap;
import java.util.Map;

public class Data {

    public static final HashMap<String, Integer> GoalAndIdealCount = new HashMap<String, Integer>(); // ArrayList to store all accounts.

    public static boolean createAGoal(String goalName, Integer goalIdealCount) {

        if (!goalExists(goalName)) {
            GoalAndIdealCount.put(goalName, goalIdealCount);
            System.out.printf("Goal added successfully!\nYour goal is: " + goalName + " and your ideal count is: " + goalIdealCount + "\n");
            return true;
        }
        if (goalExists(goalName)) {
            System.out.println("Your goal (" + goalName + ") already exists.");
            return false;
        }

        return false;
    }

    /**
     * This function checks if the goal entered already exists.
     * @author Tania
     * @param goalName
     * @return boolean
     */

    private static boolean goalExists(String goalName) {
        if (GoalAndIdealCount.containsKey(goalName)) {
            return true;
        }
        return false;
    }

    /**
     * This function removes an input goal from the GoalAndIdealCount hashmap.
     * @param goalToDelete
     * @return true if a goal has been deleted
     */

    public static boolean goalDelete(String goalToDelete) {

        if (GoalAndIdealCount.containsKey(goalToDelete)) {
            GoalAndIdealCount.remove(goalToDelete);
            System.out.println("Your goal " + goalToDelete + " has been removed successfully.");
//        System.out.println("Your updated goals are: " + GoalAndIdealCount.entrySet()); // Dont need this. We already print goals.
            return true;
        } else {
            System.out.println("Please enter a valid goal.");
            return false;
        }

    }

    public static HashMap<String,Integer> menuCheckingGoalsAndHabits(){
        HashMap<String,Integer> sth = new HashMap<>();
        sth.put("Reading", 100);
        sth.put("Swimming", 50);
        sth.put("Editing", 75);
        sth.put("Coding", 200);
    return sth;
    }

    public static HashMap<String,Integer> menuAddPointsToHabit(){
        HashMap<String,Integer> nth = new HashMap<>();
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
    public static HashMap<String, Integer> menuWeeklyGoalCompletionRate(HashMap<String, Integer> goalPoints, HashMap<String, Integer> habitCounts, boolean shouldPrint) {
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
    public static String menuTop3Habits(HashMap<String, Integer> goalPoints, HashMap<String, Integer> habitCounts) {
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

}
