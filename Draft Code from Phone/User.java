//import java.util.*;
//
//// The User class implements the Comparable interface, which means objects of this class can be ordered.
//public class User implements Comparable<User> {
//    // The User class has two fields: name and completionRate.
//    String name;
//    double completionRate;
//
//    // The User constructor takes a name and a completionRate and initializes the fields.
//    public User(String name, double completionRate) {
//        this.name = name;
//        this.completionRate = completionRate;
//    }
//
//    // The compareTo method is required for any class that implements Comparable.
//    // This method defines the natural ordering of User objects.
//    // In this case, Users are ordered by their completionRate in descending order.
//    @Override
//    public int compareTo(User other) {
//        return Double.compare(other.completionRate, this.completionRate);
//    }
//}
//
//// The HabitTracker class contains methods related to tracking habits.
//public class HabitTracker {
//
//    // Other methods...
//
//    // The rankUsers method takes a list of Users and sorts them based on their natural ordering.
//    // Because User implements Comparable and defines a compareTo method,
//    // the sort method knows how to order Users.
//    // After sorting the users, it returns the sorted list.
//    public static List<User> rankUsers(List<User> users) {
//        Collections.sort(users);
//        return users;
//    }
//}
