package au.edu.sydney.soft3202.tutorials.week8.value;

import org.junit.Test;

import static org.junit.Assert.*;

public class AgeTest {
    @Test
    public void testAge() {
        Age firstAge17 = new Age(17);
        Age secondAge17 = new Age(17);
        Age firstAge20 = new Age(20);

        assertFalse(firstAge17 == secondAge17);

        // This one uses .equals internally
        assertEquals(firstAge17, secondAge17);

        assertFalse(firstAge17 == firstAge20);
        assertNotEquals(firstAge17, firstAge20);
    }
}