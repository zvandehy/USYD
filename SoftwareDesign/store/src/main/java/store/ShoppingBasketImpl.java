package store;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class ShoppingBasketImpl implements ShoppingBasket{
    private List<Pair<Product, Integer>> items;
    private Double value;

    public ShoppingBasketImpl() {
        items = new ArrayList<>();
        value = 0.0;
    }

    @Override
    public void addItem(Product product, int quantity) throws IllegalArgumentException {
        if(product == null) throw new IllegalArgumentException("product cannot be null");
        if(!(quantity >= 1)) throw new IllegalArgumentException(("quantity must be one or more"));

        for(int i=0; i<items.size(); i++) {
            Pair<Product, Integer> pair = items.get(i);
            if(pair.getKey().equals(product)) {
                Pair<Product, Integer> newPair = new Pair<>(pair.getKey(),pair.getValue()+quantity);
                items.remove(i);
                items.add(newPair);
                value += product.getPrice() * quantity;
                break;
            }
        }
    }

    @Override
    public boolean removeItem(Product product, int quantity) throws IllegalArgumentException {
        if(product == null) throw new IllegalArgumentException("product cannot be null");
        if(!(quantity >= 1)) throw new IllegalArgumentException(("quantity must be one or more"));

        for(int i=0; i<items.size(); i++) {
            Pair<Product, Integer> pair = items.get(i);
            if(pair.getKey().equals(product)) {
                if(quantity > pair.getValue()) return false;
                else if(quantity == pair.getValue()) {
                    items.remove(i);
                    return true;
                } else {
                    Pair<Product, Integer> newPair = new Pair<>(pair.getKey(),pair.getValue()-quantity);
                    items.remove(i);
                    items.add(newPair);
                    value -= product.getPrice() * quantity;
                    return true;
                }
            }
        }
        return false;

    }

    @Override
    public List<Pair<Product, Integer>> getItems() {
        return items;
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public void clear() {
        items = new ArrayList<>();
        value = 0.0;
    }
}
