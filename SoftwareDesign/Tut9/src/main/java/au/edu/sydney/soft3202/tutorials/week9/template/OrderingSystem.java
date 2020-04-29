package au.edu.sydney.soft3202.tutorials.week9.template;

public class OrderingSystem {
    private OrderProcessStrategy processStrategy;

    public OrderingSystem(OrderProcessStrategy strategy) {
        this.processStrategy = strategy;
    }

    public void placeAnOrder() {
        processStrategy.processOrder();
    }
}
