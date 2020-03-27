package store;

import java.util.List;

public interface Inventory {
    /**
     * Adds a number of a product to this inventory.
     * @param product - The product to add. May not be null
     * @param quantity - The amount to add. Must be 1 or more.
     * @throws IllegalArgumentException - if any of the parameter preconditions are breached
     */
    void addProduct(Product product, int quantity) throws IllegalArgumentException;

    /**
     * Removes a number of a product from this inventory.
     * @param product - The product to remove. May not be null.
     * @param quantity - The amount to remove. Must be 1 or more.
     * @throws IllegalArgumentException - if any of the parameter preconditions are breached.
     * @throws IllegalStateException - if the inventory did not hold at least the requested amount of the given product.
     */
    void takeProduct(Product product, int quantity) throws IllegalArgumentException, IllegalStateException;

    /**
     * Accessor for the count of a product.
     * @param product - The product to view the count for. May not be null.
     * @return The number of units of the given product stored in this inventory. Will be 0 or more.
     * @throws IllegalArgumentException - if any of the parameter preconditions are breached.
     */
    int getProductQuantity(Product product) throws IllegalArgumentException;

    /**
     * Accessor for the list of products.
     * @return The list of products currently OR previously stored in this inventory. 0 count products will still be listed.
     */
    List<Product> getProducts();


}
