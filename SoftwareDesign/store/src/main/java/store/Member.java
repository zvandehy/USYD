package store;

public interface Member {
    /**
     * Takes a number of a product from the global inventory and adds it to this Member's basket.
     * @param product - The product to be selected. May not be null.
     * @param quantity - The number of units of the product to be selected. Must be 1 or more.
     * @throws IllegalArgumentException - if any of the parameter preconditions are breached.
     * @throws IllegalStateException - if the global inventory does not have the required amount of Product.
     */
    void selectProduct(Product product, int quantity) throws IllegalArgumentException, IllegalStateException;

    /**
     * Removes a number of a product from this Member's basket and returns it to the global inventory.
     * @param product - The product to be returned.
     * @param quantity - The number of units of the product to be returned.
     * @throws IllegalArgumentException - if any of the parameter preconditions are breached.
     * @throws IllegalStateException - if the Member's basket does not contain the required amount of Product.
     */
    void returnProduct(Product product, int quantity) throws IllegalArgumentException, IllegalStateException;

    /**
     * Finishes this Member's shopping, charges their account (via System.out.println), and clears their basket. Note: testing this method is more complicated than it might seem at first. StackOverflow will likely have some suggestions for you. You cannot refactor this method (even though it would be the best solution).
     */
    void finalisePurchases();
}
