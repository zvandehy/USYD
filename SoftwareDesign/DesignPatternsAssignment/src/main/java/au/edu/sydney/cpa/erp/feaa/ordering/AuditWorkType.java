package au.edu.sydney.cpa.erp.feaa.ordering;

import au.edu.sydney.cpa.erp.ordering.Report;

/**
 * Concrete Audit work type implementation of WorkType Implementor of bridge
 */
public class AuditWorkType implements WorkType {

    //uses the default constructor because it doesn't depend on variables like maxCountEmployees

    @Override
    public double calculateReportCost(Report report, int employeeCount) {
        //Report Cost depends on every employee
        return report.getCommission() * employeeCount;
    }

    @Override
    public String generateReportInvoiceMessage(Report report, int employeeCount) {
        double subtotal = calculateReportCost(report, employeeCount);
        StringBuilder sb = new StringBuilder();

        //this report is never capped
        sb.append("\tReport name: ");
        sb.append(report.getReportName());
        sb.append("\tEmployee Count: ");
        sb.append(employeeCount);
        sb.append("\tCost per employee: ");
        sb.append(String.format("$%,.2f", report.getCommission()));
        sb.append("\tSubtotal: ");
        sb.append(String.format("$%,.2f\n", subtotal));

        return sb.toString();
    }

    @Override
    public String generateReportDescription(Report report, int employeeCount) {
        StringBuilder reportSB = new StringBuilder();

        //the subtotal depends on the work type
        reportSB.append(String.format("\tReport name: %s\tEmployee Count: %d\tCommission per employee: $%,.2f\tSubtotal: $%,.2f\n",
                report.getReportName(),
                employeeCount,
                report.getCommission(),
                calculateReportCost(report, employeeCount)));
        //this report is never capped

        return reportSB.toString();
    }
}
