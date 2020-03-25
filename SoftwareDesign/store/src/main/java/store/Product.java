package store;

public class Product {
    private String name;
    private double price;

    public Product(String name, double price) throws IllegalArgumentException {
        if(name == null || name.equals("")) {
            throw new IllegalArgumentException("name cannot be null or empty");
        }
        if(!(price > 0)) {
            throw new IllegalArgumentException("price must be greater than zero");
        }
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
