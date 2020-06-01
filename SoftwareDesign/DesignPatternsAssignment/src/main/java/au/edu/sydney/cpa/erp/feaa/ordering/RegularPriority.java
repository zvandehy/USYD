package au.edu.sydney.cpa.erp.feaa.ordering;

public class RegularPriority implements PriorityType {

    //uses default constructor because regular priority orders don't depend on other information like critical loading

    @Override
    public String generateInvoiceMessage(double totalLoadedCommission, String reportsData) {
        StringBuilder sb = new StringBuilder();
        //regular order invoices are more detailed
        sb.append("Thank you for your Crimson Permanent Assurance accounting order!\n");
        sb.append("The cost to provide these services: $");
        sb.append(String.format("%,.2f", totalLoadedCommission));
        sb.append("\nPlease see below for details:\n");
        //reportsData depends on the WorkType, but needs to be appended to the invoice if not a critical order
        sb.append(reportsData);
        return sb.toString();
    }

    @Override
    public String generateDescription(double totalLoadedCommission) {
        //no extra information to append other than total cost
        return String.format("Total cost: $%,.2f\n", totalLoadedCommission);
    }

    @Override
    public String generateScheduledInvoiceMessage(double recurringCost, int numQuarters, double totalLoadedCommission, String reportsData) {
        StringBuilder sb = new StringBuilder();
        //regular order invoices are more detailed
        sb.append("Thank you for your Crimson Permanent Assurance accounting order!\n");
        sb.append("The cost to provide these services: $");
        sb.append(String.format("%,.2f", recurringCost));
        sb.append(" each quarter, with a total overall cost of: $");
        sb.append(String.format("%,.2f", totalLoadedCommission));
        sb.append("\nPlease see below for details:\n");
        sb.append(reportsData);
        return sb.toString();
    }

    @Override
    public String generateScheduledDescription(double totalLoadedCommission, int numQuarters) {
        //scheduled orders include information about the schedule
        return String.format("Recurring cost: $%,.2f\n" + "Total cost: $%,.2f\n", totalLoadedCommission/numQuarters, totalLoadedCommission);
    }

    @Override
    public double loadCost(double cost) {
        //no extra cost associated with regular priority orders
        return cost;
    }

}
