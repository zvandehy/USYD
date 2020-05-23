package au.edu.sydney.cpa.erp.feaa.ordering;

import au.edu.sydney.cpa.erp.ordering.Report;

public class RegularWorkType implements WorkType {

    private int maxCountEmployees;

    public RegularWorkType(int maxCountEmployees) {
        this.maxCountEmployees = maxCountEmployees;
    }

    @Override
    public double calculateReportCost(Report report, int employeeCount) {
        return report.getCommission() * Math.min(maxCountEmployees, employeeCount);
    }

    @Override
    public String generateInvoiceMessage(Report report, int employeeCount) {
        double subtotal = calculateReportCost(report, employeeCount);
        StringBuilder sb = new StringBuilder();

        sb.append("\tReport name: ");
        sb.append(report.getReportName());
        sb.append("\tEmployee Count: ");
        sb.append(employeeCount);
        sb.append("\tCost per employee: ");
        sb.append(String.format("$%,.2f", report.getCommission()));
        if (employeeCount > maxCountEmployees) {
            sb.append("\tThis report cost has been capped.");
        }
        sb.append("\tSubtotal: ");
        sb.append(String.format("$%,.2f\n", subtotal));
        return sb.toString();
    }

    @Override
    public String generateDescription(Report report, int employeeCount) {
        StringBuilder reportSB = new StringBuilder();

        reportSB.append(String.format("\tReport name: %s\tEmployee Count: %d\tCommission per employee: $%,.2f\tSubtotal: $%,.2f",
                report.getReportName(),
                employeeCount,
                report.getCommission(),
                calculateReportCost(report, employeeCount)));

        if (employeeCount > maxCountEmployees) {
            reportSB.append(" *CAPPED*\n");
        } else {
            reportSB.append("\n");
        }
        return reportSB.toString();
    }
}
