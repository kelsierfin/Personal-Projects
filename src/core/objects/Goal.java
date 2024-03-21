package core.objects;

import java.util.HashMap;
import java.util.Objects;

public class Goal implements Comparable<Goal>{

    // Object fields
    private String goalName;
    private Integer idealCount;
    private String category;
    private Integer currentCount;

    // Constructor
    public Goal (String goal, Integer idealCount, String category){
        this.goalName = goal;
        this.idealCount = idealCount;
        this.category = category;
    }

    // Getters
    public String getGoal(){
        return this.goalName;
    }

    public Integer getIdealCount(){
        return this.idealCount;
    }

    public String getCategory(){
        return this.category;
    }

    // Setters
    public void setGoal(String goalName){
        this.goalName = goalName;
    }

    public void setIdealCount(Integer idealCount) {
        this.idealCount = idealCount;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public int compareTo(Goal goal){
        return Integer.compare(idealCount, goal.idealCount);
    }


    /**
     * Override Equals to compare goalNames when creating a goal.
     * @param obj Object to compare
     * @return boolean True if goal exists, False otherwise
     */
    @Override
    public boolean equals(Object obj){
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Goal other = (Goal) obj;
        return Objects.equals(this.goalName, other.goalName);
    }

    /**
     * Return the hashcode for a goal. To be used in Equals
     * @return hashcode integer
     */

    @Override
    public int hashCode() {
        return goalName.hashCode();
    }


    // To String
    @Override
    public String toString() {
        return "Goal: " + goalName + " " +
                "IdealCount: " + idealCount + " " +
                "Category: " + category;
    }

}
