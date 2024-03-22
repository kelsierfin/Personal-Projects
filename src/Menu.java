import core.objects.Goal;
import core.objects.Habit;

import java.util.*;


/** This class interacts with the user to get inputs. It displays the menu of the habit tracker.
 * @author : Tania Rizwan, Sanbeer Shafin, Phone Myat
 * Tutorial:T10, T09, T16
 * Emails: tania.rizwan@ucalgary.ca, sanbeer.shafin@ucalgary.ca, phonemyat.paing@ucalgary.ca
 */

// The menu setup code was followed from Dr. Hudson's project example.
public class Menu {

    private static Data data = new Data();

    // Create scanner object for user input.
    // Private so it is only accessible within the Menu class.
    private static final Scanner scanner = new Scanner(System.in);

    // Create menu for user interaction.
    // Use a static initializer. This initializes the ArrayList when we load the Menu class.
    private static final ArrayList<String> options = new ArrayList<>();
    static {
        options.add("Exit"); // At index 0
        options.add("Create A Goal");
        options.add("Delete a Goal");
        options.add("Add Habits to A Goal");
        options.add("Delete Habits From A Goal");
        options.add("Categorize Goals");
        options.add("Show Categorized Goals");
        options.add("Create Eisenhower Matrix");
        options.add("Show Current Eisenhower Matrix");
        options.add("Add Points to Habit");
        options.add("Weekly Habit Completion Rate");
        options.add("List Top 3 Habits of the Week");
        options.add("Get Goal Recommendation");
        options.add("Get Habit Recommendation");
        options.add("View Goals and Habits");
        options.add("View Tracker");
        options.add("Load Data");
        options.add("Save Data");
        options.add("Reset Data");

    }

    // Create message to display to user. Then use static initializer to combine message and Options into one menu.
    private static String optMessage = """
            Welcome to your personal habit and goal tracker!
            \t Menu Options:
            """;
    static {
        StringBuilder sb = new StringBuilder();
        sb.append(optMessage);
        for (int i = 0; i < options.size(); i++) {
            sb.append(String.format("\t%d. %s\n", i, options.get(i) )); // Append the index and menu item
        }
        optMessage = sb.toString(); // Update the optMessage object with the final menu
    }

    // Run the menuLoop everytime Main is called
    public static void menuLoop() {
        System.out.println(optMessage);
        String choice = scanner.nextLine();
        int option = Integer.parseInt(choice);

        while (option != 0) {
            // Tell the user what option they selected. Then Continue
            if (option > 0 && option < options.size()) {
                System.out.printf("You have selected option %d. %s%n", option, options.get(option));
                System.out.println("Press Enter to continue:");
                scanner.nextLine();
            }

            switch (option) {
                case 1 -> menuCreateGoal();
                case 2 -> menuDeleteGoal();
                case 3 -> menuAddHabits();
                case 4 -> menuDeleteHabits();
                case 5 -> menuCategorizeGoals();
                case 6 -> menuShowCategorizedGoals();
                case 7 -> menuEisenhowerMatrix();
                case 8 -> menuShowEisenhowerMatrix();
                case 9 -> menuAddPointsToHabit();
                case 10 -> menuWeeklyHabitCompletionRate();
                case 11 -> menuTop3Habits();
                case 12 -> menuGoalRecommendation();
                case 13 -> menuHabitRecommendation();
                case 14 -> menuCheckingGoalsAndHabits(); // REMOVE. REPLACED BY TRACKER.
                case 15 -> menuViewTracker();
                case 16 -> menuLoadData();
                case 17 -> menuSaveData();
                case 18 -> menuResetData();

                default -> System.out.printf("Option %d is not recognizable %n", option);
            }


            System.out.println("Press Enter to see the menu again");
            scanner.nextLine();
            System.out.println(optMessage);
            choice = scanner.nextLine();
            option = Integer.parseInt(choice);
        }
        System.out.printf("Thank you for using the habit and goal tracker!%nSee you tomorrow (hopefully)!");


        scanner.close();
    }

    private static void menuHabitRecommendation() {
    }

    private static void menuGoalRecommendation() {

    }

    private static void menuSaveData() {
    }

    private static void menuLoadData() {

    }



    private static void menuGetRecommendation() {
        // Two choices:
            // Comparator 1: organize goals by current counts. Say you need to focus on goal X which has the least points.
        // Comparator 2:

    }

    private static void menuShowEisenhowerMatrix() {
        if (data.matrixExists()){
            System.out.println(data.matrix);
            scanner.nextLine();
        }else{
            System.out.println("You haven't a created a matrix buddy, please press Enter");
            scanner.nextLine();
        }
    }
    private static void menuShowCategorizedGoals(){
        if (data.categoryExists()){
            System.out.println(data.fields);
            scanner.nextLine();
        }else{
        System.out.println("You haven't categorized your goals yet, please press Enter ");
        scanner.nextLine();}
    }


    /** @description This function allows users to create a Goal and assign a goalIdealCount to it, which is the number of days they would like to work on the goal.
     * @author Tania
     * @params None
     * @returns None
     */
    private static void menuCreateGoal() {

        // Summary:
        // Ask user to input goal
        // Goal must be a String, and not empty
        // Ask user to add a count to their goal
        // Between 1 - 7.
        // Populate HashMap with Key = goal and Value = goalIdealCount if it doesn't exist already
        // Ask user to enter another goal if they wish. Otherwise, exit.

        String goalName;
        Integer goalIdealCount;
        Integer option;

        boolean shouldPrint;
        do {
            System.out.println("What is your goal?");
            // Ask user to input goal
            goalName = getGoalName();

            // Ask user to input num of days they want to work on their goal
            goalIdealCount = getIdealCount();

            // Send data to Data.java to populate hashmap
            data.createAGoal(goalName, goalIdealCount);

            // Prompt user to continue or exit menu
            System.out.println("Would you like to enter another goal? (Yes / No)");
            String response = scanner.next().trim().toLowerCase();
            shouldPrint = response.equals("yes") || response.equals("y") || response.equals("true");

//            scanner.nextLine(); // Consume any leftover newline character in the buffer

        } while (shouldPrint);

        System.out.println("Your goals are:");
        for (Goal goal : data.goals) {
            System.out.println("Goal: " + goal.getGoal() + " Ideal Count: " + goal.getIdealCount());
        }
        scanner.nextLine(); // Consume the \n left in the buffer

    }

    /** @description This function prompts the user for the name of their goal.
     * @requirements The goal cannot be an empty string.
     * @author Tania
     * @params None
     * @return goalName (String)
     */

    private static String getGoalName() {
        String goalName;
//        System.out.println("What is your goal?");
        do {
            goalName = scanner.nextLine();
        } while(goalName.isEmpty());

        return goalName; // Remove whitespaces and make lowercase. This prevents duplicating goals
    }

    /** @description This function prompts the user for the idealCount of their goal (number of days/week they want to work on their goal).
     * @requirements The idealCount cannot be <= 0, or greater than 7.
     * @author Tania
     * @return goalIdealCount (Integer)
     */

    private static Integer getIdealCount() {
        Integer goalIdealCount;
        System.out.println("Add the number of days (1-7) you want to work on this goal:");

        do {
            goalIdealCount = scanner.nextInt();
        } while(goalIdealCount <= 0 || goalIdealCount > 7);

        return goalIdealCount;
    }

    /** @description This function prompts the user for a goal to delete, and allows them to delete as many as they wish.
     * @author Tania
     * @params None
     * @return None
     */
    private static void menuDeleteGoal() {
        boolean shouldPrint;

        do {
            System.out.println("Your goals are: ");
            for (Goal goal : data.goals) {
                System.out.println("Goal: " + goal.getGoal());
            }

            System.out.print("Enter the name of the goal to remove:");
            String goalToDelete = scanner.nextLine();
            data.goalDelete(goalToDelete);

            System.out.println("Would you like to enter another goal? (Yes / No)");
            String response = scanner.next().trim().toLowerCase();
            shouldPrint = response.equals("yes") || response.equals("y") || response.equals("true");

        } while(shouldPrint);

        System.out.println("Updated goals: ");
        for (Goal goal : data.goals) {
            System.out.println("Goal: " + goal.getGoal() + " Ideal Count: " + goal.getIdealCount());
        }

    }

    /** @description This function allows a user to add habits for their goal
     * @author Tania
     * @params None
     * @return None
     */

    private static void menuAddHabits() {

        String goalName;
        ArrayList<String> habitsList;
        boolean shouldPrint;

        // print all goals
        System.out.println("Your goals are:");
        for (Goal goal : data.goals) {
            System.out.println("Goal: " + goal.getGoal());
        }

        do {
            System.out.println("Enter the goal to add habits for:");
            goalName = getGoalName();

            habitsList = getHabits();

            data.addHabits(goalName, habitsList);

            System.out.println("Would you like to enter another goal? (Yes / No)");
            String response = scanner.next().trim().toLowerCase();
            shouldPrint = response.equals("yes") || response.equals("y") || response.equals("true");

        } while (shouldPrint);

        // Finally, show all goals and their habits
        System.out.println("Here is a list of your goals, and the habits");
        for (Map.Entry<Goal, HashSet<Habit>> entry : data.tracker.entrySet()) {
            System.out.println("Goal: " + entry.getKey().getGoal());
            for (Habit habit : entry.getValue()) {
                System.out.println("Habit: " + habit.getHabit() + " Ideal Count: " + habit.getIdealCount());
            }
        }

        scanner.nextLine(); // Consume newline character in buffer

    }

    /**
     * @description This function prompts user for habits to append to an ArrayList. This is used in AddHabits
     * @author Tania
     * @params None
     * @return ArrayList<String> for habits
     */

    private static ArrayList<String> getHabits() {
        ArrayList<String> habitsList = new ArrayList<>();
        String habit;
        boolean shouldPrint = false;

        do {
            System.out.println("Enter a habit");
            habit = scanner.nextLine().trim().toLowerCase();

            if (!habit.isEmpty()) {
                habitsList.add(habit);
            } else {
                System.out.println("Invalid input.");
//                continue;
            }
            System.out.println("Would you like to enter another habit? (Yes / No)");
            String response = scanner.next().trim().toLowerCase();
            shouldPrint = response.equals("yes") || response.equals("y") || response.equals("true");
            scanner.nextLine();

        } while(shouldPrint);

        return habitsList;
    }


    /** @description This function allows users to delete habits from a goal.
     * @author Tania
     * @params None
     * @return None
     */
    private static void menuDeleteHabits() {
        // Prompt user for goal they want to remove habit from
            // Goal must be valid, otherwise retry
        // Prompt user for habit they want to remove
            // Habit must be valid, otherwise retry
        // Remove habit
        // Ask user if they want to remove more habits
        // Ask user if they want to remove habit from any other goals

        String goalName;
        String habitToDelete;
        Integer option;

        // Get list of all goals and habits
        System.out.println("Here is a list of your goals, and the habits");
        for (Map.Entry<Goal, HashSet<Habit>> entry : data.tracker.entrySet()) {
            System.out.println("Goal: " + entry.getKey().getGoal());
            for (Habit habit : entry.getValue()) {
                System.out.println("Habit: " + habit.getHabit() + " Ideal Count: " + habit.getIdealCount());
            }
        }

        do {


            System.out.println("Enter the goal you want to remove a habit from:");
            goalName = getGoalName();

            for (Goal goal : data.goals) {
                if (goal.getGoal().equals(goalName)) {
                    System.out.println("Enter the habit you want to delete: ");
                    do {
                        habitToDelete = scanner.nextLine();
                    } while (habitToDelete.isEmpty());

                    data.deleteHabitsFromGoal(goalName, habitToDelete);
                }
            }

            System.out.println("Would you like retry or delete a habit from another goal? (Yes = any number | No = 0)");
            option = scanner.nextInt();
            scanner.nextLine(); // Consume the \n left in the buffer

        } while(option != 0);

        // Finally, show all goals and their habits
        System.out.println("Here is a list of your goals, and the habits");
        for (Object[] item : data.GoalHabitSetup) {
            System.out.println("Goal: " + item[data.INDEX_GOALNAME] + " Habits: " + item[data.INDEX_HABITSLIST]);
        }
//
//        String goalName;
//        String habitToDelete;
//        Integer option;
//
//        System.out.println("Here is a list of your goals, and the habits");
//        for (Object[] item : data.GoalHabitSetup) {
//            System.out.println("Goal: " + item[data.INDEX_GOALNAME] + " Habits: " + item[data.INDEX_HABITSLIST]);
//        }
//
//        do {
//            System.out.println("Enter the goal you want to remove a habit from:");
//            goalName = getGoalName();
//            if (data.goalExists(goalName)){ // If goal exists:
//                System.out.println("Enter the habit you want to delete");
//                do {
//                    habitToDelete = scanner.nextLine();
//                }while(habitToDelete.isEmpty());
//
//                data.deleteHabitsFromGoal(goalName, habitToDelete);
//            }
//
//            System.out.println("Would you like retry or delete a habit from another goal? (Yes = any number | No = 0)");
//            option = scanner.nextInt();
//            scanner.nextLine(); // Consume the \n left in the buffer
//
//        } while(option != 0);
//
//        // Finally, show all goals and their habits
//        System.out.println("Here is a list of your goals, and the habits");
//        for (Object[] item : data.GoalHabitSetup) {
//            System.out.println("Goal: " + item[data.INDEX_GOALNAME] + " Habits: " + item[data.INDEX_HABITSLIST]);
//        }

    }

    /** @description This function allows the user to see what goals and habits they have
     * @author Tania
     * @params None
     * @return None
     */
    private static void menuCheckingGoalsAndHabits(){
        System.out.println("Here is a list of your goals, and the habits");
//        for (Object[] item : data.GoalHabitSetup) {
//            System.out.println("Goal: " + item[data.INDEX_GOALNAME] + " Habits: " + item[data.INDEX_HABITSLIST]);
//        }
//        for (Map.Entry<Goal, HashSet<Habit>> entry: data.tracker.entrySet()) {
//            String key = entry.getKey().getGoal();
//            HashSet<Habit> values = entry.getValue();
//
//            System.out.println("Key: " + key);
//            System.out.println("Values:");
//            for (String value : values) {
//                System.out.println(value);
//            }
//            System.out.println();
//        }
//        }
    }

    private static void menuViewTracker() {

        System.out.println("List of your goals, and associated habits");
        for (Object[] item : data.GoalHabitSetup) {
            System.out.println("Goal: " + item[data.INDEX_GOALNAME] + " Habits: " + item[data.INDEX_HABITSLIST]);
        }


    }



    /**@description:Asks the user for what quadrant he wants to put each of his goals
     *
     * call the storage function in data and prints it out
     * @param:none
     * @return:none
     * @author: Sanbeer
     */
    private static void menuEisenhowerMatrix(){
        ArrayList<String> goalsArrayList = data.getGoalsArrayList();
        ArrayList<Integer> choicesArrayList = new ArrayList<>();
        String[] categories = {"1) Urgent & Important", "2) Urgent & Not Important", "3) Important & Not Urgent",
                "4) Not Important & Not Urgent"};

        for (int i = 0; i < data.getGoalsArrayList().size(); i++) {
            System.out.println("The 4 Quadrants are \"1) Urgent & Important\", \"2) Urgent & Not Important\", \"3) Important & Not Urgent\",\n" +
                    " \"4) Not Important & Not Urgent");
            System.out.println("What quadrant in the matrix would you like to choose for goal: " + goalsArrayList.get(i));
            int chosenSection = scanner.nextInt();
            choicesArrayList.add(chosenSection);
        }

        System.out.println(data.storeEisenhowerMatrix(choicesArrayList));
        scanner.nextLine();

    }

    private static void menuCategorizeGoals() {
        ArrayList<String> goalsArrayList2 = data.getGoalsArrayList();
        String[] categories2 = {"1) Finance", "2) Work", "3) School", "4) Emotional", "5) Spiritual", "6) Social"};
        for (int i = 0; i < data.getGoalsArrayList().size(); i++) {
            System.out.println("The 6 Categories are \"1) Finance\", \"2) Work\", \"3) School\", \"4) Emotional\", \n" +
                    "\"5) Spiritual\", \"6) Social");
            System.out.println("What category of goals would you like to choose for goal: " + goalsArrayList2.get(i));
            int chosenSection2 = scanner.nextInt();
            data.choicesArrayList2.add(chosenSection2);
//            data.setChoicesArrayList2(chosenSection2);

        }
        System.out.println(data.storeCategorizeGoals(data.choicesArrayList2));
        scanner.nextLine();

    }



    /**@description:Updates the completion counts for habits.
     *
     *calls the {@code updateHabitCompletionCounts} method from the {@code Data} class,which handles the specifics of updating these counts.
     * This abstraction allows for a separation of concerns,where this method deals with the user interface aspect, and the {@code Data} class handles data manipulation.
     *
     * @param:
     * @return:
     * @author: Phone
     */
    private static void menuAddPointsToHabit() {
        // Invoke the updateHabitCompletionCounts method to update completion counts for habits.
        // This call reflects an action where user-earned points towards habit completion are modified.
        data.updateHabitCompletionCounts(); // Correctly invoke the update method here
    }

    /**@description: Prompts the user to decide if they want to see their weekly habit completion rate for each habit.
     *
     *This method first retrieves two pieces of information: the ideal goal points for each habit and the actual points earned.
     *Based on the user's response, the method either calculates and displays the completion rates or exits back to the main menu.
     *completion rates are calculated by comparing the actual points earned against the ideal goal points for each habit.
     *
     * @param: None
     * @return: None
     * @author: Phone
     */
    private static void menuWeeklyHabitCompletionRate() {
        // Retrieve goal points and the actual earned points for each habit
        HashMap<String, Integer> habitAndGoals = data.habitAndIdealCount();
        HashMap<String, Integer> habitsAndEarned = data.habitsAndEarned();
        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;

        do {
            // Prompt the user for their preference on viewing the weekly completion percentage
            System.out.println("Do you want to see your weekly completion percentage for each habit (type yes or no)?");
            String response = scanner.next().trim().toLowerCase();

            // Determine the user's response
            boolean shouldPrint = response.equals("yes") || response.equals("true");
            boolean shouldNotPrint = response.equals("no") || response.equals("false");
            scanner.nextLine(); // Consume any leftover newline character in the buffer

            if (shouldPrint) {
                // If the user wants to see the completion rate, calculate and display it
                HashMap<String, Integer> rate = data.menuWeeklyHabitCompletionRate(habitAndGoals, habitsAndEarned, shouldPrint);
                validInput = true; // Mark the input as valid to exit the loop
            } else if (shouldNotPrint) {
                // If the user opts not to see the completion rate, exit back to the menu
                System.out.println("Heading Back to menu");
                validInput = true; // Mark the input as valid to exit the loop
            } else {
                // Handle invalid input by asking again
                System.out.println("Invalid input. Please type 'yes' or 'no'.");
            }
        } while (!validInput); // Repeat until a valid input is received
    }

    /**@description: Displays the top 3 habits based on comparisons between goals and actual earned points.
     *
     *  retrieves two sets of data from the Data class: one representing the ideal goal points for each habit, and another showing the actual points earned from those habits.
     *  calculates the top 3 habits by comparing these sets of data. The results are summarized and printed to the console.
     *
     * @param: None
     * @return: None
     * @author: Phone
     */
    private static void menuTop3Habits() {
        // Retrieve habit goals and the actual earned points for each habit from the Data class
        // habitAndGoals maps each habit to its ideal goal points
        HashMap<String, Integer> habitAndGoals = data.habitAndIdealCount();

        // habitsAndEarned maps each habit to the points actually earned by the user
        HashMap<String, Integer> habitsAndEarned = data.habitsAndEarned();

        // Assuming the correct method to call is something like calculateTop3Habits instead of menuTop3Goals
        // This function would compare the ideal goals and actual points to determine the top 3 habits
        String top3HabitsSummary = data.menuTop3Habits(habitAndGoals, habitsAndEarned);

        // Print the summary of the top 3 habits to the console
        System.out.println(top3HabitsSummary);
    }

    /**@description: Resets the application data based on user confirmation
     *
     * @param: None
     * @return None
     * @author: Phone
     */
    private static void menuResetData() {
        Scanner scanner = new Scanner(System.in);
        boolean validInput = false; // Flag to track if the user input is valid

        do {
            System.out.println("After you reset the data, you will not be able to retrieve any history.");
            System.out.println("Do you still want to reset? (Type yes or no):");
            String confirmation = scanner.next().trim().toLowerCase();

            boolean delete = confirmation.equals("yes") || confirmation.equals("true");
            boolean notDelete = confirmation.equals("no") || confirmation.equals("false");

            if (delete) {
                String result = data.menuResetData(); // Ensure this method name matches your Data class method name
                System.out.println(result);
                validInput = true; // Mark input as valid to exit the loop
            } else if (notDelete) {
                System.out.println("Heading Back to Menu");
                validInput = true; // Mark input as valid to exit the loop
            } else {
                System.out.println("Invalid input. Please type 'yes' or 'no'.");
                // Loop will continue due to invalid input
            }
        } while (!validInput); // Loop until a valid input is received
    }



}


