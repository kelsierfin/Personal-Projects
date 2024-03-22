package core.util;


import core.objects.Goal;
import core.objects.Habit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;

public class FileSaver {
    public static boolean save(File file, Data data) {
        try(FileWriter fw = new FileWriter(file)){
            // Write CSV header
            fw.write("Goals and Habits\n");

            for (Map.Entry<Goal, HashSet<Habit>> entry : data.tracker.entrySet()) {
                fw.write(String.format("%s, %d, %s", entry.getKey().getGoal(), entry.getKey().getIdealCount(), entry.getKey().getCategory()));
                for (Habit habit : entry.getValue()) {
                    fw.write(String.format("%s, %d, %s, %d, %s", habit.getGoal(), habit.getIdealCount(), habit.getCategory(), habit.getCurrentCount(), habit.getHabit()));
                }
            }
            fw.flush();

        } catch (IOException e) {
            return false;
        }
        return true;


    }
}
