//import java.util.HashMap;
//
//public class HabitTracker {
//
//    /**
//     * Calculates the completion rate for a goal.
//     *
//     * @param userHabits A HashMap where each key-value pair represents a habit and its associated points.
//     *                   The keys are of type String (representing the habit names),
//     *                   and the values are of type Integer (representing the points for each habit).
//     * @param pointsRequiredForGoal An integer representing the total points required to achieve a goal.
//     * @return The completion rate of the goal. If pointsRequiredForGoal is 0, it returns 0 to avoid division by zero.
//     *         Otherwise, it calculates the completion rate as the total points from userHabits divided by
//     *         pointsRequiredForGoal, multiplied by 100 to get a percentage.
//     */
//    public static double calculateCompletionRate(HashMap<String, Integer> userHabits, int pointsRequiredForGoal) {
//        // Check if pointsRequiredForGoal is 0 to avoid division by zero
//        if (pointsRequiredForGoal == 0) {
//            return 0;
//        }
//
//        // Initialize totalPoints to 0. This variable is used to store the sum of all the points from userHabits.
//        int totalPoints = 0;
//
//        // Iterate over the values of userHabits (which are the points for each habit),
//        // and add each of these points to totalPoints.
//        for (int points : userHabits.values()) {
//            totalPoints += points;
//        }
//
//        // Calculate the completionRate by dividing totalPoints by pointsRequiredForGoal,
//        // multiplying the result by 100 to get a percentage,
//        // and casting totalPoints to double to allow for decimal precision.
//        double completionRate = ((double) totalPoints / pointsRequiredForGoal) * 100;
//
//        // Return the completion rate
//        return completionRate;
//    }
//}
