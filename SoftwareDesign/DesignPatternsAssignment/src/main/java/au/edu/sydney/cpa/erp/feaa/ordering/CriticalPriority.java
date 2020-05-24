package au.edu.sydney.cpa.erp.feaa.ordering;

public class CriticalPriority implements PriorityType {

    private double criticalLoading;

    public CriticalPriority(double criticalLoading) {
        this.criticalLoading = criticalLoading;
    }

    @Override
    public String generateInvoiceMessage(double totalCommission, String reportsData) {
        return String.format("Your priority business account has been charged: $%,.2f" +
                "\nPlease see your internal accounting department for itemised details.", totalCommission);
    }

    @Override
    public String generateDescription(double loadedCommission) {
        //loaded = base + base*load
        //base + base*load = loaded
        //base(1 + load) = loaded
        //base = loaded / (1+load)
        double baseCommission = loadedCommission / (1+ criticalLoading);
        return String.format("Critical Loading: $%,.2f\n" +
                        "Total cost: $%,.2f\n", loadedCommission - baseCommission,
                loadedCommission);
    }

    @Override
    public String generateScheduledInvoiceMessage(double recurringCost, int numQuarters, double totalCommission, String reportsData) {
        return String.format("Your priority business account will be charged: $%,.2f each quarter for %d quarters, with a total overall cost of: $%,.2f" +
                "\nPlease see your internal accounting department for itemised details.", recurringCost, numQuarters, totalCommission);
    }

    @Override
    public String generateScheduledDescription(double totalLoadedCost, int numQuarters) {
        double totalBaseCost = totalLoadedCost / (1+ criticalLoading);
        return String.format("Critical Loading: $%,.2f\n" +
                "Recurring cost: $%,.2f\n" +
                "Total cost: $%,.2f\n",
                totalLoadedCost - (totalBaseCost),
                totalLoadedCost / numQuarters,
                totalLoadedCost
                );
    }

    @Override
    public double loadCost(double cost) {
        return cost + cost*criticalLoading;
    }
}
