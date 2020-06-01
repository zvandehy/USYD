package au.edu.sydney.cpa.erp.feaa.handlers;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.contact.CarrierPigeon;
import au.edu.sydney.cpa.erp.ordering.Client;

/**
 * Concrete Handler for Carrier Pigeon
 */
public class CarrierPigeonHandler implements ContactMethod {

    private ContactMethod next;

    @Override
    public void setNext(ContactMethod handler) {
        this.next = handler;
    }

    @Override
    public boolean sendInvoice(AuthToken token, Client client, String data) {
        String pigeonCoopID = client.getPigeonCoopID();
        if (null != pigeonCoopID) {//if any of the client's contact details are not set then cannot send invoice using this method
            CarrierPigeon.sendInvoice(token, client.getFName(), client.getLName(), data, pigeonCoopID);
            return true;
        }

        if(next == null) {
            //if this is the last method in the chain
            return false;
        } else {
            //otherwise attempt to send invoice with the next method in the chain
            return next.sendInvoice(token, client, data);
        }

    }
}
