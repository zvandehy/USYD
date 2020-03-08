package au.edu.sydney.pac.erp.client;

import java.util.List;

public class ClientListImpl implements ClientList{

    @Override
    public Client addClient(int id, String firstName, String lastName, String phoneNumber) throws IllegalStateException, IllegalArgumentException {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public List<Client> findAll() {
        return null;
    }

    @Override
    public List<Client> findAll(boolean assigned) {
        return null;
    }

    @Override
    public List<Client> findAll(String... departmentCodes) {
        return null;
    }

    @Override
    public Client findOne(int id) throws IllegalArgumentException {
        return null;
    }

    @Override
    public boolean remove(int id) throws IllegalArgumentException {
        return false;
    }
}
