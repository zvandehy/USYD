package au.edu.sydney.cpa.erp.feaa.ordering;

/**
 * PriorityType is an implementor in the bridge. It is currently either Critical or Regular.
 */
public interface PriorityType {

    /**
     * Returns the main/initial invoice message for the Order.
     * @param totalLoadedCommission - total commission (after any critical loading) of Order
     * @param reportsData - summarised invoice data about the reports in the Order
     * @return an invoice message for the Order
     */
    String generateInvoiceMessage(double totalLoadedCommission, String reportsData);

    /**
     * Create a formatted string to append to the end of a long Order description
     * @param totalLoadedCommission - total commission (after any critical loading) of Order
     * @return a formatted string to append to the end of a long Order description
     */
    String generateDescription(double totalLoadedCommission);

    /**
     * Similar to generateInvoiceMessage() but also includes information specific to ScheduledOrders
     * @param recurringCost - the recurring cost of the Scheduled Order
     * @param numQuarters - the number of quarters for the Scheduled Order
     * @param totalLoadedCommission - total commission (after any critical loading) of Scheduled Order
     * @param reportsData - summarised invoice data about the reports in the Scheduled Order
     * @return an invoice message for the Scheduled Order
     */
    String generateScheduledInvoiceMessage(double recurringCost, int numQuarters, double totalLoadedCommission, String reportsData);

    /**
     * Similar to generateDescription() but also includes information specific to ScheduledOrders
     * @param totalLoadedCost - total commission (after any critical loading) of Scheduled Order
     * @param numQuarters - the number of quarters for the Scheduled Order
     * @return an formatted string to append to teh end for a long Scheduled Order description
     */
    String generateScheduledDescription(double totalLoadedCost, int numQuarters);

    /**
     * Calculate the total loaded commission of an Order given the base commission of said Order
     * @param cost - the cost of the order before any critical loading is applied
     * @return the total loaded commission
     */
    double loadCost(double cost);

}
