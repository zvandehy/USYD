package au.edu.sydney.cpa.erp.feaa.handlers;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.contact.PhoneCall;
import au.edu.sydney.cpa.erp.ordering.Client;

/**
 * Concrete handler for phone
 */
public class PhoneCallHandler implements ContactMethod {
    private ContactMethod next;
    @Override
    public void setNext(ContactMethod handler) {
        this.next = handler;
    }

    @Override
    public boolean sendInvoice(AuthToken token, Client client, String data) {
        String phone = client.getPhoneNumber();
        if (null != phone) {
            PhoneCall.sendInvoice(token, client.getFName(), client.getLName(), data, phone);
            return true;
        }
        if(next == null) {
            return false;
        } else {
            return next.sendInvoice(token, client, data);
        }
    }
}
