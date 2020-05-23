package au.edu.sydney.cpa.erp.feaa.handlers;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.contact.InternalAccounting;
import au.edu.sydney.cpa.erp.ordering.Client;

public class InternalAccountingHandler implements ContactMethod {
    private ContactMethod next;
    @Override
    public void setNext(ContactMethod handler) {
        this.next = handler;
    }

    @Override
    public boolean sendInvoice(AuthToken token, Client client, String data) {
        String internalAccounting = client.getInternalAccounting();
        String businessName = client.getBusinessName();
        if (null != internalAccounting && null != businessName) {
            InternalAccounting.sendInvoice(token, client.getFName(), client.getLName(), data, internalAccounting,businessName);
            return true;
        }
        if(next == null) {
            return false;
        } else {
            return next.sendInvoice(token, client, data);
        }
    }
}
