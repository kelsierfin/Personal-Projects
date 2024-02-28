import java.util.*;

public class Menu2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HashMap<String, Integer> goalPoints = new HashMap<>();
        goalPoints.put("Reading", 100);
        goalPoints.put("Swimming", 50);
        goalPoints.put("Editing", 75);
        goalPoints.put("Coding", 200);

        HashMap<String, Integer> earnedPoints = new HashMap<>();
        earnedPoints.put("Reading", 40);
        earnedPoints.put("Swimming", 10);
        earnedPoints.put("Editing", 45);
        earnedPoints.put("Coding", 160);

        System.out.println("Do you want to see your weekly completion percentage for each habit (type yes or no)?");
        String response = scanner.next().trim().toLowerCase();
        boolean answer = response.equals("yes") || response.equals("true");
        calculateCompletionRates(goalPoints, earnedPoints, answer);

        System.out.println("Do you want to see your most completed habits for this week (type yes or no)?");
        response = scanner.next().trim().toLowerCase();
        boolean answer2 = response.equals("yes") || response.equals("true");
        if(answer2){
            top3Habits(goalPoints, earnedPoints);
        }
    }

    public static HashMap<String, Integer> calculateCompletionRates(HashMap<String, Integer> goalPoints, HashMap<String, Integer> earnedPoints, boolean shouldPrint) {
        HashMap<String, Integer> rates = new HashMap<>();
        StringBuilder output = new StringBuilder();

        for (String habit : goalPoints.keySet()) {
            int goal = goalPoints.getOrDefault(habit, 0);
            int earned = earnedPoints.getOrDefault(habit, 0);
            int rate = 0;
            if (goal != 0) {
                rate = (int) (((double) earned / goal) * 100);
            }
            rates.put(habit, rate);
            if (shouldPrint) {
                output.append(String.format("%s Habit is %d%% completed according to this weekly target.\n", habit, rate));
            }
        }

        if (shouldPrint) {
            System.out.println(output.toString());
        }

        return rates;
    }

    public static void top3Habits(HashMap<String, Integer> goalPoints, HashMap<String, Integer> earnedPoints) {
        HashMap<String, Integer> rates = calculateCompletionRates(goalPoints, earnedPoints, false); // Updated call
        List<Map.Entry<String, Integer>> list = new ArrayList<>(rates.entrySet());
        list.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        StringBuilder output = new StringBuilder();
        output.append(list.size() >= 3 ? "Top 3 habits for this week are " : "Mostly completed habits are ");

        int limit = Math.min(list.size(), 3);
        for (int i = 0; i < limit; i++) {
            Map.Entry<String, Integer> entry = list.get(i);
            output.append(entry.getKey());
            if (i < limit - 1) {
                output.append(", ");
            }
        }

        output.append(" in descending order.");
        System.out.println(output.toString());
    }

}