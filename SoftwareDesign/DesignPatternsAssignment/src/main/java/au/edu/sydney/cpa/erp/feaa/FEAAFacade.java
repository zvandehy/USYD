package au.edu.sydney.cpa.erp.feaa;

import au.edu.sydney.cpa.erp.auth.AuthModule;
import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.database.TestDatabase;
import au.edu.sydney.cpa.erp.feaa.handlers.*;
import au.edu.sydney.cpa.erp.feaa.ordering.*;
import au.edu.sydney.cpa.erp.feaa.reports.ReportDatabase;
import au.edu.sydney.cpa.erp.ordering.Client;
import au.edu.sydney.cpa.erp.ordering.Order;
import au.edu.sydney.cpa.erp.ordering.Report;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("Duplicates")
public class FEAAFacade {
    private AuthToken token;

    public boolean login(String userName, String password) {
        token = AuthModule.login(userName, password);

        return null != token;
    }

    public List<Integer> getAllOrders() {
        if (null == token) {
            throw new SecurityException();
        }

        TestDatabase database = TestDatabase.getInstance();

        List<Order> orders = database.getOrders(token);

        List<Integer> result = new ArrayList<>();

        for (Order order : orders) {
            result.add(order.getOrderID());
        }

        return result;
    }

    public Integer createOrder(int clientID, LocalDateTime date, boolean isCritical, boolean isScheduled, int orderType, int criticalLoadingRaw, int maxCountedEmployees, int numQuarters) {
        if (null == token) {
            throw new SecurityException();
        }

        double criticalLoading = criticalLoadingRaw / 100.0;

        Order order;

        if (!TestDatabase.getInstance().getClientIDs(token).contains(clientID)) {
            throw new IllegalArgumentException("Invalid client ID");
        }

        int id = TestDatabase.getInstance().getNextOrderID();

        if (isScheduled) {
            if (1 == orderType) {
                if (isCritical) {
                    order = new ScheduledOrderImpl(id, clientID, date, new CriticalPriority(criticalLoading), new RegularWorkType(maxCountedEmployees), numQuarters);//FirstOrderType
                } else {
                    order = new ScheduledOrderImpl(id, clientID, date, new RegularPriority(), new RegularWorkType(maxCountedEmployees), numQuarters);//Order66
                }
            } else if (2 == orderType) {
                if (isCritical) {
                    order = new ScheduledOrderImpl(id, clientID, date, new CriticalPriority(criticalLoading), new AuditWorkType(), numQuarters);//CriticalAuditOrder
                } else {
                    order = new ScheduledOrderImpl(id, clientID, date, new RegularPriority(), new AuditWorkType(), numQuarters);//NewOrderImpl
                }
            } else {return null;}
        } else {
            if (1 == orderType) {
                if (isCritical) {
                    order = new OrderImpl(id, clientID, date, new CriticalPriority(criticalLoading), new RegularWorkType(maxCountedEmployees));//FirstOrderType
                } else {
                    order = new OrderImpl(id, clientID, date, new RegularPriority(), new RegularWorkType(maxCountedEmployees));//Order66
                }
            } else if (2 == orderType) {
                if (isCritical) {
                    order = new OrderImpl(id, clientID, date, new CriticalPriority(criticalLoading), new AuditWorkType());//CriticalAuditOrder
                } else {
                    order = new OrderImpl(id, clientID, date, new RegularPriority(), new AuditWorkType());//NewOrderImpl
                }
            } else {return null;}
        }

        TestDatabase.getInstance().saveOrder(token, order);
        return order.getOrderID();
    }

    public List<Integer> getAllClientIDs() {
        if (null == token) {
            throw new SecurityException();
        }

        TestDatabase database = TestDatabase.getInstance();
        return database.getClientIDs(token);
    }

    public Client getClient(int id) {
        if (null == token) {
            throw new SecurityException();
        }

        /*
        I recommend making some kind of change to this method so that Lazy Loading can be fully utilized.
        For example, as it is now the CLI.viewClientMenu() method checks if the client returned by this method is null.
        If it is null, then it stops executing. Otherwise, it gets *all* of the client fields for that client.
        However, since this method never returns null, the viewClientMenu() always requests all of the client fields,
        even if the id is invalid. This means that the user always has to wait the 11 seconds it takes to get request those fields.
        If this method could return null by either doing a check like shown below, or another method not shown here, then those 11 wasted
        seconds could be avoided (because lazy loading waits to access those fields!).
         */
//        if(!TestDatabase.getInstance().getClientIDs(token).contains(id)) {
//            return null;
//        }
        return new ClientImpl(token, id);
    }

    public boolean removeOrder(int id) {
        if (null == token) {
            throw new SecurityException();
        }

        TestDatabase database = TestDatabase.getInstance();
        return database.removeOrder(token, id);
    }

    public List<Report> getAllReports() {
        if (null == token) {
            throw new SecurityException();
        }

        return new ArrayList<>(ReportDatabase.getTestReports());
    }

    public boolean finaliseOrder(int orderID, List<String> contactPriority) {
        if (null == token) {
            throw new SecurityException();
        }

        //list of ContactMethods will be put into chain later
        List<ContactMethod> contactHandlers = new ArrayList<>();

        if (null != contactPriority) {
            for (String method: contactPriority) {
                switch (method.toLowerCase()) {
                    case "internal accounting":
                        //changed these from enum to their respective Concrete Class
                        contactHandlers.add(new InternalAccountingHandler());
                        break;
                    case "email":
                        contactHandlers.add(new EmailHandler());
                        break;
                    case "carrier pigeon":
                        contactHandlers.add(new CarrierPigeonHandler());
                        break;
                    case "mail":
                        contactHandlers.add(new MailHandler());
                        break;
                    case "phone call":
                        contactHandlers.add(new PhoneCallHandler());
                        break;
                    case "sms":
                        contactHandlers.add(new SMSHandler());
                        break;
                    default:
                        break;
                }
            }
        }

        if (contactHandlers.size() == 0) { // needs setting to default
            contactHandlers = Arrays.asList(
                    new InternalAccountingHandler(),
                    new EmailHandler(),
                    new CarrierPigeonHandler(),
                    new MailHandler(),
                    new PhoneCallHandler()
            );
        }

        Order order = TestDatabase.getInstance().getOrder(token, orderID);

        order.finalise();
        TestDatabase.getInstance().saveOrder(token, order);
        return ContactHandler.sendInvoice(token, getClient(order.getClient()), contactHandlers, order.generateInvoiceData());
    }

    public void logout() {
        AuthModule.logout(token);
        token = null;
    }

    public double getOrderTotalCommission(int orderID) {
        if (null == token) {
            throw new SecurityException();
        }

        Order order = TestDatabase.getInstance().getOrder(token, orderID);
        if (null == order) {
            return 0.0;
        }

        return order.getTotalCommission();
    }

    public void orderLineSet(int orderID, Report report, int numEmployees) {
        if (null == token) {
            throw new SecurityException();
        }

        Order order = TestDatabase.getInstance().getOrder(token, orderID);

        if (null == order) {
            System.out.println("got here");
            return;
        }

        order.setReport(report, numEmployees);

        TestDatabase.getInstance().saveOrder(token, order);
    }

    public String getOrderLongDesc(int orderID) {
        if (null == token) {
            throw new SecurityException();
        }

        Order order = TestDatabase.getInstance().getOrder(token, orderID);

        if (null == order) {
            return null;
        }

        return order.longDesc();
    }

    public String getOrderShortDesc(int orderID) {
        if (null == token) {
            throw new SecurityException();
        }

        Order order = TestDatabase.getInstance().getOrder(token, orderID);

        if (null == order) {
            return null;
        }

        return order.shortDesc();
    }

    public List<String> getKnownContactMethods() {
        if (null == token) {
            throw new SecurityException();
        }
        return ContactHandler.getKnownMethods();
    }
}
