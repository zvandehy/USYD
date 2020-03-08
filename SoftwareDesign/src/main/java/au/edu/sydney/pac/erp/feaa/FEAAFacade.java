package au.edu.sydney.pac.erp.feaa;

import au.edu.sydney.pac.erp.client.Client;
import au.edu.sydney.pac.erp.client.ClientList;

import java.util.List;

/**
 * The main access view for users of the FEAA module.
 */
public interface FEAAFacade {
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
     * @throws IllegalStateException If the FEAAFacade has a null ClientProvider
     * @throws IllegalArgumentException If any argument requirements are breached
     */
    Client addClient(String fName, String lName, String phoneNumber) throws IllegalStateException, IllegalArgumentException;

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
     * @throws IllegalStateException If the FEAAFacade has a null ClientProvider. If the given (valid) client ID does not match an <i>unassigned</i> client in the current client provider.
     * @throws IllegalArgumentException If any of the argument requirements are breached.
     */
    void assignClient(int clientID, String departmentCode) throws IllegalStateException, IllegalArgumentException;

    /**
     * Returns a list of all current clients represented by "last name, first name"<br><br>
     * <b>Preconditions:</b><br>
     * None<br>
     * <b>Postconditions:</b><br>
     * None<br>
     *
     * @return A list of all current clients represented by "last name, first name". In the case no clients can be found, returns an empty list.
     */
    List<String> viewAllClients(); // lname, fname

    /**
     * Returns a list of all current clients.<br><br>
     * <b>Preconditions:</b><br>
     * The FEAAFacade must have been injected with a non-null instance of a Client module via FEAAFacade.setClientProvider<br>
     * <b>Postconditions:</b><br>
     * None<br>
     *
     * @return A list of all current clients represented by "last name, first name".
     * @throws IllegalStateException If the FEAAFacade has a null ClientProvider.
     */
    List<Client> getAllClients() throws IllegalStateException;

    /**
     * Adds an account to the given client<br><br>
     * <b>Preconditions:</b><br>
     * The FEAAFacade must have been injected with a non-null instance of a Client module via FEAAFacade.setClientProvider<br>
     * <b>Postconditions:</b><br>
     * An account will be added to the given client with the provided details.<br>
     *
     * @param accountID The account ID must be unique, may not be negative, may not be zero. The account ID may be null, in which case a generated unique ID will be used. This is only available so long as all previous account IDs have been null - if an ID is provided, all <i>future</i> calls must also provide a unique ID.
     * @param clientID The client to assign. Must be positive (may not be zero).
     * @param accountName The name of the account to add. May not be null or empty.
     * @param initialIncomings The initial income portion of the account. May not be negative, may be zero.
     * @param initialOutgoings The initial outgoings  portion of the account. May not be negative, may be zero.
     * @return The id of the created account.
     * @throws IllegalStateException If the FEAAFacade has a null ClientProvider. If the given (valid) client ID does not match a client in the current client provider.
     * @throws IllegalArgumentException If any of the argument requirements are breached.
     */
    int addAccount(Integer accountID, int clientID, String accountName, int initialIncomings, int initialOutgoings) throws IllegalStateException, IllegalArgumentException;

    /**
     * Returns a list of all current accounts.<br><br>
     * <b>Preconditions:</b><br>
     * None<br>
     * <b>Postconditions:</b><br>
     * None<br>
     *
     * @return A list of all current accounts represented by "account id: account name". In the case no accounts can be found, returns an empty list.
     */
    List<String> getAccounts();

    /**
     * Returns a list of all current accounts matching the given client.<br><br>
     * <b>Preconditions:</b><br>
     * The FEAAFacade must have been injected with a non-null instance of a Client module via FEAAFacade.setClientProvider<br>
     * <b>Postconditions:</b><br>
     * None<br>
     *
     * @param clientID The client to assign. Must be positive (may not be zero).
     * @return A list of all current account IDs owned by the given client. In the case no accounts can be found, returns an empty list.
     * @throws IllegalStateException If the FEAAFacade has a null ClientProvider. If the given (valid) client ID does not match a client in the current client provider.
     * @throws IllegalArgumentException If any of the argument requirements are breached.
     */
    List<Integer> getAccounts(int clientID) throws IllegalStateException, IllegalArgumentException;

    /**
     * Gets the name of the given account.<br><br>
     * <b>Preconditions:</b><br>
     * The FEAAFacade must have been injected with a non-null instance of a Client module via FEAAFacade.setClientProvider<br>
     * <b>Postconditions:</b><br>
     * None<br>
     *
     * @param id The account to retrieve. Must be positive, may not be zero.
     * @return The name of the account
     * @throws IllegalStateException If the FEAAFacade has a null ClientProvider. If the given (valid) account ID does not match a known account
     * @throws IllegalArgumentException If any of the argument requirements are breached.
     */
    String getAccountName(int id) throws IllegalStateException, IllegalArgumentException;

    /**
     * Gets the balance of the given account.<br><br>
     * <b>Preconditions:</b><br>
     * The FEAAFacade must have been injected with a non-null instance of a Client module via FEAAFacade.setClientProvider<br>
     * <b>Postconditions:</b><br>
     * None<br>
     *
     * @param id The account id to retrieve. Must be positive, may not be zero.
     * @return The account's balance, i.e. incomings - outgoings.
     * @throws IllegalStateException If the FEAAFacade has a null ClientProvider. If the given (valid) account ID does not match a known account
     * @throws IllegalArgumentException If any of the argument requirements are breached.
     */
    int getAccountBalance(int id) throws IllegalStateException, IllegalArgumentException;

    /**
     * Gets the income portion of the given account.<br><br>
     * <b>Preconditions:</b><br>
     * The FEAAFacade must have been injected with a non-null instance of a Client module via FEAAFacade.setClientProvider<br>
     * <b>Postconditions:</b><br>
     * None<br>
     *
     * @param id The account id to retrieve. Must be positive, may not be zero.
     * @return The account's incomings
     * @throws IllegalStateException If the FEAAFacade has a null ClientProvider. If the given (valid) account ID does not match a known account
     * @throws IllegalArgumentException If any of the argument requirements are breached.
     */
    int getAccountIncomings(int id) throws IllegalStateException, IllegalArgumentException;

    /**
     * Gets the outgoing portion of the given account.<br><br>
     * <b>Preconditions:</b><br>
     * The FEAAFacade must have been injected with a non-null instance of a Client module via FEAAFacade.setClientProvider<br>
     * <b>Postconditions:</b><br>
     * None<br>
     *
     * @param id The account id to retrieve. Must be positive, may not be zero.
     * @return The account's outgoings
     * @throws IllegalStateException If the FEAAFacade has a null ClientProvider. If the given (valid) account ID does not match a known account
     * @throws IllegalArgumentException If any of the argument requirements are breached.
     */
    int getAccountOutgoings(int id) throws IllegalStateException, IllegalArgumentException;

    /**
     * Sets the income portion of the given account.<br><br>
     * <b>Preconditions:</b><br>
     * The FEAAFacade must have been injected with a non-null instance of a Client module via FEAAFacade.setClientProvider<br>
     * <b>Postconditions:</b><br>
     * The selected account incomings will reflect the new value.<br>
     *
     * @param id The account id to modify. Must be positive, may not be zero.
     * @param incomings The new incoming portion of the given account. May not be negative, may be zero.
     * @throws IllegalStateException If the FEAAFacade has a null ClientProvider. If the given (valid) account ID does not match a known account
     * @throws IllegalArgumentException If any of the argument requirements are breached.
     */
    void setAccountIncomings(int id, int incomings) throws IllegalStateException, IllegalArgumentException;

    /**
     * Sets the outgoing portion of the given account.<br><br>
     * <b>Preconditions:</b><br>
     * The FEAAFacade must have been injected with a non-null instance of a Client module via FEAAFacade.setClientProvider<br>
     * <b>Postconditions:</b><br>
     * The selected account outgoings will reflect the new value.<br>
     *
     * @param id The account id to modify. Must be positive, may not be zero.
     * @param outgoings The new outgoing portion of the given account. May not be negative, may be zero.
     * @throws IllegalStateException If the FEAAFacade has a null ClientProvider. If the given (valid) account ID does not match a known account
     * @throws IllegalArgumentException If any of the argument requirements are breached.
     */
    void setAccountOutgoings(int id, int outgoings) throws IllegalStateException, IllegalArgumentException;

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
}
