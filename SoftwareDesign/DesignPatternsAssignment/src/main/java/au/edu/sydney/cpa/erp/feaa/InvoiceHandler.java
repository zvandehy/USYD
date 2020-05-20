package au.edu.sydney.cpa.erp.feaa;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.ordering.Client;

public interface InvoiceHandler {

    void setNextChain(InvoiceHandler nextInvoiceHandler);
    boolean sendInvoice(AuthToken token, Client client, String data);
}
