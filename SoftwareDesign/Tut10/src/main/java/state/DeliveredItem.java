package state;

public class DeliveredItem extends DeliveryState {

    public DeliveredItem(String parcel) {
        super(parcel);
    }

    @Override
    public DeliveryState getPreviousState() {
        return new ItemInTransition(parcel);
    }

    @Override
    public DeliveryState getNextState() {
        return null;
    }

    @Override
    public void printState() {
        System.out.println(parcel + ": Delivered");
    }
}
