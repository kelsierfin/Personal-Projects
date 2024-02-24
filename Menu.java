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
    static {
        options.add("Enter your name: "); // At index 1
        options.add("Exit"); // Make this the last option
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


    public static void menuLoop() {
        System.out.print(optMessage);
        String choice = scanner.nextLine();
        int option = Integer.parseInt(choice);

        while (option != options.size() - 1) {
            System.out.print(optMessage);
            choice = scanner.nextLine();
            option = Integer.parseInt(choice);
        }

        System.out.println("Thank you for using the habit and goal tracker! See you tomorrow (hopefully)!");

    }
}
