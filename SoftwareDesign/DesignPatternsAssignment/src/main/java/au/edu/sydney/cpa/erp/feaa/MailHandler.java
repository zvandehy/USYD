package au.edu.sydney.cpa.erp.feaa;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.contact.Mail;
import au.edu.sydney.cpa.erp.ordering.Client;

public class MailHandler implements InvoiceHandler {

    private InvoiceHandler next;

    @Override
    public void setNextChain(InvoiceHandler nextInvoiceHandler) {
        this.next = nextInvoiceHandler;
    }

    @Override
    public boolean sendInvoice(AuthToken token, Client client, String data) {
        String address = client.getAddress();
        String suburb = client.getSuburb();
        String state = client.getState();
        String postcode = client.getPostCode();
        if (null != address && null != suburb &&
                null != state && null != postcode) {
            Mail.sendInvoice(token, client.getFName(), client.getLName(), data, address, suburb, state, postcode);
            return true;
        } else if(next == null) {
            return false;
        } else {
            return next.sendInvoice(token, client, data);
        }
    }
}
