package au.edu.sydney.soft3202.tutorials.week9.template;

//now template method since processOrder is implemented
public interface OrderProcessStrategy {
    void startOrder();
    void addItemsToOrder();
    void finaliseOrder();

    default void processOrder() {
        startOrder();
        addItemsToOrder();
        finaliseOrder();
    }

}
