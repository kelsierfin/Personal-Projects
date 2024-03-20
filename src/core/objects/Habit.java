package core.objects;

import java.util.ArrayList;
import java.util.HashMap;

public class Habit extends Goal{

    private String habit;

    private Integer currentCount;

    private ArrayList<String> habitsList;


    public Habit(String goal, Integer idealCount, String category, Integer currentCount,String habit){
        super(goal, idealCount, category);
        this.habit = habit;
        this.currentCount = currentCount;
    }

    public String getHabit() {
        return habit;
    }

    public void setHabit(String habit) {
        this.habit = habit;
    }

    public Integer getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(Integer currentCount) {
        this.currentCount = currentCount;
    }

    @Override
    public String toString() {
        return "HabitsList{" +
                "habit='" + habit + '\'' +
                ", currentCount=" + currentCount +
                '}';
    }





}
