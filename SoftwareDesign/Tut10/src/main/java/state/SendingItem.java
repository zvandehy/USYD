package state;

public class SendingItem extends DeliveryState {
    public SendingItem(String parcel) {
        super(parcel);
    }

    @Override
    public DeliveryState getPreviousState() {
        return new OpeningContract(parcel);
    }

    @Override
    public DeliveryState getNextState() {
        return new ItemInTransition(parcel);
    }

    @Override
    public void printState() {
        System.out.println(parcel + ": Being sent to collection point");
    }
}
