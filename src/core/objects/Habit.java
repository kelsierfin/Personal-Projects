package core.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

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
    public boolean equals(Object obj){
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Habit other = (Habit) obj;
        return Objects.equals(this.habitName, other.habitName);
    }

    @Override
    public int hashCode() {
        return habitName.hashCode();
    }

    @Override
    public String toString() {
        return "{" +
                "habit='" + habitName + '\'' +
                ", currentCount=" + currentCount +
                '}';
    }





}
