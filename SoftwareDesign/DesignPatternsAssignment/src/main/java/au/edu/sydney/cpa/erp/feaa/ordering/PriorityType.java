package au.edu.sydney.cpa.erp.feaa.ordering;

public interface PriorityType {

    String generateInvoiceMessage(double totalCommission);
    double getCriticalLoading();
    String generateDescription(double totalLoadedCommission);
    double loadCost(double cost);

}
