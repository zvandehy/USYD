package mediator;

public interface AbstractPhoneNetwork {
    public void sendSMS(String msg, String phoneNumber);
    public void connect(Phone phone);
}
