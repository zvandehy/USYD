package store;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class InventoryImpl implements Inventory {
    private  static Inventory instance;
    private List<Product> products;
    private List<Pair<Product, Integer>> items;

    @Override
    public Inventory getInstance() {
        if(instance == null) {
            instance = new InventoryImpl();
            products = new ArrayList<>();
            items = new ArrayList<>();
            return instance;
        } else {
            return instance;
        }
    }

    @Override
    public void addProduct(Product product, int quantity) throws IllegalArgumentException {
        if(product == null) throw new IllegalArgumentException("product cannot be null");
        if(!(quantity >= 1)) throw new IllegalArgumentException(("quantity must be one or more"));

        //find the product and add to it's quantity
        for(int i=0; i<items.size(); i++) {
            Pair<Product, Integer> pair = items.get(i);
            if(pair.getKey().equals(product)) {
                Pair<Product, Integer> newPair = new Pair<>(pair.getKey(),pair.getValue()+quantity);
                items.remove(i);
                items.add(newPair);
                break;
            }
        }
        //if product not found, add it
        items.add(new Pair<>(product, quantity));
        products.add(product);
    }

    @Override
    public void takeProduct(Product product, int quantity) throws IllegalArgumentException, IllegalStateException {
        if(product == null) throw new IllegalArgumentException("product cannot be null");
        if(!(quantity >= 1)) throw new IllegalArgumentException(("quantity must be one or more"));

        if(!products.contains(product)) throw new IllegalStateException("Inventory does not hold product");
        for(int i=0; i<items.size(); i++) {
            Pair<Product, Integer> pair = items.get(i);
            if(pair.getKey().equals(product)) {
                if(quantity > pair.getValue()) throw new IllegalStateException("Inventory does not hold requested quantity of product");
                else if(quantity == pair.getValue()) {
                    items.remove(i);
                } else {
                    Pair<Product, Integer> newPair = new Pair<>(pair.getKey(),pair.getValue()-quantity);
                    items.remove(i);
                    items.add(newPair);
                }
            }
        }
    }

    @Override
    public int getProductQuantity(Product product) throws IllegalArgumentException {
        if(product == null) throw new IllegalArgumentException("product cannot be null");
        for (Pair<Product, Integer> pair : items) {
            if (pair.getKey().equals(product)) {
                return pair.getValue();
            }
        }
        return 0;
    }

    @Override
    public List<Product> getProducts() {
        return products;
    }
}
