package au.edu.sydney.cpa.erp.feaa.handlers;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.ordering.Client;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class ContactHandler {
    /**
     * Initialize a Chain of Responsibility of Contact Methods (invoice handlers) in the order given by the priority list
     * @param priority - the order to build the ContactMethod chain
     * @return the first ContactMethod in the resulting chain
     */
    public static ContactMethod buildChain(List<ContactMethod> priority) {
        for(int i=1;i < priority.size(); i++) {
            ContactMethod contactMethod = priority.get(i-1);
            contactMethod.setNext(priority.get(i));
        }
        return priority.get(0);
    }

    /**
     * Send an invoice to with the provided data to the specified client. Try the highest priority contact method that hasn't failed until one succeeds or the priority list is exhausted.
     * @param token - provides authentication for sending invoices
     * @param client - the recipient of the invoice
     * @param priority - A list of ContactMethods order from highest priority to lowest priority. Only 1 or 0 of the methods will be successful.
     * @param data - the data to send in the invoice
     * @return true if an invoice was successfully sent via any provided ContactMethod, false if no method was successful.
     */
    public static boolean sendInvoice(AuthToken token, Client client, List<ContactMethod> priority, String data) {
        return buildChain(priority).sendInvoice(token, client, data);
    }

    /**
     *
     * @return a list of all known contact methods
     */
    public static List<String> getKnownMethods() {
        return Arrays.asList(
                "Carrier Pigeon",
                "Email",
                "Mail",
                "Internal Accounting",
                "Phone call",
                "SMS"
        );
    }
}
