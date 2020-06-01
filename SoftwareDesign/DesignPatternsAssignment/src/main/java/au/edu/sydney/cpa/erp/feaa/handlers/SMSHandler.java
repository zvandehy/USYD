package au.edu.sydney.cpa.erp.feaa.handlers;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.contact.SMS;
import au.edu.sydney.cpa.erp.ordering.Client;

/**
 * Concrete handler for SMS
 */
public class SMSHandler implements ContactMethod {
    private ContactMethod next;
    @Override
    public void setNext(ContactMethod handler) {
        this.next = handler;
    }

    @Override
    public boolean sendInvoice(AuthToken token, Client client, String data) {
        String smsPhone = client.getPhoneNumber();
        if (null != smsPhone) {
            SMS.sendInvoice(token, client.getFName(), client.getLName(), data, smsPhone);
            return true;
        }
        if(next == null) {
            return false;
        } else {
            return next.sendInvoice(token, client, data);
        }
    }
}
