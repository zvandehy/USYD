package au.edu.sydney.cpa.erp.feaa.handlers;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.ordering.Client;

/**
 * An Invoice Handler abstraction to create a Chain of Responsibility
 */
public interface ContactMethod {

    /**
     * Save handler to be the next handler in the chain to use if this contact method fails.
     * @param handler - the next handler
     */
    void setNext(ContactMethod handler);

    /**
     * Attempt to send an invoice to the provided client, otherwise pass the request to the next method in the chain
     * @param token - provides authentication for sending the invoice
     * @param client - the recipient of the invoice
     * @param data - data to include in the message of the invoice
     * @return true if this method or a following method was successful, otherwise return false
     */
    boolean sendInvoice(AuthToken token, Client client, String data);

}
