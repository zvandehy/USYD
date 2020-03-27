package store;

public class Product {
    private String name;
    private double price;

    /**
     * Only constructor for Product
     * @param name - The name that this product should be listed under. May not be null or the empty string.
     * @param price - The price of a single unit of this product. Must be greater than 0.
     * @throws IllegalArgumentException - if any of the parameter preconditions are breached.
     */
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

    /**
     * Standard name accessor. Throws no exceptions.
     * @return The name for this product. Will not be null or the empty string.
     */
    public String getName() {
        return name;
    }

    /**
     * Standard price accessor. Throws no exceptions.
     * @return The price of a single unit of this product. Will be greater than 0.
     */
    public double getPrice() {
        return price;
    }
}
