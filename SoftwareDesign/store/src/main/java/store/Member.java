package store;

public interface Member {
    void selectProduct(Product product, int quantity) throws IllegalArgumentException, IllegalStateException;

    void returnProduct(Product product, int quantity) throws IllegalArgumentException, IllegalStateException;

    void finalisePurchases();
}
