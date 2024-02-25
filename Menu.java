import java.util.ArrayList;
import java.util.Scanner;


/** This class interacts with the user to get inputs. It displays the menu of the habit tracker.
 * Names: Tania Rizwan, Sanbeer Shafin, Phone Myat
 * Tutorial:(fill)
 */

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

    }
    private static void menuCreateAccount() {

    }
    private static void menuLogIn() {

    }


    private static void menuCreateGoals() {
    }

    private static void menuHabitSetup() {

    }


}
