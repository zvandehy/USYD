package state;

public class ItemInTransition extends DeliveryState {
    public ItemInTransition(String parcel) {
        super(parcel);
    }

    @Override
    public DeliveryState getPreviousState() {
        return null;
    }

    @Override
    public DeliveryState getNextState() {
        return new DeliveredItem(parcel);
    }

    @Override
    public void printState() {
        System.out.println(parcel + ": In transition");
    }
}
