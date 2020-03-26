package store;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InventoryTest {

    Product product1;
    Product product2;
    Product product3;
    Product product4;
    Product inventoryTestProduct;

    @Before
    public void setup() {
        product1 = new Product("Muffins", 5.50);
        product2 = new Product("Chocolate Chip", .01);
        product3 = new Product("Enchiladas", 13.01);
        product4 = new Product("Cheese", 100.0);
        inventoryTestProduct = new Product("Happiness", 100.0);
        new InventoryImpl();
    }

    @Test
    public void getInstanceTest() {
        assertNotNull(InventoryImpl.getInstance());
    }

    @Test
    public void testAddProduct_MultipleOfOneProduct() {
        Inventory inventory = InventoryImpl.getInstance();
        inventory.addProduct(product1, 1);
        assertEquals(1,inventory.getProductQuantity(product1));
        inventory.addProduct(product1, 100);
        assertEquals(101,inventory.getProductQuantity(product1));
        //must take the products to maintain inventory state in tests
        inventory.takeProduct(product1,101);
    }

    @Test
    public void testAddProduct_DistinctProducts() {
        Inventory inventory = InventoryImpl.getInstance();
        inventory.addProduct(product1, 1);
        assertEquals(1,inventory.getProductQuantity(product1));
        inventory.addProduct(product2, 100);
        assertEquals(100,inventory.getProductQuantity(product2));
        inventory.addProduct(product3, 50);
        assertEquals(50,inventory.getProductQuantity(product3));
        //must take the products to maintain inventory state
        inventory.takeProduct(product1,1);
        inventory.takeProduct(product2,100);
        inventory.takeProduct(product3,50);
    }
    @Test (expected = IllegalArgumentException.class)
    public void testAddProduct_NullProduct() {
        Inventory inventory = InventoryImpl.getInstance();
        inventory.addProduct(null,1);
    }
    @Test (expected = IllegalArgumentException.class)
    public void testAddProduct_ZeroQuantity() {
        Inventory inventory = InventoryImpl.getInstance();
        inventory.addProduct(product1,0);
    }
    @Test (expected = IllegalArgumentException.class)
    public void testAddProduct_NegativeQuantity() {
        Inventory inventory = InventoryImpl.getInstance();
        inventory.addProduct(product1,-1);
    }
    @Test
    public void testTakeProduct_PositiveQuantities() {
        Inventory inventory = InventoryImpl.getInstance();
        inventory.addProduct(product1, 1);
        inventory.takeProduct(product1,1);
        inventory.addProduct(product1, 100);
        inventory.takeProduct(product1,100);
    }
    @Test (expected = IllegalArgumentException.class)
    public void testTakeProduct_ZeroQuantity() {
        Inventory inventory = InventoryImpl.getInstance();
        inventory.addProduct(product1, 1);
        inventory.takeProduct(product1,0);
        //remove to maintain inventory
        inventory.takeProduct(product1,1);
    }
    @Test (expected = IllegalArgumentException.class)
    public void testTakeProduct_NegativeQuantity() {
        Inventory inventory = InventoryImpl.getInstance();
        inventory.addProduct(product1, 1);
        inventory.takeProduct(product1,-1);
        //remove to maintain inventory
        inventory.takeProduct(product1,1);
    }
    @Test (expected = IllegalArgumentException.class)
    public void testTakeProduct_NullProduct() {
        Inventory inventory = InventoryImpl.getInstance();
        inventory.addProduct(product1, 1);
        inventory.takeProduct(null,1);
        //remove to maintain inventory
        inventory.takeProduct(product1,1);
    }
    @Test (expected = IllegalStateException.class)
    public void testTakeProduct_QuantityOneHigherThanActual() {
        Inventory inventory = InventoryImpl.getInstance();
        inventory.addProduct(product1, 1);
        inventory.takeProduct(product1,2);
        //remove to maintain inventory
        inventory.takeProduct(product1,1);
    }
    @Test (expected = IllegalStateException.class)
    public void testTakeProduct_QuantityManyHigherThanActual() {
        Inventory inventory = InventoryImpl.getInstance();
        inventory.addProduct(product1, 1);
        inventory.takeProduct(product1,100);
        //remove to maintain inventory
        inventory.takeProduct(product1,1);
    }
    @Test
    public void testGetProductQuantity_ValidQuantities() {
        Inventory inventory = InventoryImpl.getInstance();
        inventory.addProduct(product1, 1);
        assertEquals(1,inventory.getProductQuantity(product1));
        inventory.addProduct(product2, 100);
        assertEquals(100,inventory.getProductQuantity(product2));
        inventory.addProduct(product3, 50);
        assertEquals(50,inventory.getProductQuantity(product3));
        //must take the products to maintain inventory state
        inventory.takeProduct(product1,1);
        inventory.takeProduct(product2,100);
        inventory.takeProduct(product3,50);
    }
    @Test
    public void testGetProductQuantity_ZeroQuantity() {
        Inventory inventory = InventoryImpl.getInstance();
        //add and then remove a product
        inventory.addProduct(product1,10);
        inventory.takeProduct(product1,10);
        assertEquals(0,inventory.getProductQuantity(product1));
    }
    @Test (expected = IllegalArgumentException.class)
    public void testGetProductQuantity_NullProduct() {
        InventoryImpl.getInstance().getProductQuantity(null);
    }
    @Test
    public void testGetProducts_positive() {
        Inventory inventory = InventoryImpl.getInstance();
        inventory.addProduct(product1, 1);
        assertTrue(inventory.getProducts().contains(product1));
        inventory.addProduct(product2, 100);
        assertTrue(inventory.getProducts().contains(product2));
        inventory.addProduct(product3, 50);
        assertTrue(inventory.getProducts().contains(product3));
        //take products to maintain inventory
        inventory.takeProduct(product1,1);
        inventory.takeProduct(product2,100);
        inventory.takeProduct(product3,50);

    }
    @Test
    public void testGetProducts_UniqueZero() {
        Inventory inventory = InventoryImpl.getInstance();
        //note that this product was not added in any other tests
        inventory.addProduct(inventoryTestProduct, 5);
        assertTrue(inventory.getProducts().contains(inventoryTestProduct));
        inventory.takeProduct(inventoryTestProduct,5);
        assertEquals(0,inventory.getProductQuantity(inventoryTestProduct));
        //still in list even though quantity is zero
        assertTrue(inventory.getProducts().contains(inventoryTestProduct));
    }


}
