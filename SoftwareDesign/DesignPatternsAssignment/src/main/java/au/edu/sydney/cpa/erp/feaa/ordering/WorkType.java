package au.edu.sydney.cpa.erp.feaa.ordering;

import au.edu.sydney.cpa.erp.ordering.Report;

/**
 * WorkType is an implementor in the bridge. It currently focuses on how report information is calculated or generated. It is currently either Audit or Regular.
 */
public interface WorkType {

    /**
     * Get the commission cost for the provided report given the number of employees
     * @param report - the report in question
     * @param employeeCount - the number of employees
     * @return the commission cost of this report. May depend on a max number of employees.
     */
    double calculateReportCost(Report report, int employeeCount);

    /**
     * Summarise the invoice information for this report
     * @param report - the report in question
     * @param employeeCount - the number of employees
     * @return an invoice message with a summary of the invoice information for the report
     */
    String generateReportInvoiceMessage(Report report, int employeeCount);

    /**
     * Summarise the information for this report
     * @param report - the report in question
     * @param employeeCount - the number of employees
     * @return a description summary for the information in the report
     */
    String generateReportDescription(Report report, int employeeCount);


}
