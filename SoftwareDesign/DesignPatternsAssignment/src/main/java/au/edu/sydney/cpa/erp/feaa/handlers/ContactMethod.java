package au.edu.sydney.cpa.erp.feaa.handlers;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.ordering.Client;

public interface ContactMethod {

    void setNext(ContactMethod handler);

    boolean sendInvoice(AuthToken token, Client client, String data);

}
