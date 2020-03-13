package au.edu.sydney.pac.erp.client;

import java.util.ArrayList;
import java.util.List;

public class ClientListImpl implements ClientList {

    private List<Client> clients;

    public ClientListImpl() {
        this.clients = new ArrayList<>();
    }

    @Override
    public Client addClient(int id, String firstName, String lastName, String phoneNumber) throws IllegalStateException, IllegalArgumentException {
        //todo: Should I leave these parameter checks to the ClientImpl?
        if(id <= 0) {
            throw new IllegalArgumentException();
        } else if(findOne(id) != null) {
            throw new IllegalStateException();
        }
        if(firstName == null || firstName.equals("")) {
            throw new IllegalArgumentException();
        }
        if(lastName == null || lastName.equals("")) {
            throw new IllegalArgumentException();
        }
        if(phoneNumber == null || phoneNumber.equals("")) {
            throw new IllegalArgumentException();
        }
        Client client = new ClientImpl(id, firstName, lastName, phoneNumber);
        clients.add(client);
        return client;
    }

    @Override
    public void clear() {
        clients = new ArrayList<>();
    }

    @Override
    public List<Client> findAll() {
        return clients;
    }

    @Override
    public List<Client> findAll(boolean assigned) {
        List<Client> ret = new ArrayList<>();
        for(Client client:clients) {
            if(client.isAssigned() == assigned) {
                ret.add(client);
            }
        }
        return ret;
    }

    @Override
    public List<Client> findAll(String... departmentCodes) {
        List<Client> ret = new ArrayList<>();
        for(String code: departmentCodes) {
            for(Client client: clients) {
                if(client.getDepartmentCode() != null && client.getDepartmentCode().equals(code)) {
                    ret.add(client);
                }
            }
        }
        return ret;
    }

    @Override
    public Client findOne(int id) throws IllegalArgumentException {
        if(id <= 0) {
            throw new IllegalArgumentException();
        }
        for(Client client: clients) {
            if(client.getID() == id) {
                return client;
            }
        }
        return null;
    }

    @Override
    public boolean remove(int id) throws IllegalArgumentException {
        if(id <= 0) {
            throw new IllegalArgumentException();
        }
        for(Client client: clients) {
            if(client.getID() == id) {
                clients.remove(client);
                return true;
            }
        }
        return false;
    }
}
