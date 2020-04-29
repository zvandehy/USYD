package au.edu.sydney.soft3202.tutorials.week9.template;

public class CarefulProcessing implements OrderProcessStrategy {
//no longer needed that Strategy is template method
//    @Override
//    public void processOrder() {
//        startOrder();
//        addItemsToOrder();
//        finaliseOrder();
//    }

    public void startOrder() {
        System.out.println("Starting order");

    }

    public void addItemsToOrder() {
        System.out.println("Adding items to order");
    }

    public void finaliseOrder() {
        System.out.println("Getting final approval from sales before sending order to warehousing");
    }
}
