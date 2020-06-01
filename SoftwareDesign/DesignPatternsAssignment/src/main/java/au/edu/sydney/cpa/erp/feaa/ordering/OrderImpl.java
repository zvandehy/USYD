package au.edu.sydney.cpa.erp.feaa.ordering;


import au.edu.sydney.cpa.erp.ordering.Order;
import au.edu.sydney.cpa.erp.ordering.Report;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * OrderImpl is an abstraction that defines common behavior among all orders. It implements the Order interface.
 */
public class OrderImpl implements Order {

    //reports and number of employees
    private Map<Report, Integer> reports = new HashMap<>();
    //unique order id
    private final int id;
    //date of the order
    private LocalDateTime date;
    //client's id
    private int clientID;
    //finalised
    private boolean finalised = false;
    //PriorityType Implementor in Bridge pattern
    protected PriorityType priorityType;
    //WorkType Implementor in Bridge pattern
    protected WorkType workType;

    /**
     * Create an OrderImpl. All Order implementations use this class or extensions of it.
     * @param id - unique order id
     * @param clientID - id of the client who made this order
     * @param date - the date the of the order
     * @param priorityType - either Critical or Regular
     * @param workType - either Audit or Regular
     */
    public OrderImpl(int id, int clientID, LocalDateTime date, PriorityType priorityType, WorkType workType) {
        this.id = id;
        this.clientID = clientID;
        this.date = date;
        this.priorityType = priorityType;
        this.workType = workType;
    }

    @Override
    public int getOrderID() {
        return this.id;
    }

    @Override
    public double getTotalCommission() {
        //cost starts at 0
        double cost = 0.0;
        //increase cost for each report in the order
        for (Report report : reports.keySet()) {
            //report cost depends on the WorkType
            //audits are not capped by max employee count
            //regular orders are limited by max employee count
            int employeeCount = reports.get(report);
            cost += workType.calculateReportCost(report, employeeCount);
        }
        //increase cost of order based on priority
        //critical orders increase based on their critical loading
        //regular orders don't have an effect
        cost = priorityType.loadCost(cost);
        return cost;
    }

    @Override
    public LocalDateTime getOrderDate() {
        return this.date;
    }

    @Override
    public void setReport(Report report, int employeeCount) {
        if (finalised) throw new IllegalStateException("Order was already finalised.");
        reports.put(report, employeeCount);
    }

    @Override
    public Set<Report> getAllReports() {
        return reports.keySet();
    }

    @Override
    public int getReportEmployeeCount(Report report) {
        Integer result = reports.get(report);
        return null == result ? 0 : result;
    }

    @Override
    public String generateInvoiceData() {
        //the amount of detail in the report depends on the priority
        //if it should be more detailed then it needs the report invoice data
        return priorityType.generateInvoiceMessage(getTotalCommission(), getReportsInvoiceData());
    }

    /**
     * Generate invoice data for each report. Invoice Data depends on the work type of the order.
     * @return a formatted string with all report invoice messages
     */
    protected String getReportsInvoiceData() {
        String result = "";

        //sort reports by name and then commission
        List<Report> keyList = new ArrayList<>(reports.keySet());
        keyList.sort(Comparator.comparing(Report::getReportName).thenComparing(Report::getCommission));
        //get report invoice message for each report in the order
        for (Report report : keyList) {
            result += workType.generateReportInvoiceMessage(report, reports.get(report));
        }
        return result;
    }

    @Override
    public int getClient() {
        return clientID;
    }

    @Override
    public void finalise() {
        this.finalised = true;
    }

    @Override
    public Order copy() {
        Order copy = new OrderImpl(id, clientID, date, priorityType, workType);
        for (Report report : reports.keySet()) {
            copy.setReport(report, reports.get(report));
        }

        if(finalised) copy.finalise();

        return copy;
    }

    @Override
    public String shortDesc() {
        //all short descriptions are the same
        return String.format("ID:%s $%,.2f", id, getTotalCommission());
    }

    @Override
    public String longDesc() {
        StringBuilder reportSB = new StringBuilder();

        //order based on name and then commission
        List<Report> keyList = new ArrayList<>(reports.keySet());
        keyList.sort(Comparator.comparing(Report::getReportName).thenComparing(Report::getCommission));

        //get a report description for each report in the order
        for (Report report : keyList) {
            reportSB.append(workType.generateReportDescription(report, reports.get(report)));
        }

        //all orders have this summary information
        String result = String.format(finalised ? "" : "*NOT FINALISED*\n" +
                        "Order details (id #%d)\n" +
                        "Date: %s\n" +
                        "Reports:\n" +
                        "%s",
                        id,
                date.format(DateTimeFormatter.ISO_LOCAL_DATE),
                reportSB.toString()
        );

        //append the remaining order information based on the priority type
        result += priorityType.generateDescription(getTotalCommission());

        return result;
    }

    //allows specialised abstractions like Scheduling to access the reports of the order
    protected Map<Report, Integer> getReports() {
        return reports;
    }

    //allows specialised abstractions like Scheduling to access the finalised boolean
    protected boolean isFinalised() {
        return finalised;
    }
}
