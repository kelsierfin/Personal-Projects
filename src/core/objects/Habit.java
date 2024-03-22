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

    public void incrementCurrentCount() {
        this.currentCount++;
    }

    /**
     * Calculates the weekly completion rate of a habit based on its current and ideal counts.
     * The completion rate is determined by dividing the current count by the ideal count,
     * and then multiplying by 100 to convert it into a percentage.
     *
     * If the ideal count is zero, indicating that no completion was expected, the method
     * returns a completion rate of 0.0 to avoid division by zero.
     *
     * @return The weekly completion rate as a double. This rate ranges from 0.0 (indicating no completion)
     *         to 100.0 (indicating full completion of the ideal count). If the ideal count is zero,
     *         the method returns 0.0.
     */
    public double getWeeklyCompletionRate() {
        if (this.getIdealCount() == 0) {
            return 0.0;
        }
        return ((double) this.currentCount / this.getIdealCount()) * 100;
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
