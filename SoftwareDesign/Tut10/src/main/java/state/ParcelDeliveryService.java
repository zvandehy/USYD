package state;

public class ParcelDeliveryService {
    public String parcel;
    private DeliveryState state;
    public ParcelDeliveryService(String parcel) {
        this.parcel = parcel;
    }

    /**
     * Goes through the four states of a delivery
     */
    public void deliver() {
        System.out.println("Delivering " + parcel);
        state = new OpeningContract(parcel);
        while(state != null) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            state.printState();
            state = state.getNextState();
        }
    }


}
