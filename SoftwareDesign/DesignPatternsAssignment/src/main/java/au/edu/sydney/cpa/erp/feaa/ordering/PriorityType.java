package au.edu.sydney.cpa.erp.feaa.ordering;

public interface PriorityType {

    String generateInvoiceMessage(double totalCommission, String reportsData);
    String generateDescription(double totalLoadedCommission);
    String generateScheduledInvoiceMessage(double recurringCost, int numQuarters, double totalCommission, String reportsData);
    String generateScheduledDescription(double totalLoadedCost, int numQuarters);
    double loadCost(double cost);

}
