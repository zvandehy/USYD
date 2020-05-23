package au.edu.sydney.cpa.erp.feaa.handlers;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.ordering.Client;

import java.util.Arrays;
import java.util.List;

public class ContactHandler {

    public static ContactMethod buildChain(List<ContactMethod> priority) {
        for(int i=1;i < priority.size(); i++) {
            ContactMethod contactMethod = priority.get(i-1);
            contactMethod.setNext(priority.get(i));
        }
        return priority.get(0);
    }

    public static boolean sendInvoice(AuthToken token, Client client, List<ContactMethod> priority, String data) {
        return buildChain(priority).sendInvoice(token, client, data);
    }
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
