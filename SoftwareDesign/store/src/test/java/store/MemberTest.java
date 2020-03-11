package store;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class MemberTest {

    Product product1;
    Product product2;
    Product product3;
    Product product4;

    @Before
    public void setup() {
        product1 = new Product("Muffins", 5.50);
        product2 = new Product("Chocolate Chip", .01);
        product3 = new Product("Enchiladas", 13.01);
        product4 = new Product("Cheese", 100.0);
    }
    //Test Constructor
    @Test
    public void testMemberConstructor() {
        try {
            Member m1 = new Member();
        } catch(Exception e) {
            fail("Should not throw exception on constructor");
        }
        assertTrue(true);
    }

    @Test
    public void testSelectProduct() {
        //
    }

}
