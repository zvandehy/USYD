package au.edu.sydney.cpa.erp.feaa;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.contact.Email;
import au.edu.sydney.cpa.erp.ordering.Client;

public class EmailHandler implements InvoiceHandler {

    private InvoiceHandler next;

    @Override
    public void setNextChain(InvoiceHandler nextInvoiceHandler) {
        this.next = nextInvoiceHandler;
    }

    @Override
    public boolean sendInvoice(AuthToken token, Client client, String data) {
        String email = client.getEmailAddress();
        if (null != email) {
            Email.sendInvoice(token, client.getFName(), client.getLName(), data, email);
            return true;
        } else if(next == null) {
            return false;
        } else {
            return next.sendInvoice(token, client, data);
        }
    }
}
