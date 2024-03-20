package core.objects;

import java.util.ArrayList;
import java.util.HashMap;

public class Habit extends Goal{

    private String habitName;
    private Integer currentCount;


    public Habit(String goal, Integer idealCount, String category, Integer currentCount,String habitName){
        super(goal, idealCount, category);
        this.habitName = habitName;
        this.currentCount = currentCount;
    }

    public String getHabit() {
        return habitName;
    }

    public void setHabit(String habit) {
        this.habitName = habit;
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
                "habit='" + habitName + '\'' +
                ", currentCount=" + currentCount +
                '}';
    }





}
