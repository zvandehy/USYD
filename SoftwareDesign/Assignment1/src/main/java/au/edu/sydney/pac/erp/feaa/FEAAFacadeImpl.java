package au.edu.sydney.pac.erp.feaa;

import au.edu.sydney.pac.erp.client.Client;
import au.edu.sydney.pac.erp.client.ClientList;

import java.util.List;

public class FEAAFacadeImpl implements FEAAFacade {
    @Override
    public Client addClient(String fName, String lName, String phoneNumber) throws IllegalStateException, IllegalArgumentException {
        return null;
    }

    @Override
    public void assignClient(int clientID, String departmentCode) throws IllegalStateException, IllegalArgumentException {

    }

    @Override
    public List<String> viewAllClients() {
        return null;
    }

    @Override
    public List<Client> getAllClients() throws IllegalStateException {
        return null;
    }

    @Override
    public int addAccount(Integer accountID, int clientID, String accountName, int initialIncomings, int initialOutgoings) throws IllegalStateException, IllegalArgumentException {
        return 0;
    }

    @Override
    public List<String> getAccounts() {
        return null;
    }

    @Override
    public List<Integer> getAccounts(int clientID) throws IllegalStateException, IllegalArgumentException {
        return null;
    }

    @Override
    public String getAccountName(int id) throws IllegalStateException, IllegalArgumentException {
        return null;
    }

    @Override
    public int getAccountBalance(int id) throws IllegalStateException, IllegalArgumentException {
        return 0;
    }

    @Override
    public int getAccountIncomings(int id) throws IllegalStateException, IllegalArgumentException {
        return 0;
    }

    @Override
    public int getAccountOutgoings(int id) throws IllegalStateException, IllegalArgumentException {
        return 0;
    }

    @Override
    public void setAccountIncomings(int id, int incomings) throws IllegalStateException, IllegalArgumentException {

    }

    @Override
    public void setAccountOutgoings(int id, int outgoings) throws IllegalStateException, IllegalArgumentException {

    }

    @Override
    public void setClientProvider(ClientList provider) {

    }
}
