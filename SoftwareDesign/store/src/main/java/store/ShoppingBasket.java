package store;

import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public interface ShoppingBasket {
    void addItem(Product product, int count) throws IllegalArgumentException;

    boolean removeItem(Product product, int count) throws IllegalArgumentException;

    List<Pair<Product, Integer>> getItems();

    Double getValue();

    void clear();

}
