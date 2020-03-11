/*
* This Java source file was generated by the Gradle 'init' task.
*/

import javafx.util.Pair;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class AppTest {

    //Todo: Should move these "megaTests" into more specific tests
    @Test
    public void ShoppingBasket() {

    }

    @Test public void addItem() {
        //setup
        ShoppingBasket basket = new ShoppingBasket();
        String grocery1 = "apple";
        String grocery2 = "orange";
        String grocery3 = "pear";
        String grocery4 = "banana";

        //initialized as empty
        assertTrue(basket.getItems().isEmpty());

        //check normal case that values are added
        // ASSUME that grocery items is an underlying data structure that stores the # of items
        basket.addItem(grocery1,1);
        assertEquals(1, basket.getItems().size());
        assertTrue(basket.getItems().contains(new Pair<String,Integer>(grocery1,1)));
        basket.addItem(grocery2,2);
        assertEquals(2, basket.getItems().size());
        assertTrue(basket.getItems().contains(new Pair<String,Integer>(grocery2,2)));
        basket.addItem(grocery3,3);
        assertEquals(3, basket.getItems().size());
        assertTrue(basket.getItems().contains(new Pair<String,Integer>(grocery3,3)));

        //size shouldn't change when item duplicate input is added
        basket.addItem(grocery1,1);
        assertEquals(3, basket.getItems().size());
        assertTrue(basket.getItems().contains(new Pair<String,Integer>(grocery1,2)));

        //testing invalid argument exception
        try {
            basket.addItem(null,1);
            fail("Should not reach. ArgumentException should be caught");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            basket.addItem("",1);
            fail("Should not reach. ArgumentException should be caught");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            basket.addItem("other grocery item",1);
            fail("Should not reach. ArgumentException should be caught");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        //testing number format exception
        try {
            basket.addItem(grocery1,-1);
            fail("Should not reach. NumberException should be caught");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            basket.addItem(grocery1,0);
            fail("Should not reach. NumberException should be caught");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test public void removeItem() {
        //setup
        ShoppingBasket basket = new ShoppingBasket();
        String grocery1 = "apple";
        String grocery2 = "orange";
        String grocery3 = "pear";
        String grocery4 = "banana";

        //initialized as empty
        assertTrue(basket.getItems().isEmpty());

        //remove 1 item when there is only 1 in the cart
        basket.addItem(grocery1,1);
        assertEquals(1, basket.getItems().size());
        assertFalse(basket.getItems().contains(grocery1));
        //removal
        assertTrue(basket.removeItem(grocery1, 1));
        //check that the item was removed from the list
        assertFalse(basket.getItems().contains(grocery1));
        assertTrue(basket.getItems().isEmpty());

        //remove 1 item when there is more than 1 in the cart
        basket.addItem(grocery3,3);
        assertEquals(1, basket.getItems().size());
        assertTrue(basket.getItems().contains(new Pair<String,Integer>(grocery3,3)));
        //removal
        assertTrue(basket.removeItem(grocery3, 1));
        //check there are still items in the cart
        assertEquals(1, basket.getItems().size());
        assertTrue(basket.getItems().contains(new Pair<String,Integer>(grocery3,2)));

        //remove 2 items when there is 2 in the cart
        assertTrue(basket.removeItem(grocery3, 2));
        assertTrue(basket.getItems().isEmpty());

        //testing invalid inputs
        assertFalse(basket.removeItem("", 1));
        assertFalse(basket.removeItem("other grocery item", 1));
        //testing illegal argument exception
        try {
            basket.removeItem(null, 1);
            fail("Should not reach. Illegal Argument Exception should be thrown");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        //testing number format exception
        try {
            basket.removeItem(grocery1, -1);
            fail("Should not reach. Number Format Exception should be thrown");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            basket.removeItem(grocery1, 0);
            fail("Should not reach. Number Format Exception should be thrown");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

    }

    @Test public void getItems() {
        ShoppingBasket basket = new ShoppingBasket();
        String grocery1 = "apple";
        String grocery2 = "orange";
        String grocery3 = "pear";
        String grocery4 = "banana";


        //ASSUME a grocery item is a mapping
        List<Pair<String, Integer>> groceryItems = new ArrayList<>();
        int count;

        //verify empty cart returns empty list
        assertTrue(basket.getItems().isEmpty());

        basket.addItem(grocery1,1);
        basket.addItem(grocery2,2);
        basket.addItem(grocery4,4);

        groceryItems = basket.getItems();

        //verify size is 3
        assertEquals(3, groceryItems.size());
        //verify contains the 3 added grocery items
        //also verify that the added items have the correct quantities
        assertTrue(groceryItems.contains(new Pair<String,Integer>(grocery1,1)));

        assertTrue(groceryItems.contains(new Pair<String,Integer>(grocery2,2)));

        assertTrue(groceryItems.contains(new Pair<String,Integer>(grocery4,4)));

    }

    @Test public void getValue() {
        ShoppingBasket basket = new ShoppingBasket();
        String grocery1 = "apple";
        String grocery2 = "orange";
        String grocery3 = "pear";
        String grocery4 = "banana";

        //verify returns Nothing (not 0)
        assertNull(basket.getValue());

        //test regular conditions
        basket.addItem(grocery1,1); //2.50
        basket.addItem(grocery2,2); //1.25 * 2
        basket.addItem(grocery3,1); //3.00
        basket.addItem(grocery4,4); //4.95 * 4
        double total = 2.50 + 1.25 * 2 + 3.00 + 4.95 * 4;

        //test overflow

    }

    @Test public void clear() {
        ShoppingBasket basket = new ShoppingBasket();
        String grocery1 = "apple";
        String grocery2 = "orange";
        String grocery3 = "pear";
        String grocery4 = "banana";
        basket.addItem(grocery1,1);
        basket.addItem(grocery2,2);
        basket.addItem(grocery3,3);
        basket.clear();
        assertTrue(basket.getItems().isEmpty());

    }
}