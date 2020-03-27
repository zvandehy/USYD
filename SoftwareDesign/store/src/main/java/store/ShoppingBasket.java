package store;

import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public interface ShoppingBasket {
    /**
     * Add a product to this ShoppingBasket. Will combine and sum duplicate item counts if encountered.
     * @param product - The product to add. May not be null.
     * @param count - The number of units of the product to add. Must be 1 or more.
     * @throws IllegalArgumentException - if any of the parameter preconditions are breached.
     */
    void addItem(Product product, int count) throws IllegalArgumentException;

    /**
     * Removes a product from this ShoppingBasket if and only if the given count or more was contained in the ShoppingBasket. If this brings the total count of that product to 0 the product entry is removed entirely.
     * @param product - The product to remove. May not be null.
     * @param count - The number of units of the product to remove. Must be 1 or more.
     * @return True if the given count of more of the given product were contained in the ShoppingBasket and thus removed. Otherwise false.
     * @throws IllegalArgumentException - if any of the parameter preconditions were breached.
     */
    boolean removeItem(Product product, int count) throws IllegalArgumentException;

    /**
     * Retrieves a list of each Product and count of units currently contained by this ShoppingBasket.
     * @return The list of Products and count of units by Pair. Minimum count per product is 1.
     */
    List<Pair<Product, Integer>> getItems();

    /**
     * Sums the prices of each unit of each product in this ShoppingBasket
     * @return The total price of the ShoppingBasket contents.
     */
    Double getValue();

    /**
     * Empties the ShoppingBasket
     */
    void clear();

}
