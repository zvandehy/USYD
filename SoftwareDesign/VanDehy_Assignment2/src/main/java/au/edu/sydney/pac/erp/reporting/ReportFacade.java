package au.edu.sydney.pac.erp.reporting;


import au.edu.sydney.pac.erp.auth.AuthToken;

import java.math.BigDecimal;

public interface ReportFacade {

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * The report's commission will be calculated, recorded in the reporting module, and impact lifetime commission for this account<br>
     *
     * @param token The authentication token to verify this operation with. May not be null.
     * @param accountID The account ID to report for. May not be null or empty. Must be greater than 0.
     * @param clientFName The account client's first name. May not be null or empty.
     * @param clientLName The account client's last name. May not be null or empty.
     * @param balance The balance of the account during this reporting period.
     * @return The commission charged for this report (dollars as the whole number component, cents as the decimal). Will not be negative.
     * @throws SecurityException If the authentication token is null or fails verification.
     * @throws IllegalArgumentException If any of the other preconditions are violated.
     */
    BigDecimal makeReport(AuthToken token, int accountID, String clientFName, String clientLName, int balance) throws IllegalArgumentException, SecurityException;

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * <br>
     *
     * @param token The authentication token to verify this operation with. May not be null.
     * @param accountID The account ID to get the lifetime commission for. May not be null or empty. Must be greater than 0.
     * @return If no reports have been generated returns 0 - otherwise the total commission of all previous reports for this
     * account - will not be negative. Never returns null.
     * @throws SecurityException If the authentication token is null or fails verification.
     * @throws IllegalArgumentException If any of the other preconditions are violated.
     */
    BigDecimal getLifetimeCommission(AuthToken token, int accountID) throws IllegalArgumentException, SecurityException;
}
