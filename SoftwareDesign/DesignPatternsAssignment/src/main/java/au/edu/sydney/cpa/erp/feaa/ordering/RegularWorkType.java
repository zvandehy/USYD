package au.edu.sydney.cpa.erp.feaa.ordering;

import au.edu.sydney.cpa.erp.ordering.Report;


public class RegularWorkType implements WorkType {

    //limit the report to this many employees
    private int maxCountEmployees;

    /**
     * Create a RegularWorkType order
     * @param maxCountEmployees - the number of employees to cap the order
     */
    public RegularWorkType(int maxCountEmployees) {
        this.maxCountEmployees = maxCountEmployees;
    }

    @Override
    public double calculateReportCost(Report report, int employeeCount) {
        //regular work orders limit the cost to a max number of employees.
        return report.getCommission() * Math.min(maxCountEmployees, employeeCount);
    }

    @Override
    public String generateReportInvoiceMessage(Report report, int employeeCount) {
        double subtotal = calculateReportCost(report, employeeCount);
        StringBuilder sb = new StringBuilder();

        sb.append("\tReport name: ");
        sb.append(report.getReportName());
        sb.append("\tEmployee Count: ");
        sb.append(employeeCount);
        sb.append("\tCost per employee: ");
        sb.append(String.format("$%,.2f", report.getCommission()));
        //if the order is capped, it needs to be noted in the invoice
        if (employeeCount > maxCountEmployees) {
            sb.append("\tThis report cost has been capped.");
        }
        sb.append("\tSubtotal: ");
        sb.append(String.format("$%,.2f\n", subtotal));
        return sb.toString();
    }


    @Override
    public String generateReportDescription(Report report, int employeeCount) {
        StringBuilder reportSB = new StringBuilder();

        //subtotal for this report depends on worktype and in this case the max number of employees
        reportSB.append(String.format("\tReport name: %s\tEmployee Count: %d\tCommission per employee: $%,.2f\tSubtotal: $%,.2f",
                report.getReportName(),
                employeeCount,
                report.getCommission(),
                calculateReportCost(report, employeeCount)));
        //if the order is capped, it needs to be noted in the description
        if (employeeCount > maxCountEmployees) {
            reportSB.append(" *CAPPED*\n");
        } else {
            reportSB.append("\n");
        }
        return reportSB.toString();
    }
}
