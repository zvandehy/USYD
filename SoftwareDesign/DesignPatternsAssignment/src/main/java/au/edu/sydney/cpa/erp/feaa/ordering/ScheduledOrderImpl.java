package au.edu.sydney.cpa.erp.feaa.ordering;

import au.edu.sydney.cpa.erp.ordering.Order;
import au.edu.sydney.cpa.erp.ordering.Report;
import au.edu.sydney.cpa.erp.ordering.ScheduledOrder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * This is a specialised abstraction in the bridge pattern. It extends the OrderImpl and adds scheduling behavior as specified in the Scheduled Order interface.
 */
public class ScheduledOrderImpl extends OrderImpl implements ScheduledOrder {

    private int numQuarters;

    /**
     * Creates a ScheduledOrderImpl. Mostly the same constructor as OrderImpl, but with the addition of numQuarters
     * @param id - order id
     * @param clientID - client id
     * @param date - date of order
     * @param priorityType - PriorityType implementor
     * @param workType - WorkType implementor
     * @param numQuarters - number of quarters in schedule
     */
    public ScheduledOrderImpl(int id, int clientID, LocalDateTime date, PriorityType priorityType, WorkType workType, int numQuarters) {
        super(id, clientID, date, priorityType, workType);
        this.numQuarters = numQuarters;
    }


    @Override
    public double getRecurringCost() {
        //the recurring cost is the same as the total cost of the OrderImpl
        return super.getTotalCommission();
    }

    @Override
    public int getNumberOfQuarters() {
        return numQuarters;
    }

    @Override
    public double getTotalCommission() {
        //total cost = recurring cost * number of quarters
        return getRecurringCost() * getNumberOfQuarters();
    }

    @Override
    public Order copy() {
        Order copy = new ScheduledOrderImpl(super.getOrderID(), super.getClient(), super.getOrderDate(), priorityType, workType, numQuarters);

        for (Report report : super.getReports().keySet()) {
            copy.setReport(report, super.getReports().get(report));
        }

        if(super.isFinalised()) copy.finalise();

        return copy;
    }

    @Override
    public String shortDesc() {
        //short description also includes schedule specific information
        return String.format("ID:%s $%,.2f per quarter, $%,.2f total", super.getOrderID(), getRecurringCost(), getTotalCommission());
    }

    @Override
    public String longDesc() {
        StringBuilder reportSB = new StringBuilder();

        List<Report> keyList = new ArrayList<>(getReports().keySet());
        keyList.sort(Comparator.comparing(Report::getReportName).thenComparing(Report::getCommission));

        //generate report summary information using worktype implementor
        for (Report report : keyList) {
            reportSB.append(workType.generateReportDescription(report, getReports().get(report)));

        }

        //summarise the order (including schedule information)
        String result = String.format(isFinalised() ? "" : "*NOT FINALISED*\n" +
                        "Order details (id #%d)\n" +
                        "Date: %s\n" +
                        "Number of quarters: %d\n" +
                        "Reports:\n" +
                        "%s",
                super.getOrderID(),
                super.getOrderDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                numQuarters,
                reportSB.toString()
        );

        //delegate appending the rest of the summary to priority type implementor (and include schedule information)
        result += priorityType.generateScheduledDescription(getTotalCommission(), numQuarters);

        return result;
    }

    @Override
    public String generateInvoiceData() {
        //delegate invoice data structure to priority type
        //also includes schedule information
        //also includes report information (which was gathered by delegating to work type)
        return priorityType.generateScheduledInvoiceMessage(getRecurringCost(), numQuarters, getTotalCommission(), getReportsInvoiceData());
    }

}
