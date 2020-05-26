package au.edu.sydney.cpa.erp.feaa.ordering;

/**
 * Concrete critical priority implementation for PriorityType implementor of bridge
 */
public class CriticalPriority implements PriorityType {

    //Extra cost/rate for critical order
    private double criticalLoading;

    /**
     * Create a CriticalPriority PriorityType
     * @param criticalLoading - the increased commission loading rate
     */
    public CriticalPriority(double criticalLoading) {
        this.criticalLoading = criticalLoading;
    }

    @Override
    public String generateInvoiceMessage(double totalLoadedCommission, String reportsData) {
        //Critical invoice messages are more concise
        return String.format("Your priority business account has been charged: $%,.2f" +
                "\nPlease see your internal accounting department for itemised details.", totalLoadedCommission);
    }

    @Override
    public String generateDescription(double loadedCommission) {
        /*
        How baseCommission formula is determined:
        loaded = base + base*critical
        base + base*critical = loaded
        base(1 + critical) = loaded
        base = loaded / (1+critical)
         */
        double baseCommission = loadedCommission / (1+ criticalLoading);

        //critical orders append critical loading information near the end of the long description
        return String.format("Critical Loading: $%,.2f\n" +
                        "Total cost: $%,.2f\n", loadedCommission - baseCommission,
                loadedCommission);
    }

    @Override
    public String generateScheduledInvoiceMessage(double recurringCost, int numQuarters, double totalLoadedCommission, String reportsData) {
        //Critical Invoice messages are more concise
        return String.format("Your priority business account will be charged: $%,.2f each quarter for %d quarters, with a total overall cost of: $%,.2f" +
                "\nPlease see your internal accounting department for itemised details.", recurringCost, numQuarters, totalLoadedCommission);
    }

    @Override
    public String generateScheduledDescription(double totalLoadedCost, int numQuarters) {
        /*
        How baseCommission formula is determined:
        loaded = base + base*critical
        base + base*critical = loaded
        base(1 + critical) = loaded
        base = loaded / (1+critical)
         */
        double totalBaseCost = totalLoadedCost / (1+ criticalLoading);
        //append critical loading and recurring cost information
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
        //load cost is increased by the criticalLoading rate
        return cost + cost*criticalLoading;
    }
}
