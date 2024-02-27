import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Data {

    private static final Scanner scanner = new Scanner(System.in);


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

}
