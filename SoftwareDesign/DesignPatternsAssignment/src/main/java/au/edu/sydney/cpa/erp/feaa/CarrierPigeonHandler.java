package au.edu.sydney.cpa.erp.feaa;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.contact.CarrierPigeon;
import au.edu.sydney.cpa.erp.ordering.Client;

public class CarrierPigeonHandler implements InvoiceHandler {

    private InvoiceHandler next;

    @Override
    public void setNextChain(InvoiceHandler nextInvoiceHandler) {
        this.next = nextInvoiceHandler;
    }

    @Override
    public boolean sendInvoice(AuthToken token, Client client, String data) {
        String pigeonCoopID = client.getPigeonCoopID();
        if (null != pigeonCoopID) {
            CarrierPigeon.sendInvoice(token, client.getFName(), client.getLName(), data, pigeonCoopID);
            return true;
        } else if(next == null) {
            return false;
        } else {
            return next.sendInvoice(token, client, data);
        }
    }
}
