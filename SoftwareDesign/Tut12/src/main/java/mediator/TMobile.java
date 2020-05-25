package mediator;

import java.util.HashMap;
import java.util.Map;

public class TMobile implements AbstractPhoneNetwork {

    private Map<String, Phone> network;

    public TMobile() {
        this.network = new HashMap<>();
    }

    public void sendSMS(String msg, String phoneNumber)     {
        Phone phone = network.get(phoneNumber);
        phone.receiveSMS(msg + " from " + phoneNumber + " via TMobile");
    }

    @Override
    public void connect(Phone phone) {
        network.put(phone.getPhoneNumber(), phone);
    }
}
