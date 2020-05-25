package mediator;

public abstract class Phone {
    private AbstractPhoneNetwork mediator;
    protected String phoneNumber;

    public  Phone(AbstractPhoneNetwork mediator, String phoneNumber){
        this.mediator = mediator;
        this.phoneNumber = phoneNumber;
    }
    public abstract void sendSMS(String msg, String phone);
    public abstract void receiveSMS(String msg);
    public AbstractPhoneNetwork getMediator() {
        return mediator;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
