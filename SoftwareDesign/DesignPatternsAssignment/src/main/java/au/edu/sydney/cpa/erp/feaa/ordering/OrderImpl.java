package au.edu.sydney.cpa.erp.feaa.ordering;


import au.edu.sydney.cpa.erp.ordering.Order;
import au.edu.sydney.cpa.erp.ordering.Report;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrderImpl implements Order {

    private Map<Report, Integer> reports = new HashMap<>();
    private final int id;
    private LocalDateTime date;
    private int clientID;
    private boolean finalised = false;
    private PriorityType priorityType;
    private WorkType workType;

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
        double cost = 0.0;
        for (Report report : reports.keySet()) {
            cost += workType.calculateReportCost(report, reports.get(report));
        }
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

        for (Report contained: reports.keySet()) {
            if (contained.equals(report)) {
                report = contained;
                break;
            }
        }

        reports.put(report, employeeCount);
    }

    @Override
    public Set<Report> getAllReports() {
        return reports.keySet();
    }

    @Override
    public int getReportEmployeeCount(Report report) {
        for (Report contained: reports.keySet()) {
            if (contained.equals(report)) {
                report = contained;
                break;
            }
        }
        Integer result = reports.get(report);
        return null == result ? 0 : result;
    }

    @Override
    public String generateInvoiceData() {
        String result = priorityType.generateInvoiceMessage(getTotalCommission());

        if(priorityType instanceof CriticalPriority) return result;

        List<Report> keyList = new ArrayList<>(reports.keySet());
        keyList.sort(Comparator.comparing(Report::getReportName).thenComparing(Report::getCommission));
        for (Report report : keyList) {
            result += workType.generateInvoiceMessage(report, reports.get(report));

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
        return String.format("ID:%s $%,.2f", id, getTotalCommission());
    }

    @Override
    public String longDesc() {
        StringBuilder reportSB = new StringBuilder();

        List<Report> keyList = new ArrayList<>(reports.keySet());
        keyList.sort(Comparator.comparing(Report::getReportName).thenComparing(Report::getCommission));

        for (Report report : keyList) {
            reportSB.append(workType.generateDescription(report, reports.get(report)));

        }

        String result = String.format(finalised ? "" : "*NOT FINALISED*\n" +
                        "Order details (id #%d)\n" +
                        "Date: %s\n" +
                        "Reports:\n" +
                        "%s",
                        id,
                date.format(DateTimeFormatter.ISO_LOCAL_DATE),
                reportSB.toString()
        );

        result += priorityType.generateDescription(getTotalCommission());

        return result;
    }
}
