package au.edu.sydney.cpa.erp.feaa.ordering;

public class RegularPriority implements PriorityType {

    @Override
    public String generateInvoiceMessage(double totalCommission, String reportsData) {
        StringBuilder sb = new StringBuilder();

        sb.append("Thank you for your Crimson Permanent Assurance accounting order!\n");
        sb.append("The cost to provide these services: $");
        sb.append(String.format("%,.2f", totalCommission));
        sb.append("\nPlease see below for details:\n");
        sb.append(reportsData);
        return sb.toString();
    }

    @Override
    public String generateDescription(double totalLoadedCommission) {
        return String.format("Total cost: $%,.2f\n", totalLoadedCommission);
    }

    @Override
    public String generateScheduledInvoiceMessage(double recurringCost, int numQuarters, double totalCommission, String reportsData) {
        StringBuilder sb = new StringBuilder();

        sb.append("Thank you for your Crimson Permanent Assurance accounting order!\n");
        sb.append("The cost to provide these services: $");
        sb.append(String.format("%,.2f", recurringCost));
        sb.append(" each quarter, with a total overall cost of: $");
        sb.append(String.format("%,.2f", totalCommission));
        sb.append("\nPlease see below for details:\n");
        sb.append(reportsData);
        return sb.toString();
    }

    @Override
    public String generateScheduledDescription(double totalLoadedCommission, int numQuarters) {
        return String.format("Recurring cost: $%,.2f\n" + "Total cost: $%,.2f\n", totalLoadedCommission/numQuarters, totalLoadedCommission);
    }

    @Override
    public double loadCost(double cost) {
        return cost;
    }

}
