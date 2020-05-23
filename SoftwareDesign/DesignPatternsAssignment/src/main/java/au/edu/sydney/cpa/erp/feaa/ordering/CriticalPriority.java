package au.edu.sydney.cpa.erp.feaa.ordering;

public class CriticalPriority implements PriorityType {

    private double criticalLoading;

    public CriticalPriority(double criticalLoading) {
        this.criticalLoading = criticalLoading;
    }

    @Override
    public String generateInvoiceMessage(double totalCommission) {
        return String.format("Your priority business account has been charged: $%,.2f" +
                "\nPlease see your internal accounting department for itemised details.", totalCommission);
    }

    @Override
    public double getCriticalLoading() {
        return criticalLoading;
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
    public double loadCost(double cost) {
        return cost + cost*criticalLoading;
    }
}
