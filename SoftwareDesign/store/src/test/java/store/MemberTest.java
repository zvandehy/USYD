package store;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

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

    @Test (expected = IllegalArgumentException.class)
    public void testSelectProduct_NullProduct() {
        Member member = new  Member();
        member.selectProduct(null,1);
    }
    @Test (expected = IllegalArgumentException.class)
    public void testSelectProduct_ZeroQuantity() {
        Member member = new  Member();
        member.selectProduct(product1,0);
    }
    @Test (expected = IllegalArgumentException.class)
    public void testSelectProduct_NegativeQuantity() {
        Member member = new  Member();
        member.selectProduct(product1,-1);
    }
    @Test
    public void testSelectProduct_QuantityTooHigh() {
        Member member = new  Member();
        Inventory.getInstance().addProduct(product1,1);
        try {
            member.selectProduct(product1,2);
            fail("global inventory does not have the required amount of Product");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
        //maintain inventory
        Inventory.getInstance().takeProduct(product1,1);
    }
    @Test
    public void testSelectProduct_SelectMultiple() {
        Member member = new  Member();
        Inventory.getInstance().addProduct(product1,5);
        member.selectProduct(product1,2);
        //verify product taken from inventory
        assertEquals(3, Inventory.getInstance().getProductQuantity(product1));
        //5.50 + 5.50 = 11.00
        //verify value when finalised is appropriate
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        member.finalisePurchases();
        assertTrue(outContent.toString().contains("11.0"));
        //maintain inventory
        Inventory.getInstance().takeProduct(product1,3);
    }
    @Test
    public void testSelectProduct_SelectDistinct() {
        Member member = new  Member();
        Inventory.getInstance().addProduct(product1,5);
        Inventory.getInstance().addProduct(product2,5);
        member.selectProduct(product1,1);
        member.selectProduct(product2,1);
        //verify product taken from Inventory
        assertEquals(4, Inventory.getInstance().getProductQuantity(product1));
        assertEquals(4, Inventory.getInstance().getProductQuantity(product2));
        //5.50 + .01 = 5.51
        //verify value of cart when finalised is appropriate
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        member.finalisePurchases();
        assertTrue(outContent.toString().contains("5.51"));
        //maintain inventory
        Inventory.getInstance().takeProduct(product1,4);
        Inventory.getInstance().takeProduct(product2,4);
    }
    @Test
    public void testReturnProduct_ReturnOnlyProduct() {
        Member member = new  Member();
        Inventory.getInstance().addProduct(product1,5);
        member.selectProduct(product1,1);
        member.returnProduct(product1,1);
        //verify product returned to inventory
        assertEquals(5, Inventory.getInstance().getProductQuantity(product1));
        //verify cart value is zero
        //ASSUMPTION: cart is charged to account even when it is empty, value is 0.00
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        member.finalisePurchases();
        assertTrue(outContent.toString().contains("0.00"));
        //maintain inventory
        Inventory.getInstance().takeProduct(product1,5);
    }
    @Test
    public void testReturnProduct_ReturnMultiple() {
        Member member = new  Member();
        Inventory.getInstance().addProduct(product1,5);
        member.selectProduct(product1,3);
        member.returnProduct(product1,2);
        //5.50
        //verify product returned to inventory
        assertEquals(4, Inventory.getInstance().getProductQuantity(product1));
        //verify cart value
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        member.finalisePurchases();
        assertTrue(outContent.toString().contains("5.50"));
        //maintain inventory
        Inventory.getInstance().takeProduct(product1,4);
    }
    @Test (expected = IllegalArgumentException.class)
    public void testReturnProduct_NullProduct() {
        Member member = new  Member();
        member.returnProduct(null,1);
    }
    @Test (expected = IllegalArgumentException.class)
    public void testReturnProduct_ZeroQuantity() {
        Member member = new  Member();
        member.returnProduct(product1,0);
    }
    @Test (expected = IllegalArgumentException.class)
    public void testReturnProduct_NegativeQuantity() {
        Member member = new  Member();
        member.returnProduct(product1,-1);
    }
    @Test
    public void testReturnProduct_QuantityTooHigh() {
        Member member = new  Member();
        Inventory.getInstance().addProduct(product1,5);
        try {
            member.selectProduct(product1,2);
            //11.00
            //verify product returned to inventory
            assertEquals(3, Inventory.getInstance().getProductQuantity(product1));
            member.returnProduct(product1,3);
            fail("member does not have the required amount of Product to return");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
        //verify Inventory wasn't changed
        assertEquals(3, Inventory.getInstance().getProductQuantity(product1));
        //verify cart had $11.00
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        member.finalisePurchases();
        assertTrue(outContent.toString().contains("11.00"));
        //maintain inventory
        Inventory.getInstance().takeProduct(product1,3);
    }
    @Test
    public void testReturnProduct_Valid() {
        Member member = new  Member();
        Inventory.getInstance().addProduct(product1,5);
        Inventory.getInstance().addProduct(product2,5);
        member.selectProduct(product1,2);
        member.selectProduct(product2,4);
        member.returnProduct(product1,1);
        //1 in cart, 4 in inventory
        member.returnProduct(product2,3);
        //1 in cart, 4 in inventory
        //cart value is 5.50 + .01
        //verify appropriate inventory
        assertEquals(4, Inventory.getInstance().getProductQuantity(product1));
        assertEquals(4, Inventory.getInstance().getProductQuantity(product2));
        //verify cart value
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        member.finalisePurchases();
        assertTrue(outContent.toString().contains("5.51"));
        //maintain inventory
        Inventory.getInstance().takeProduct(product1,4);
        Inventory.getInstance().takeProduct(product2,4);
    }
    @Test
    public void testFinalisePurchases_EmptyCart() {
        Member member = new  Member();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        member.finalisePurchases();
        assertTrue(outContent.toString().contains("0.00"));
    }
    @Test
    public void testFinalisePurchases_CartWithItems() {
        Member member = new  Member();
        Inventory.getInstance().addProduct(product1,1);
        Inventory.getInstance().addProduct(product2,2);
        Inventory.getInstance().addProduct(product3,3);
        member.selectProduct(product1,1);
        member.selectProduct(product2,2);
        member.selectProduct(product3,3);
        //5.50 + .01 *2 + 13.01 * 3 == 5.52 + 39.03 == 44.55
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        member.finalisePurchases();
        assertTrue(outContent.toString().contains("44.55"));
    }
    @Test
    public void testFinalisePurchases_CartIsCleared() {
        Member member = new  Member();
        Inventory.getInstance().addProduct(product1,1);
        member.selectProduct(product1,1);
        //first call prints 5.50 and clears cart
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        member.finalisePurchases();
        assertTrue(outContent.toString().contains("5.50"));
        //second call prints 0 because cart is empty
        member.finalisePurchases();
        assertTrue(outContent.toString().contains("0.00"));
    }
}
