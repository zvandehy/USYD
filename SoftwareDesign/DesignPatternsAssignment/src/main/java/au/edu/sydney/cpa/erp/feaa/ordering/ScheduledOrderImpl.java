package au.edu.sydney.cpa.erp.feaa.ordering;

import au.edu.sydney.cpa.erp.ordering.Order;
import au.edu.sydney.cpa.erp.ordering.Report;
import au.edu.sydney.cpa.erp.ordering.ScheduledOrder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScheduledOrderImpl extends OrderImpl implements ScheduledOrder {

    private int numQuarters;

    public ScheduledOrderImpl(int id, int clientID, LocalDateTime date, PriorityType priorityType, WorkType workType, int numQuarters) {
        super(id, clientID, date, priorityType, workType);
        this.numQuarters = numQuarters;
    }


    @Override
    public double getRecurringCost() {
        return super.getTotalCommission();
    }

    @Override
    public int getNumberOfQuarters() {
        return numQuarters;
    }

    @Override
    public double getTotalCommission() {
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
        return String.format("ID:%s $%,.2f per quarter, $%,.2f total", super.getOrderID(), getRecurringCost(), getTotalCommission());
    }

    @Override
    public String longDesc() {
        StringBuilder reportSB = new StringBuilder();

        List<Report> keyList = new ArrayList<>(getReports().keySet());
        keyList.sort(Comparator.comparing(Report::getReportName).thenComparing(Report::getCommission));

        for (Report report : keyList) {
            reportSB.append(workType.generateDescription(report, getReports().get(report)));

        }

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

        result += priorityType.generateScheduledDescription(getTotalCommission(), numQuarters);

        return result;
    }

    @Override
    public String generateInvoiceData() {
     return priorityType.generateScheduledInvoiceMessage(getRecurringCost(), numQuarters, getTotalCommission(), getReportsInvoiceData());
    }

}
