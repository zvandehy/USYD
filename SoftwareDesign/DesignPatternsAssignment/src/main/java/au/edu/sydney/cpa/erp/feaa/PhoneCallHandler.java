package au.edu.sydney.cpa.erp.feaa;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.contact.Mail;
import au.edu.sydney.cpa.erp.contact.PhoneCall;
import au.edu.sydney.cpa.erp.ordering.Client;

public class PhoneCallHandler implements InvoiceHandler {

    private InvoiceHandler next;

    @Override
    public void setNextChain(InvoiceHandler nextInvoiceHandler) {
        this.next = nextInvoiceHandler;
    }

    @Override
    public boolean sendInvoice(AuthToken token, Client client, String data) {
        String phone = client.getPhoneNumber();
        if (null != phone) {
            PhoneCall.sendInvoice(token, client.getFName(), client.getLName(), data, phone);
            return true;
        } else if(next == null) {
            return false;
        } else {
            return next.sendInvoice(token, client, data);
        }
    }
}
