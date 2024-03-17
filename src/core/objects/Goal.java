package core.objects;

public class Goal {

    // Object fields
    private String goal;
    private Integer idealCount;
    private String category;


    // Constructor
    public Goal (String goal, Integer idealCount, String category){
        this.goal = goal;
        this.idealCount = 0;
        this.category = category;
    }

    // Getters
    public String getGoal(){
        return this.goal;
    }

    public Integer getIdealCount(){
        return this.idealCount;
    }

    public String getCategory(){
        return this.category;
    }

    // Setters
    public void setGoal(String goalName){
        this.goal = goalName;
    }

    public void setIdealCount(Integer idealCount) {
        this.idealCount = idealCount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // To String
    @Override
    public String toString() {
        return "Goal{" +
                "goal='" + goal + '\'' +
                ", idealCount=" + idealCount +
                ", category='" + category + '\'' +
                '}';
    }

}
