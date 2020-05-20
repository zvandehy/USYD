package au.edu.sydney.cpa.erp.feaa;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.contact.SMS;
import au.edu.sydney.cpa.erp.ordering.Client;

public class SMSHandler implements InvoiceHandler {
    private InvoiceHandler next;

    @Override
    public void setNextChain(InvoiceHandler nextInvoiceHandler) {
        this.next = nextInvoiceHandler;
    }

    @Override
    public boolean sendInvoice(AuthToken token, Client client, String data) {
        String smsPhone = client.getPhoneNumber();
        if (null != smsPhone) {
            SMS.sendInvoice(token, client.getFName(), client.getLName(), data, smsPhone);
            return true;
        } else if(next == null) {
            return false;
        } else {
            return next.sendInvoice(token, client, data);
        }
    }
}
