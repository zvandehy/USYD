/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package store;

import javafx.util.Pair;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class ShoppingBasketTest {

    Product product1;
    Product product2;
    Product product3;
    Product product4;

    @Mock
    ShoppingBasket mockBasket;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setupProducts() {
        product1 = new Product("Muffins", 5.50);
        product2 = new Product("Chocolate Chip", .01);
        product3 = new Product("Enchiladas", 13.01);
        product4 = new Product("Cheese", 100.0);
    }

    @Before
    public void setupMock() {
    }


    //Test Constructor
    @Test public void testShoppingBasketConstructor() {
        try {
            ShoppingBasket basket = mockBasket;
        } catch(Exception e) {
            fail("Should not throw exception on constructor");
        }
        assertTrue(true);
    }
    //Test AddItem
    @Test
    public void testAddNewItemsToEmptyBasket() {
        ShoppingBasket basket = new ShoppingBasketImpl();
        //adding various counts of product1 to an empty basket
        for(int i=1;i<=25;i++) {
            basket.addItem(product1,i);
            assertEquals(1, basket.getItems().size());
            assertTrue(basket.getItems().contains(new Pair<Product,Integer>(product1,i)));
            basket = new ShoppingBasketImpl();
        }
    }
    @Test
    public void testAddDuplicateItems() {
        ShoppingBasket basket = new ShoppingBasketImpl();
        //add one of product1
        basket.addItem(product1,1);
        assertEquals(1, basket.getItems().size());
        assertTrue(basket.getItems().contains(new Pair<Product,Integer>(product1,1)));
        //add five more
        basket.addItem(product1,5);
        assertEquals(1, basket.getItems().size());
        assertTrue(basket.getItems().contains(new Pair<Product,Integer>(product1,6)));
    }
    @Test
    public void testAddDistinctItems() {
        ShoppingBasket basket = new ShoppingBasketImpl();
        basket.addItem(product1,1);
        assertEquals(1,basket.getItems().size());
        assertTrue(basket.getItems().contains(new Pair<Product,Integer>(product1,1)));
        basket.addItem(product2,2);
        assertEquals(2,basket.getItems().size());
        assertTrue(basket.getItems().contains(new Pair<Product,Integer>(product2,2)));
        basket.addItem(product3,3);
        assertEquals(3,basket.getItems().size());
        assertTrue(basket.getItems().contains(new Pair<Product,Integer>(product3,3)));
        basket.addItem(product4,4);
        assertEquals(4,basket.getItems().size());
        assertTrue(basket.getItems().contains(new Pair<Product,Integer>(product4,4)));
    }
    @Test (expected = IllegalArgumentException.class)
    public void testAddNullProduct() {
        ShoppingBasket basket = new ShoppingBasketImpl();
        basket.addItem(null,1);
    }
    @Test (expected = IllegalArgumentException.class)
    public void testAddInvalidCount_Zero() {
        ShoppingBasket basket = new ShoppingBasketImpl();
        basket.addItem(product1,0);
    }
    @Test (expected = IllegalArgumentException.class)
    public void testAddInvalidCount_Negative() {
        ShoppingBasket basket = new ShoppingBasketImpl();
        basket.addItem(product1,-1);
    }

    //Test RemoveItem
    @Test
    public void testRemoveItem_ItemRemainsInBasket() {
        ShoppingBasket basket = new ShoppingBasketImpl();
        basket.addItem(product1,10);
        assertTrue(basket.removeItem(product1,5));
        assertEquals(1, basket.getItems().size());
        assertTrue(basket.getItems().contains(new Pair<Product,Integer>(product1, 5)));
        //execute 4 more times
        //remove 1 each time
        //count should be i
        for(int i=4;i>0;i--) {
            assertTrue(basket.removeItem(product1,1));
            assertEquals(1, basket.getItems().size());
            assertTrue(basket.getItems().contains(new Pair<Product,Integer>(product1, i)));
        }
    }
    @Test
    public void testRemoveAllItems() {
        ShoppingBasket basket = new ShoppingBasketImpl();
        //remove multiple at once
        basket.addItem(product1,3);
        assertTrue(basket.removeItem(product1,3));
        assertTrue(basket.getItems().isEmpty());
        //remove the only item
        basket.addItem(product1, 1);
        assertTrue(basket.removeItem(product1,1));
        assertTrue(basket.getItems().isEmpty());
    }
    @Test
    public void testRemoveMoreThanInBasket() {
        ShoppingBasket basket = new ShoppingBasketImpl();
        basket.addItem(product1,5);
        assertTrue(basket.getItems().contains(new Pair<Product,Integer>(product1,5)));
        //remove too many rails
        assertFalse(basket.removeItem(product1,6));
        //basket state doesn't change
        assertTrue(basket.getItems().contains(new Pair<Product,Integer>(product1,5)));
    }
    @Test (expected = IllegalArgumentException.class)
    public void testRemoveInvalidProduct() {
        ShoppingBasket basket = new ShoppingBasketImpl();
        basket.removeItem(null,1);
    }
    @Test (expected = IllegalArgumentException.class)
    public void testRemoveInvalidCount_Zero() {
        ShoppingBasket basket = new ShoppingBasketImpl();
        basket.removeItem(product1,0);
    }
    @Test (expected = IllegalArgumentException.class)
    public void testRemoveInvalidCount_Negative() {
        ShoppingBasket basket = new ShoppingBasketImpl();
        basket.removeItem(product1,-1);
    }
    //Test getItems
    @Test
    public void testGetItems_DistinctItems() {
        ShoppingBasket basket = new ShoppingBasketImpl();
        basket.addItem(product1,1);
        assertEquals(1,basket.getItems().size());
        assertTrue(basket.getItems().contains(new Pair<Product,Integer>(product1,1)));
        basket.addItem(product2,2);
        assertEquals(2,basket.getItems().size());
        assertTrue(basket.getItems().contains(new Pair<Product,Integer>(product2,2)));
        basket.addItem(product3,3);
        assertEquals(3,basket.getItems().size());
        assertTrue(basket.getItems().contains(new Pair<Product,Integer>(product3,3)));
        basket.addItem(product4,4);
        assertEquals(4,basket.getItems().size());
        assertTrue(basket.getItems().contains(new Pair<Product,Integer>(product4,4)));
    }
    @Test
    public void testGetItems_DuplicateItem() {
        ShoppingBasket basket = new ShoppingBasketImpl();
        for(int i=0;i<25;i++) {
            basket.addItem(product1, 1);
            assertEquals(1, basket.getItems().size());
            assertTrue(basket.getItems().contains(new Pair<Product, Integer>(product1, i + 1)));
        }
    }

    @Test
    public void testGetValue_DuplicateItems() {
        ShoppingBasket basket = new ShoppingBasketImpl();
        basket.addItem(product2,400);
        assertEquals((Double) 4.0,basket.getValue());
    }

    @Test
    public void testGetValue_DistinctItems() {
        ShoppingBasket basket = new ShoppingBasketImpl();
        basket.addItem(product1,1);
        basket.addItem(product2,1);
        basket.addItem(product3,1);
        basket.addItem(product4,1);
        assertEquals((Double)118.52, basket.getValue());
    }

    @Test
    public void testClear_DuplicateItems() {
        ShoppingBasket basket = new ShoppingBasketImpl();
        basket.addItem(product1,100);
        basket.clear();
        assertTrue(basket.getItems().isEmpty());
    }
    @Test
    public void testClear_DistinctItems() {
        ShoppingBasket basket = new ShoppingBasketImpl();
        basket.addItem(product1,1);
        basket.addItem(product2,1);
        basket.addItem(product3,1);
        basket.addItem(product4,1);
        basket.clear();
        assertTrue(basket.getItems().isEmpty());
    }



}
