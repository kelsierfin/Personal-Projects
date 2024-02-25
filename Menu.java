import java.util.ArrayList;
import java.util.Scanner;


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
        options.add("Create an Account");
        options.add("Log In");
        options.add("Create goals");
        options.add("Add habits to goal");
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
                case 1 -> menuCreateAccount();
                case 2 -> menuLogIn();
                case 3 -> menuCreateGoals();
                case 4 -> menuHabitSetup();
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

    /** Description: This function creates a user account if one does not exist in our database.
     *
     */

    private static void menuCreateAccount() {
        boolean accountExists;
        String username;
        String password;
        do {
            username = getUserName();
            password = getPassword();
            accountExists = Data.userAccount(username, password);
        } while(!accountExists);
    }

    /** Description: This function prompts user for their account username
     * @return name
     */
    private static String getUserName() {
        System.out.println("Enter your username");
        String name;
        do{
            name = scanner.nextLine().trim(); // trim() removes whitespaces
        } while(name.isEmpty()); // user input for name should not be empty
        return name;
    }

    /**
     * Description: This function prompts the user for their account password.
     * The password must be at least 3 digits long, so it is treated as a String.
     * @return password
     */
    private static String getPassword() {
        System.out.println("Enter your password (at least 3 digits)");
        String password; // Treat as string because we have a limitation on number of digits.
        do{
            password = scanner.nextLine();
        } while(password.length() < 3 || password.isEmpty()); // user input for password should be at least 3 digits
        return password;
    }



    private static void menuLogIn() {

    }


    private static void menuCreateGoals() {
    }

    private static void menuHabitSetup() {

    }



}
