package mediator;

public class ApplePhone extends Phone {

    public ApplePhone(AbstractPhoneNetwork mediator, String phone) {
        super(mediator, phone);
        getMediator().connect(this);
    }

    @Override
    public void sendSMS(String msg, String phone) {
        getMediator().sendSMS(msg, phone);
    }

    @Override
    public void receiveSMS(String msg) {
        System.out.println("(this is an apple) " + getPhoneNumber() + " received " + msg);
    }
}
