package state;

// Client
public class Main {
    public static void main(String[] args) {
        ParcelDeliveryService service1 = new ParcelDeliveryService("Item 1");
        service1.deliver();
    }
}
