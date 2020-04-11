package au.edu.sydney.pac.erp.feaa;

import au.edu.sydney.pac.erp.auth.AuthModule;
import au.edu.sydney.pac.erp.client.Client;
import au.edu.sydney.pac.erp.client.ClientList;
import au.edu.sydney.pac.erp.email.EmailService;
import au.edu.sydney.pac.erp.fax.FaxService;
import au.edu.sydney.pac.erp.reporting.ReportFacade;
import au.edu.sydney.pac.erp.print.PrintService;

import java.util.List;

/**
 * The main access view for users of the FEAA module.
 */
public interface FEAAFacade {
    /**
     * Injects a client module provider to the system<br><br>
     * <b>Preconditions:</b><br>
     * None<br>
     * <b>Postconditions:</b><br>
     * The system will call the given provider for all client related operations<br>
     * All accounts will be cleared, and the null ID state for new accounts will be reset.<br>
     *
     * @param provider The provider to inject. May be null, however this will result in some client related operations throwing exceptions.
     */
    void setClientProvider(ClientList provider);

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * All method calls requiring this service will be passed to the given provider until this method is invoked again.<br>
     * If this method is invoked with a null parameter (a legal operation) all such calls will fail.<br>
     * This method will set FEAA to a logged-out state regardless of any prior Auth state<br>
     *
     * @param provider The service provider to be injected
     */
    void setAuthProvider(AuthModule provider);

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * All method calls requiring this service will be passed to the given provider until this method is invoked again.<br>
     * If this method is invoked with a null parameter (a legal operation) all such calls will fail.<br>
     *
     * @param provider The service provider to be injected
     */
    void setEmailProvider(EmailService provider);

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * All method calls requiring this service will be passed to the given provider until this method is invoked again.<br>
     * If this method is invoked with a null parameter (a legal operation) all such calls will fail.<br>
     *
     * @param provider The service provider to be injected
     */
    void setFaxProvider(FaxService provider);

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * All method calls requiring this service will be passed to the given provider until this method is invoked again.<br>
     * If this method is invoked with a null parameter (a legal operation) all such calls will fail.<br>
     *
     * @param provider The service provider to be injected
     */
    void setReportingProvider(ReportFacade provider);

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * All method calls requiring this service will be passed to the given provider until this method is invoked again.<br>
     * If this method is invoked with a null parameter (a legal operation) all such calls will fail.<br>
     *
     * @param provider The service provider to be injected
     */
    void setPrintProvider(PrintService provider);

    /**
     * Adds a new client to the system.<br><br>
     * <b>Preconditions:</b><br>
     * The FEAAFacade must have been injected with a non-null instance of a Client module via FEAAFacade.setClientProvider<br>
     * <b>Postconditions:</b><br>
     * A new client with the given information will be present in the system.<br>
     *
     * @param fName The client's first name. May not be null or empty.
     * @param lName The client's first name. May not be null or empty.
     * @param phoneNumber The client's phone number. May not be null or empty. May only contain digits, spaces, and +().
     * @return The Client that has been added.
     * @throws IllegalStateException If the FEAAFacade has a null ClientProvider. If called with a null Auth provider set.
     * @throws SecurityException if FEAA is not logged in or the logged in user cannot be authenticated
     * @throws IllegalArgumentException If any argument requirements are breached
     */
    Client addClient(String fName, String lName, String phoneNumber) throws SecurityException, IllegalStateException, IllegalArgumentException;

    /**
     * Assigns a client to a department. Note that this is permanent.<br><br>
     * <b>Preconditions:</b><br>
     * The FEAAFacade must have been injected with a non-null instance of a Client module via FEAAFacade.setClientProvider<br>
     * <b>Postconditions:</b><br>
     * The matching client will be assigned to the given department.<br>
     *
     * @param clientID The client to assign. Must be positive (may not be zero).
     * @param departmentCode The department to assign to. Must be one of the following: "DOMESTIC", "INTERNATIONAL", "LARGE ACCOUNTS".<br>
     *                       Shortened versions may also be used: "DOM", "INT", "LRG"
     * @throws IllegalStateException If the FEAAFacade has a null ClientProvider. If the given (valid) client ID does not match an <i>unassigned</i> client in the current client provider. If called with a null Auth provider set.
     * @throws SecurityException if FEAA is not logged in or the logged in user cannot be authenticated
     * @throws IllegalArgumentException If any of the argument requirements are breached.
     */
    void assignClient(int clientID, String departmentCode) throws SecurityException, IllegalStateException, IllegalArgumentException;

    /**
     * Returns a list of all current clients represented by "last name, first name"<br><br>
     * <b>Preconditions:</b><br>
     * None<br>
     * <b>Postconditions:</b><br>
     * None<br>
     *
     * @return A list of all current clients represented by "last name, first name". In the case no clients can be found, returns an empty list.
     * @throws SecurityException if FEAA is not logged in or the logged in user cannot be authenticated
     * @throws IllegalStateException if called with a null Auth provider set.
     */
    List<String> viewAllClients() throws SecurityException, IllegalStateException; // lname, fname

    /**
     * Returns a list of all current clients.<br><br>
     * <b>Preconditions:</b><br>
     * The FEAAFacade must have been injected with a non-null instance of a Client module via FEAAFacade.setClientProvider<br>
     * <b>Postconditions:</b><br>
     * None<br>
     *
     * @return A list of all current clients represented by "last name, first name".
     * @throws IllegalStateException If the FEAAFacade has a null ClientProvider or AuthProvider.
     * @throws SecurityException if FEAA is not logged in or the logged in user cannot be authenticated
     */
    List<Client> getAllClients() throws SecurityException, IllegalStateException;

    /**
     * Adds an account to the given client<br><br>
     * <b>Preconditions:</b><br>
     * One of either phone or email must not be null<br>
     * The FEAAFacade must have been injected with a non-null instance of a Client module via FEAAFacade.setClientProvider<br>
     * The FEAAFacade must have been injected with a non-null instance of an AuthModule via FEAAFacade.setAuthProvider<br>
     * FEAA must be in a logged-in state prior to this call<br>
     * <b>Postconditions:</b><br>
     * An account will be added to the given client with the provided details.<br>
     *
     * @param accountID The account ID must be unique, may not be negative, may not be zero. The account ID may be null, in which case a generated unique ID will be used. This is only available so long as all previous account IDs have been null - if an ID is provided, all <i>future</i> calls must also provide a unique ID.
     * @param clientID The client to assign. Must be positive (may not be zero).
     * @param accountName The name of the account to add. May not be null or empty.
     * @param initialIncomings The initial income portion of the account. May not be negative, may be zero.
     * @param initialOutgoings The initial outgoings  portion of the account. May not be negative, may be zero.
     * @param reportPhone may be null, may not be empty, may only contain numeric characters and ‘+()’ if not null
     * @param reportEmail may be null, may not be empty, must contain at least one @ character if not null
     * @return The id of the created account.
     * @throws IllegalStateException If the FEAAFacade has a null ClientProvider or AuthProvider. If the given (valid) client ID does not match a client in the current client provider. If a null account ID is given after an integer ID was given.
     * @throws SecurityException if FEAA is not logged in or the logged in user cannot be authenticated
     * @throws IllegalArgumentException If any of the other preconditions are breached.
     */
    int addAccount(Integer accountID, int clientID, String accountName, int initialIncomings, int initialOutgoings, String reportPhone, String reportEmail) throws SecurityException, IllegalStateException, IllegalArgumentException;

    /**
     * Returns a list of all current accounts.<br><br>
     * <b>Preconditions:</b><br>
     * None<br>
     * <b>Postconditions:</b><br>
     * None<br>
     *
     * @return A list of all current accounts represented by "account id: account name". In the case no accounts can be found, returns an empty list.
     * @throws SecurityException if FEAA is not logged in or the logged in user cannot be authenticated
     * @throws IllegalStateException if called with a null Auth provider set.
     */
    List<String> getAccounts() throws SecurityException, IllegalStateException;

    /**
     * Returns a list of all current accounts matching the given client.<br><br>
     * <b>Preconditions:</b><br>
     * The FEAAFacade must have been injected with a non-null instance of a Client module via FEAAFacade.setClientProvider<br>
     * <b>Postconditions:</b><br>
     * None<br>
     *
     * @param clientID The client to assign. Must be positive (may not be zero).
     * @return A list of all current account IDs owned by the given client. In the case no accounts can be found, returns an empty list.
     * @throws SecurityException if FEAA is not logged in or the logged in user cannot be authenticated
     * @throws IllegalStateException If the FEAAFacade has a null ClientProvider. If the given (valid) client ID does not match a client in the current client provider. If called with a null Auth provider set.
     * @throws IllegalArgumentException If any of the other preconditions are breached.
     */
    List<Integer> getAccounts(int clientID) throws SecurityException, IllegalStateException, IllegalArgumentException;

    /**
     * Gets the name of the given account.<br><br>
     * <b>Preconditions:</b><br>
     * The FEAAFacade must have been injected with a non-null instance of a Client module via FEAAFacade.setClientProvider<br>
     * <b>Postconditions:</b><br>
     * None<br>
     *
     * @param id The account to retrieve. Must be positive, may not be zero.
     * @return The name of the account
     * @throws SecurityException if FEAA is not logged in or the logged in user cannot be authenticated
     * @throws IllegalStateException If the FEAAFacade has a null ClientProvider. If the given (valid) account ID does not match a known account. If called with a null Auth provider set.
     * @throws IllegalArgumentException If any of the other preconditions are breached.
     */
    String getAccountName(int id) throws SecurityException, IllegalStateException, IllegalArgumentException;

    /**
     * Gets the balance of the given account.<br><br>
     * <b>Preconditions:</b><br>
     * The FEAAFacade must have been injected with a non-null instance of a Client module via FEAAFacade.setClientProvider<br>
     * <b>Postconditions:</b><br>
     * None<br>
     *
     * @param id The account id to retrieve. Must be positive, may not be zero.
     * @return The account's balance, i.e. incomings - outgoings.
     * @throws SecurityException if FEAA is not logged in or the logged in user cannot be authenticated
     * @throws IllegalStateException If the FEAAFacade has a null ClientProvider. If the given (valid) account ID does not match a known account. If called with a null Auth provider set.
     * @throws IllegalArgumentException If any of the other preconditions are breached.
     */
    int getAccountBalance(int id) throws SecurityException, IllegalStateException, IllegalArgumentException;

    /**
     * Gets the income portion of the given account.<br><br>
     * <b>Preconditions:</b><br>
     * The FEAAFacade must have been injected with a non-null instance of a Client module via FEAAFacade.setClientProvider<br>
     * <b>Postconditions:</b><br>
     * None<br>
     *
     * @param id The account id to retrieve. Must be positive, may not be zero.
     * @return The account's incomings
     * @throws SecurityException if FEAA is not logged in or the logged in user cannot be authenticated
     * @throws IllegalStateException If the FEAAFacade has a null ClientProvider. If the given (valid) account ID does not match a known account. If called with a null Auth provider set.
     * @throws IllegalArgumentException If any of the other preconditions are breached.
     */
    int getAccountIncomings(int id) throws SecurityException, IllegalStateException, IllegalArgumentException;

    /**
     * Gets the outgoing portion of the given account.<br><br>
     * <b>Preconditions:</b><br>
     * The FEAAFacade must have been injected with a non-null instance of a Client module via FEAAFacade.setClientProvider<br>
     * <b>Postconditions:</b><br>
     * None<br>
     *
     * @param id The account id to retrieve. Must be positive, may not be zero.
     * @return The account's outgoings
     * @throws SecurityException if FEAA is not logged in or the logged in user cannot be authenticated
     * @throws IllegalStateException If the FEAAFacade has a null ClientProvider. If the given (valid) account ID does not match a known account. If called with a null Auth provider set.
     * @throws IllegalArgumentException If any of the other preconditions are breached.
     */
    int getAccountOutgoings(int id) throws SecurityException, IllegalStateException, IllegalArgumentException;

    /**
     * Sets the income portion of the given account.<br><br>
     * <b>Preconditions:</b><br>
     * The FEAAFacade must have been injected with a non-null instance of a Client module via FEAAFacade.setClientProvider<br>
     * <b>Postconditions:</b><br>
     * The selected account incomings will reflect the new value.<br>
     *
     * @param id The account id to modify. Must be positive, may not be zero.
     * @param incomings The new incoming portion of the given account. May not be negative, may be zero.
     * @throws SecurityException if FEAA is not logged in or the logged in user cannot be authenticated
     * @throws IllegalStateException If the FEAAFacade has a null ClientProvider. If the given (valid) account ID does not match a known account. If called with a null Auth provider set.
     * @throws IllegalArgumentException If any of the other preconditions are breached.
     */
    void setAccountIncomings(int id, int incomings) throws SecurityException, IllegalStateException, IllegalArgumentException;

    /**
     * Sets the outgoing portion of the given account.<br><br>
     * <b>Preconditions:</b><br>
     * The FEAAFacade must have been injected with a non-null instance of a Client module via FEAAFacade.setClientProvider<br>
     * <b>Postconditions:</b><br>
     * The selected account outgoings will reflect the new value.<br>
     *
     * @param id The account id to modify. Must be positive, may not be zero.
     * @param outgoings The new outgoing portion of the given account. May not be negative, may be zero.
     * @throws SecurityException if FEAA is not logged in or the logged in user cannot be authenticated
     * @throws IllegalStateException If the FEAAFacade has a null ClientProvider. If the given (valid) account ID does not match a known account. If called with a null Auth provider set.
     * @throws IllegalArgumentException If any of the other preconditions are breached.
     */
    void setAccountOutgoings(int id, int outgoings) throws SecurityException, IllegalStateException, IllegalArgumentException;

    /**
     * <b>Preconditions:</b><br>
     * An account with the matching id exists in this instance<br>
     * At least one preference must be set.
     * The FEAAFacade must have been injected with a non-null instance of an AuthModule via FEAAFacade.setAuthProvider<br>
     * FEAA must be in a logged-in state prior to this call<br>
     * <b>Postconditions:</b><br>
     * An report will be sent for all true preference parameter channels for all subsequent reports<br>
     *
     * @param accountID The target account id
     * @param email Send an email invoice when this account gets a report
     * @param print Send a printed invoice when this account gets a report
     * @param fax Send an faxed invoice when this account gets a report
     * @throws SecurityException if FEAA is not logged in or the logged in user cannot be authenticated
     * @throws IllegalArgumentException if any of the parameter preconditions are breached
     * @throws IllegalStateException if called with a null Auth provider set
     */
    void setReportPreferences(int accountID, boolean email, boolean print, boolean fax) throws SecurityException, IllegalArgumentException, IllegalStateException;

    /**
     * <b>Preconditions:</b><br>
     * An account with the matching id exists in this instance<br>
     * The FEAAFacade must have been injected with a non-null instance of an AuthModule via FEAAFacade.setAuthProvider<br>
     * FEAA must be in a logged-in state prior to this call<br>
     * The FEAAFacade must have a corresponding service provider for any channel preference the given account has set (via setReportPreferences)
     * <b>Postconditions:</b><br>
     * A report will be sent for this account. Reports will be generated for all set customer preferences through
     * the appropriate services. Commission will be charged to this account.
     *
     * @param accountID The target account id
     * @return The rounded (0.5 = 1) dollar commission for this report. Will be 0 or more.
     * @throws SecurityException if FEAA is not logged in or the logged in user cannot be authenticated
     * @throws IllegalArgumentException if any of the parameter preconditions are breached
     * @throws IllegalStateException if any required provider (Auth, invoice channels) is null
     */
    int makeReport(int accountID) throws SecurityException, IllegalArgumentException, IllegalStateException;

    /**
     * <b>Preconditions:</b><br>
     * An account with the matching id exists in this instance<br>
     * The FEAAFacade must have been injected with a non-null instance of an AuthModule via FEAAFacade.setAuthProvider<br>
     * FEAA must be in a logged-in state prior to this call<br>
     * <b>Postconditions:</b><br>
     * <br>
     * @param accountID The target account id
     * @return The rounded (0.5 = 1) dollar cost of this account's lifetime commission. Will return a minimum of 1 if any
     * commission exists regardless of rounding, 0 if no commission exists.
     * @throws SecurityException if FEAA is not logged in or the logged in user cannot be authenticated
     * @throws IllegalArgumentException if any of the parameter preconditions are breached
     * @throws IllegalStateException if any required provider (Auth, invoice channels) is null
     */
    int getTotalLifetimeCommission(int accountID) throws SecurityException, IllegalArgumentException, IllegalStateException;

    /**
     * <b>Preconditions:</b><br>
     * The FEAAFacade must have been injected with a non-null instance of an AuthModule via FEAAFacade.setAuthProvider<br>
     * The FEAAFacade must not be in a logged-in state prior to this call<br>
     * <b>Postconditions:</b><br>
     * FEAA will be in a logged in state until its Auth provider is changed or logout() is called.<br>
     * @param userName The username to verify. May not be null or empty.
     * @param password The password to verify. May not be null or empty.
     * @return True if the credentials were valid, otherwise false.
     * @throws IllegalArgumentException if any of the parameter preconditions are breached
     * @throws IllegalStateException if called with a null Auth provider set, or if already in a logged-in state
     */
    boolean login(String userName, String password) throws IllegalArgumentException, IllegalStateException;

    /**
     * <b>Preconditions:</b><br>
     * The FEAAFacade must have been injected with a non-null instance of an AuthModule via FEAAFacade.setAuthProvider<br>
     * FEAA must be in a logged-in state prior to this call<br>
     * <b>Postconditions:</b><br>
     * FEAA will no longer be in a logged-in state<br>
     * @throws IllegalStateException if called with a null Auth provider set, or if FEAA was not already in a logged-in state
     */
    void logout() throws IllegalStateException;
}
