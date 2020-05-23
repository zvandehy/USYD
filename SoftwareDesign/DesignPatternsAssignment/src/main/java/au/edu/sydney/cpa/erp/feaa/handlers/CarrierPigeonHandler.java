package au.edu.sydney.cpa.erp.feaa.handlers;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.contact.CarrierPigeon;
import au.edu.sydney.cpa.erp.ordering.Client;

public class CarrierPigeonHandler implements ContactMethod {
    private ContactMethod next;
    @Override
    public void setNext(ContactMethod handler) {
        this.next = handler;
    }

    @Override
    public boolean sendInvoice(AuthToken token, Client client, String data) {
        String pigeonCoopID = client.getPigeonCoopID();
        if (null != pigeonCoopID) {
            CarrierPigeon.sendInvoice(token, client.getFName(), client.getLName(), data, pigeonCoopID);
            return true;
        }
        if(next == null) {
            return false;
        } else {
            return next.sendInvoice(token, client, data);
        }

    }
}
