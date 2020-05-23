package au.edu.sydney.cpa.erp.feaa.ordering;

import au.edu.sydney.cpa.erp.ordering.Report;

public interface WorkType {

    double calculateReportCost(Report report, int employeeCount);
    String generateInvoiceMessage(Report report, int employeeCount);
    String generateDescription(Report report, int employeeCount);
}
