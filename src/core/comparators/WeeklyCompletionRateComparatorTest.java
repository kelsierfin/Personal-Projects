package core.comparators;

import core.objects.Habit;
import core.comparators.WeeklyCompletionRateComparator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WeeklyCompletionRateComparatorTest {

    private Habit habitHigh;
    private Habit habitLow;
    private Habit habitMedium;
    private WeeklyCompletionRateComparator comparator;

    @BeforeEach
    void setUp() {
        // Initialize habits with different weekly completion rates
        habitHigh = new Habit("Exercise", 7, "a", 7, "importance");
        habitMedium = new Habit("Read", 7, "b", 4, "not importance");
        habitLow = new Habit("Study", 7, "c", 1, "very importance");

        // Initialize the comparator
        comparator = new WeeklyCompletionRateComparator();
    }

    @Test
    void testHigherCompletionRateFirst() {
        // Test that higher completion rate comes before lower rate
        assertTrue(comparator.compare(habitMedium, habitHigh) > 0);
        assertTrue(comparator.compare(habitMedium, habitLow) < 0);
    }

    @Test
    void testLowerCompletionRateFirst() {
        // Test that lower completion rate comes after higher rate
        assertTrue(comparator.compare(habitLow, habitMedium) > 0);
        assertTrue(comparator.compare(habitMedium, habitHigh) > 0);
    }

    @Test
    void testEqualCompletionRate() {
        // Test that equal completion rates are considered equal
        Habit habitEqualHigh = new Habit("Yoga", 7, "a", 7, "Not importance"); // Same completion rate as habitHigh
        assertEquals(0, comparator.compare(habitHigh, habitEqualHigh));
    }

    @Test
    void testComparisonConsistency() {
        // Test consistency of comparisons
        assertEquals(-comparator.compare(habitLow, habitHigh), comparator.compare(habitHigh, habitLow));
    }

    @Test
    void testNullHabitComparison() {
        // Test that comparing with null throws an exception
        assertThrows(NullPointerException.class, () -> comparator.compare(habitHigh, null));
    }
}
