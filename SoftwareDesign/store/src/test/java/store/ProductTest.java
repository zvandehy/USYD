package store;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProductTest {

    @Test
    public void testValidProductConstructor() {
        try {
            Product product1 = new Product("Muffins", 5.50);
            Product product2 = new Product("Oranges", 1.00);
            Product product3 = new Product("Chocolate Chip", .01);
            Product product4 = new Product("Expensive Product", Double.MAX_VALUE);
        } catch (Exception e) {
            fail("Should not throw exception on valid constructors");
        }
    }

    @Test(expected =  IllegalArgumentException.class)
    public void testIllegalName_Null() {
        Product product = new Product(null,1.00);
    }
    @Test(expected =  IllegalArgumentException.class)
    public void testIllegalName_Empty() {
        Product product = new Product("",1.00);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testIllegalPrice_Zero() {
        Product product = new Product("Muffins", 0);
    }
    @Test (expected = IllegalArgumentException.class)
    public void testIllegalPrice_Negative() {
        Product product = new Product("Muffins", -0.01);
    }

    @Test
    public void testGetName() {
        Product product = new Product("Muffins", 1.00);
        assertEquals("Muffins", product.getName());
    }

    @Test
    public void testGetPrice() {
        Product product = new Product("Muffins", 1.00);
        assertEquals(1.00, product.getPrice());
    }
}
