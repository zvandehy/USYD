import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class YearTest {

    @Test
    public void increment() {
        Year one = new Year(1);
        assertEquals(new Year(2), one.increment());
        assertEquals(1, one.year());
        assertEquals(new Year(2), one.increment());
    }

    @Test
    public void year() {
        Year five = new Year(5);
        assertEquals(5, five.year());
    }

    @Test
    public void decrement() {
        Year ten = new Year(10);
        assertEquals(new Year(9), ten.decrement());
        assertEquals(10, ten.year());
        assertEquals(new Year(9), ten.decrement());
    }

    @Test
    public void compareTo() {
        Year one = new Year(2);
        assertEquals(1, one.compareTo(new Year(1)));

        assertEquals(0, one.compareTo(new Year(2)));

        assertEquals(-1, one.compareTo(new Year(3)));
    }

    @Test
    public void diff() {
        Year ten  = new Year(10);
        assertEquals(new Year(15), ten.diff(5));
        assertEquals(new Year(5), ten.diff(-5));
        assertEquals(ten, ten.diff(0));
    }

}
