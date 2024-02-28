
import java.util.ArrayList;
import java.util.HashMap;

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
     * @author Tania
     * @param goalName - name of goal to check if it exists
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

    public static void addHabits(String goalName, ArrayList<String> habitsList) {

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

    public static void initializeGoalsAndHabits() {
        for (String key : GoalAndIdealCount.keySet()) {
            Object[] GoalHabitStorage = new Object[2];
            GoalHabitStorage[INDEX_GOALNAME] = key; // Store the goal into our GoalHabitStorage object
            GoalHabitStorage[INDEX_HABITSLIST] = new ArrayList<String>(); // Assign empty arraylist
            GoalHabitSetup.add(GoalHabitStorage); // Add this object to the GoalHabitStorage arraylist
        }
    }



    /**
     * This function takes a specific goal and returns the habits for it. It is to be used with other functions.
     * @author Tania
     * @param goalName
     * @return ArrayList containing habits
     */

    public static ArrayList<String> getHabitsForGoal(String goalName) {
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




}

