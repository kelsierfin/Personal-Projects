import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Data {

    // Create an ArrayList to store all accounts
//    public static final ArrayList<Object[]> AccountDatabase = new ArrayList<>(); // ArrayList to store all accounts.
//    public static final int INDEX_USERNAME = 0;
//    public static final int INDEX_PASSWORD = 1;
//
//    /**
//     * This function sets up a user account if an account with the same username does not exist.
//     * @author Tania
//     * @param username
//     * @param password
//     * @return true if an account can be created /false if an account already exists
//     */
//    public static boolean initializeUser(String username, String password) {
//
//        if (!userExists(username)){
//            Object[] account = new Object[2];
//            account[INDEX_USERNAME] = username;
//            account[INDEX_PASSWORD] = password;
//            AccountDatabase.add(account);
//
//            // Potential future implementation:
////            // Create textfile to store user data
////            String filename = "userfile.txt";
////            String content = username + password;
////
////            try {
////                FileWriter fileWriter = new FileWriter(filename);
////                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
////                bufferedWriter.write(content);
////                bufferedWriter.close();
////                System.out.println("Your user file has been created!");
////            } catch (IOException e){
////                System.out.println("Error while creating file");
////            }
//
//            System.out.println("Your account has been created! Note down your username: " + username + " and password: " + password + " to log in next time.");
//            return true;
//        } if (userExists(username)) {
//            System.out.println("Your account already exists. Please log in with your username: " + username);
//            return false;
//        }
//        return false;
//    }
//
//    /**
//     * This function checks if an account with the input username exists. (Accounts can have same password, but not username).
//     * @author: Tania
//     * @param username
//     * @return true if an account already exists / false if it does not
//     */
//
//    public static boolean userExists(String username) {
//        for (Object[] account : AccountDatabase) {
//            if (account[INDEX_USERNAME].equals(username)) { // || account[INDEX_PASSWORD].equals(password)
//                return true;
//            }
//        }
//        return false;
//    }
}
