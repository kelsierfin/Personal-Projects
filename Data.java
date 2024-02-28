
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
}
