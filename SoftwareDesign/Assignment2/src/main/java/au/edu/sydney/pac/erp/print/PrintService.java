package au.edu.sydney.pac.erp.print;


import au.edu.sydney.pac.erp.auth.AuthToken;

public interface PrintService {

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * <br>
     *
     * @param token The authentication token to verify this operation with. May not be null.
     * @param data The data to be printed. May not be null or empty.
     * @throws SecurityException If the authentication token is null or fails verification.
     * @throws IllegalArgumentException If any of the other preconditions are violated.
     */
    void printReport(AuthToken token, String data) throws SecurityException, IllegalArgumentException;
}
