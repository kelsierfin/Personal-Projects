package core.comparators;

import java.util.Comparator;
import core.objects.Goal;

public class GoalCountComparators implements Comparator<Goal>{
    @Override
    public int compare(Goal o1, Goal o2) {
        int comp = o1.getIdealCount().compareTo(o2.getIdealCount());
        return comp;
    }

}
