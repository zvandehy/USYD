package store;

import javafx.util.Pair;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

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

    @Mock
    Inventory mockInventory;

    @Mock
    ShoppingBasket mockBasket;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    //Test Constructor
    @Test
    public void testMemberConstructor() {
        try {
            new MemberImpl(mockInventory, mockBasket);
        } catch(Exception e) {
            fail("Should not throw exception on constructor");
        }
        assertTrue(true);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSelectProduct_NullProduct() {
        Member member = new MemberImpl(mockInventory, mockBasket);
        doThrow(new IllegalArgumentException()).when(mockInventory).takeProduct(eq(null), anyInt());
        member.selectProduct(null,1);
        verify(mockInventory).takeProduct(null,1);
        verify(mockBasket, never()).addItem(product1,1);
    }
    @Test (expected = IllegalArgumentException.class)
    public void testSelectProduct_ZeroQuantity() {
        Member member = new MemberImpl(mockInventory, mockBasket);
        doThrow(new IllegalArgumentException()).when(mockInventory).takeProduct(any(Product.class), eq(0));
        member.selectProduct(product1,0);
        verify(mockInventory).takeProduct(product1,0);
        verify(mockBasket, never()).addItem(product1,0);
    }
    @Test (expected = IllegalArgumentException.class)
    public void testSelectProduct_NegativeQuantity() {
        Member member = new MemberImpl(mockInventory, mockBasket);
        doThrow(new IllegalArgumentException()).when(mockInventory).takeProduct(any(Product.class), eq(-1));
        member.selectProduct(product1,-1);
        //todo: Are these called?
        assertTrue(false);
        verify(mockInventory).takeProduct(product1,-1);
        verify(mockBasket, never()).addItem(product1,-1);
    }
    @Test
    public void testSelectProduct_QuantityTooHigh() {
        Member member = new MemberImpl(mockInventory, mockBasket);
        doThrow(new IllegalStateException()).when(mockInventory).takeProduct(product1, 2);
        try {
            member.selectProduct(product1,2);
            fail("global inventory does not have the required amount of Product");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
            verify(mockInventory).takeProduct(product1,2);
            verify(mockBasket, never()).addItem(product1,0);
        }
    }
    @Test
    public void testSelectProduct_SelectMultiple() {
        Member member = new MemberImpl(mockInventory, mockBasket);
        member.selectProduct(product1,2);
        verify(mockInventory).takeProduct(product1,2);
        verify(mockBasket).addItem(product1,2);
        //5.50 + 5.50 = 11.00
        //verify value when finalised is appropriate
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        when(mockBasket.getValue()).thenReturn(11.00);
        member.finalisePurchases();
        assertTrue(outContent.toString().contains("11"));
        verify(mockBasket).getItems();
        verify(mockBasket).getValue();
        verify(mockBasket).clear();
    }
    @Test
    public void testSelectProduct_SelectDistinct() {
        Member member = new MemberImpl(mockInventory, mockBasket);
        member.selectProduct(product1,1);
        member.selectProduct(product2,1);
        verify(mockInventory).takeProduct(product1,1);
        verify(mockBasket).addItem(product1,1);
        verify(mockInventory).takeProduct(product2,1);
        verify(mockBasket).addItem(product2,1);
        //5.50 + .01 = 5.51
        //verify value of cart when finalised is appropriate
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        when(mockBasket.getValue()).thenReturn(5.51);
        member.finalisePurchases();
        assertTrue(outContent.toString().contains("5.51"));
        verify(mockBasket).getItems();
        verify(mockBasket).getValue();
        verify(mockBasket).clear();
    }
    @Test
    public void testReturnProduct_ReturnOnlyProduct() {
        Member member = new MemberImpl(mockInventory, mockBasket);

        member.selectProduct(product1,1);
        verify(mockInventory).takeProduct(product1,1);
        verify(mockBasket).addItem(product1,1);

        when(mockBasket.removeItem(product1,1)).thenReturn(true);
        member.returnProduct(product1,1);
        verify(mockInventory).addProduct(product1,1);
        verify(mockBasket).removeItem(product1,1);

        //Todo: do we need to call finalisePurchases to verify cart state (value is 0.00)?
    }
    @Test
    public void testReturnProduct_ReturnMultiple() {
        Member member = new MemberImpl(mockInventory, mockBasket);
        member.selectProduct(product1,3);
        verify(mockInventory).takeProduct(product1,3);
        verify(mockBasket).addItem(product1,3);
        when(mockBasket.removeItem(product1,2)).thenReturn(true);
        member.returnProduct(product1,2);
        verify(mockInventory).addProduct(product1,2);
        verify(mockBasket).removeItem(product1,2);
        //todo: do we need to check the state of anything else in member?
    }
    @Test (expected = IllegalArgumentException.class)
    public void testReturnProduct_NullProduct() {
        doThrow(new IllegalArgumentException()).when(mockBasket).removeItem(null, 1);
        Member member = new MemberImpl(mockInventory, mockBasket);
        member.returnProduct(null,1);
        verify(mockInventory, never()).addProduct(null,1);
        verify(mockBasket).removeItem(null,1);

    }
    @Test (expected = IllegalArgumentException.class)
    public void testReturnProduct_ZeroQuantity() {
        Member member = new MemberImpl(mockInventory, mockBasket);
        doThrow(new IllegalArgumentException()).when(mockBasket).removeItem(any(Product.class), eq(0));
        member.returnProduct(product1,0);
        verify(mockInventory, never()).addProduct(product1,0);
        verify(mockBasket).removeItem(product1,0);
    }
    @Test (expected = IllegalArgumentException.class)
    public void testReturnProduct_NegativeQuantity() {
        Member member = new MemberImpl(mockInventory, mockBasket);
        doThrow(new IllegalArgumentException()).when(mockBasket).removeItem(any(Product.class), eq(-1));
        member.returnProduct(product1,-1);
        verify(mockInventory, never()).addProduct(product1,-1);
        verify(mockBasket).removeItem(product1,-1);
    }
    @Test
    public void testReturnProduct_QuantityTooHigh() {
        Member member = new MemberImpl(mockInventory, mockBasket);
        doThrow(new IllegalStateException()).when(mockBasket).removeItem(product1, 3);
        try {
            member.selectProduct(product1,2);
            verify(mockBasket).addItem(product1,2);
            verify(mockInventory).takeProduct(product1,2);

            member.returnProduct(product1,3);
            fail("member does not have the required amount of Product to return");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
        //todo state check?
    }
    @Test
    public void testReturnProduct_Valid() {
        Member member = new MemberImpl(mockInventory, mockBasket);
        //2 of product1 in basket
        member.selectProduct(product1,2);
        verify(mockInventory).takeProduct(product1,2);
        verify(mockBasket).addItem(product1,2);
        //2 product1, 4 product2
        member.selectProduct(product2,4);
        verify(mockInventory).takeProduct(product2,4);
        verify(mockBasket).addItem(product2,4);

        when(mockBasket.removeItem(product1,1)).thenReturn(true);
        //1 product1, 4 product2
        member.returnProduct(product1,1);
        verify(mockInventory).addProduct(product1,1);
        verify(mockBasket).removeItem(product1,1);

        when(mockBasket.removeItem(product2,3)).thenReturn(true);
        //1 product1, 1 product2
        member.returnProduct(product2,3);
        verify(mockInventory).addProduct(product2,3);
        verify(mockBasket).removeItem(product2,3);
        //cart value is 5.50 + .01
        when(mockBasket.getItems()).thenReturn(
                Arrays.asList(new Pair<>(product1, 1), new Pair<>(product2, 2)));
        when(mockBasket.getValue()).thenReturn(5.51);
        //verify cart value
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        member.finalisePurchases();
        verify(mockBasket, atLeast(1)).getItems();
        verify(mockBasket).getValue();
        verify(mockBasket).clear();
        assertTrue(outContent.toString().contains("5.51"));
    }
    @Test
    public void testFinalisePurchases_EmptyCart() {
        Member member = new MemberImpl(mockInventory, mockBasket);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        when(mockBasket.getValue()).thenReturn(0.0);
        member.finalisePurchases();
        verify(mockBasket).getItems();
        verify(mockBasket).getValue();
        verify(mockBasket).clear();
        assertTrue(outContent.toString().contains("0.0"));
    }
    @Test
    public void testFinalisePurchases_CartWithItems() {
        Member member = new MemberImpl(mockInventory, mockBasket);
        Product mockProduct1 = mock(Product.class);
        Product mockProduct2 = mock(Product.class);
        Product mockProduct3 = mock(Product.class);
        when(mockProduct1.getName()).thenReturn("Muffins");
        when(mockProduct2.getName()).thenReturn("Chocolate Chip");
        when(mockProduct3.getName()).thenReturn("Enchiladas");
        when(mockProduct1.getPrice()).thenReturn(5.50);
        when(mockProduct2.getPrice()).thenReturn(0.01);
        when(mockProduct3.getPrice()).thenReturn(13.01);

        member.selectProduct(mockProduct1,1);
        member.selectProduct(mockProduct2,2);
        member.selectProduct(mockProduct3,3);
        verify(mockInventory, times(3)).takeProduct(any(Product.class),anyInt());
        verify(mockBasket, times(3)).addItem(any(Product.class),anyInt());
        //5.50 + .01 *2 + 13.01 * 3 == 5.52 + 39.03 == 44.55
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        when(mockBasket.getValue()).thenReturn(44.55);
        when(mockBasket.getItems()).thenReturn(
                Arrays.asList(new Pair<>(mockProduct1, 1), new Pair<>(mockProduct2, 2), new Pair<>(mockProduct3, 3)));
        member.finalisePurchases();
        verify(mockBasket, atLeast(1)).getItems();
        verify(mockBasket).getValue();
        verify(mockBasket).clear();
        assertTrue(outContent.toString().contains("44.55"));
        assertTrue(outContent.toString().contains("Muffins"));
        assertTrue(outContent.toString().contains("Chocolate Chip"));
        assertTrue(outContent.toString().contains("Enchiladas"));

    }
}
