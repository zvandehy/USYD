package state;

public abstract class DeliveryState {
    String parcel;

    public DeliveryState(String parcel) {
        this.parcel = parcel;
    }

    abstract DeliveryState getPreviousState();
    abstract DeliveryState getNextState();
    abstract void printState();
}
