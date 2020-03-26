package store;

import java.util.List;

public interface Inventory {

    void addProduct(Product product, int quantity) throws IllegalArgumentException;

    void takeProduct(Product product, int quantity) throws IllegalArgumentException, IllegalStateException;

    int getProductQuantity(Product product) throws IllegalArgumentException;

    List<Product> getProducts();


}
