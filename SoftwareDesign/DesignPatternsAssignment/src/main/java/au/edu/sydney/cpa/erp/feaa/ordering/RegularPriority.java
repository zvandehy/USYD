package au.edu.sydney.cpa.erp.feaa.ordering;

public class RegularPriority implements PriorityType {

    @Override
    public String generateInvoiceMessage(double totalCommission) {
        StringBuilder sb = new StringBuilder();

        sb.append("Thank you for your Crimson Permanent Assurance accounting order!\n");
        sb.append("The cost to provide these services: $");
        sb.append(String.format("%,.2f", totalCommission));
        sb.append("\nPlease see below for details:\n");
        return sb.toString();
    }

    @Override
    public double getCriticalLoading() {
        return 1.0;
    }

    @Override
    public String generateDescription(double totalLoadedCommission) {
        return String.format("Total cost: $%,.2f\n", totalLoadedCommission);
    }

    @Override
    public double loadCost(double cost) {
        return cost;
    }

}
