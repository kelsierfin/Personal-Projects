import java.util.*;


/** This class interacts with the user to get inputs. It displays the menu of the habit tracker.
 * Names: Tania Rizwan, Sanbeer Shafin, Phone Myat
 * Tutorial:(fill)
 * Emails: tania.rizwan@ucalgary.ca, sanbeer.shafin@ucalgary.ca, phonemyat.paing@ucalgary.ca
 */

// The menu setup code was followed from Dr. Hudson's project example.
public class Menu {

    // Create scanner object for user input.
        // Private so it is only accessible within the Menu class.
        // Static because everything in this class can share the scanner object.
    private static final Scanner scanner = new Scanner(System.in);

    // Create menu for user interaction.
    // Use a static initializer. This initializes the ArrayList when we load the Menu class.
    private static final ArrayList<String> options = new ArrayList<>();
    static { // NOTE: This is to be edited.
        options.add("Exit"); // At index 0
//        options.add("Create an Account");
//        options.add("Log In");
        options.add("Create A Goal");
        options.add("Delete a Goal");
        options.add("Add Habits to A Goal");
        options.add("Delete Habits From A Goal");
        options.add("View Goals and Habits");
        options.add("Categorize Goals");
        options.add("Create Eisenhower Matrix");
        options.add("Add Points to Habit");
        options.add("List Productivity Summary");
        options.add("Weekly Goal Completion Rate");
        options.add("List Top 3 Habits of the Week");
    }


    // Create message to display to user. Then use static initializer to combine message and Options into one menu.
    private static String optMessage = """
            Welcome to your personal habit and goal tracker!
            Enter and track your habits for the week!
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
                System.out.printf("You have selected option %d: %s %n", option, options.get(option));
                System.out.println("Press Enter to continue:");
                scanner.nextLine();
            }

            switch (option) { // NOTE: Cases have to be updated
                case 1 -> menuCreateGoal();
                case 2 -> menuDeleteGoal();
                case 3 -> menuAddHabits();
                case 4 -> menuDeleteHabits();
                case 5 -> menuCheckingGoalsAndHabits();
                case 6 -> menuCategorizeGoals();
                case 7 -> menuEisenhowerMatrix();
                case 8 -> menuAddPointsToHabit();
                case 9-> menuListProductivitySummary();
                case 10 -> menuWeeklyGoalCompletionRate();
                case 11 -> menuTop3Habits();
                default -> System.out.printf("Option %d is not recognizable %n", option);
            }


            System.out.println();
            System.out.print("Press Enter to see the menu again");
            scanner.nextLine(); // WONT WORK
            System.out.println(optMessage);
            choice = scanner.nextLine();
            option = Integer.parseInt(choice);
        }
        System.out.printf("Thank you for using the habit and goal tracker!%nSee you tomorrow (hopefully)!");


        scanner.close();
    }


    /** This function allows users to create a Goal and assign a goalIdealCount to it,
     * which is the number of days they would like to work on the gal.
     * @author Tania
     */
    private static void menuCreateGoal() {
        String goalName;
        Integer goalIdealCount;
        Integer option;


        // Summary:
        // Ask user to input goal
        // Ask user to add a count to their goal
        // Populate HashMap with Key = goal and Value = goalIdealCount if it doesn't exist already
        // Ask user to enter another goal if they wish. Otherwise, exit.

        do {
            goalName = getGoalName(); // Ask user to input goal
            goalIdealCount = getIdealCount(); // Ask user to input num of days they want to work on their goal

            Data.createAGoal(goalName, goalIdealCount); // Send data to Data.java to populate hashmap

            System.out.println("Would you like to enter another goal? (Yes = any number | No = 0)");
            option = scanner.nextInt(); // Give user the choice to add more goals and counts

        } while(option != 0);
    }

    /** This function prompts the user for the name of their goal.
     * The goal cannot be an empty string.
     * @author Tania
     * @return goalName
     */

    private static String getGoalName() {
        String goalName;
        System.out.println("What is your goal?");

        do {
            goalName = scanner.nextLine();
        } while(goalName.isEmpty());

        return goalName;
    }

    /** This function prompts the user for the idealCount of their goal (number of days they want to work on their goal).
     * The idealCount cannot be 0, or greater than 7.
     * @author Tania
     * @return goalIdealCount
     */

    private static Integer getIdealCount() {
        Integer goalIdealCount;
        System.out.println("Add the number of days (1-7) you want to work on this goal:");

        do {
            goalIdealCount = scanner.nextInt();
        } while(goalIdealCount == 0 || goalIdealCount > 7);

        return goalIdealCount;
    }

    /** This function prompts the user for a goal to delete, and allows them to delete as many as they wish.
     * @author Tania
     */


    private static void menuDeleteGoal() {
        Integer option;

        do {
            // List all goals
            for (Map.Entry<String, Integer> entry: Data.GoalAndIdealCount.entrySet()) {
                System.out.println("Goal: " + entry.getKey());
            }

            System.out.print("Enter the name of the goal to remove:");
            String goalToDelete = scanner.nextLine();
            Data.goalDelete(goalToDelete);

            System.out.println("Would you like to retry or delete another goal? (Yes = any number | No = 0)");
            option = scanner.nextInt();
            scanner.nextLine(); // Consume the \n left in the buffer
        } while(option != 0);

    }



    private static void menuAddHabits() {

    }

    private static void menuListProductivitySummary() {

    }

    private static void menuAddPointsToHabit() {

    }

    private static void menuEisenhowerMatrix() {

    }

    private static void menuCategorizeGoals() {

    }

    private static void menuDeleteHabits() {

    }

    private static void menuCheckingGoalsAndHabits(){

    }

    private static void menuWeeklyGoalCompletionRate() {
        // Get goalPoints and habitCounts from Data class methods
        HashMap<String, Integer> goalPoints = Data.menuCheckingGoalsAndHabits();
        HashMap<String, Integer> habitCounts = Data.menuAddPointsToHabit();

        System.out.println("Do you want to see your weekly completion percentage for each habit (type yes or no)?");
        String response = scanner.next().trim().toLowerCase();
        boolean shouldPrint = response.equals("yes")||response.equals("true");
        boolean shouldNotPrint = response.equals("no")||response.equals("false");
        scanner.nextLine(); // Consume the \n left in the buffer

        if(shouldPrint){
            HashMap<String, Integer> rate = Data.menuWeeklyGoalCompletionRate(goalPoints, habitCounts, shouldPrint);
        } else if (shouldNotPrint) {
            System.out.println("Heading Back to menu");
        }else{
            System.out.println("Invalid input");
        }
    }

    private static void menuTop3Habits() {
        // Retrieve goalPoints and habitCounts from the respective Data class methods
        HashMap<String, Integer> goalPoints = Data.menuCheckingGoalsAndHabits();
        HashMap<String, Integer> habitCounts = Data.menuAddPointsToHabit();

        // Call the menuTop3Habits function with the retrieved data
        String top3HabitsSummary = Data.menuTop3Habits(goalPoints, habitCounts);

        // Print the result
        System.out.println(top3HabitsSummary);
    }

//    /** This function creates a user account if one does not exist in our database.
//     * @author: Tania
//     */
//
//    private static void menuCreateAccount() {
//        String username;
//        String password;
//
//        username = getUserName();
//        password = getPassword();
//        Data.initializeUser(username, password);
//
//    }
//
//    /** This function prompts user for their account username. It is used in menuCreateAccount and menuLogIn
//     * @author: Tania
//     * @return: name (String)
//     */
//    private static String getUserName() {
//        System.out.println("Enter your username");
//        String name;
//        do{
//            name = scanner.nextLine().trim(); // trim() removes whitespaces
//        } while(name.isEmpty()); // user input for name should not be empty
//        return name;
//    }
//
//    /**
//     * This function prompts the user for their account password.
//     * The password must be at least 3 digits long, so it is treated as a String.
//     * This function is used in menuCreateAccount and menuLogIn
//     * @author: Tania
//     * @return: password
//     */
//    private static String getPassword() {
//        System.out.println("Enter your password (at least 3 digits)");
//        String password; // Treat as string because we have a limitation on number of digits.
//        do{
//            password = scanner.nextLine();
//        } while(password.length() < 3); // user input for password should be at least 3 digits
//        return password;
//    }
//
//
//    private static void menuLogIn() {
//
//    }

}
