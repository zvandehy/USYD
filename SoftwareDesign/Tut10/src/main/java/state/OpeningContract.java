package state;

public class OpeningContract extends DeliveryState {
    public OpeningContract(String parcel) {
        super(parcel);
    }

    @Override
    public DeliveryState getPreviousState() {
        return null;
    }

    @Override
    public DeliveryState getNextState() {
        return new SendingItem(parcel);
    }

    @Override
    public void printState() {
        System.out.println(parcel + ": Contract is being opened");
    }
}
