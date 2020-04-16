package au.edu.sydney.pac.erp.client;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ClientListImplTest {

    @Test
    public void testAddClient_InvalidID() {
        ClientList clientList = new ClientFactory().makeClientList();
        //test invalid id input
        try {
            Client client = clientList.addClient(0, "firstName", "lastName", "phoneNumber");
            fail("id cannot be 0");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            Client client = clientList.addClient(-1, "firstName", "lastName", "phoneNumber");
            fail("id cannot be negative");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        //verify client was not added
        assertTrue(clientList.findAll().isEmpty());
    }
    @Test
    public void testAddClient_DuplicateID() {
        ClientList clientList = new ClientFactory().makeClientList();

        Client client = clientList.addClient(1, "firstName", "lastName", "phoneNumber");
        try {
            Client client2 = clientList.addClient(1, "firstName", "lastName", "phoneNumber");
            fail("Adding duplicate ID should throw IllegalStateException");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
        //verify client was not added
        assertEquals(1, clientList.findAll().size());
    }
    @Test
    public void testAddClient_InvalidFirstName() {
        ClientList clientList = new ClientFactory().makeClientList();
        //test invalid firstName input
        try {
            Client client = clientList.addClient(1, "", "lastName", "phoneNumber");
            fail("first name cannot be empty");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            Client client = clientList.addClient(1, null, "lastName", "phoneNumber");
            fail("first name cannot be null");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        //verify client was not added
        assertTrue(clientList.findAll().isEmpty());
    }
    @Test
    public void testAddClient_InvalidLastName() {
        ClientList clientList = new ClientFactory().makeClientList();
        //test invalid lastName input
        try {
            Client client = clientList.addClient(1, "firstName", "", "phoneNumber");
            fail("last name cannot be empty");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            Client client = clientList.addClient(1, "firstName", null, "phoneNumber");
            fail("last name cannot be null");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        //verify client was not added
        assertTrue(clientList.findAll().isEmpty());
    }
    @Test
    public void testAddClient_InvalidPhoneNumber() {
        ClientList clientList = new ClientFactory().makeClientList();
        //test invalid phoneNumber input
        try {
            Client client = clientList.addClient(1, "firstName", "lastName", "");
            fail("phone number cannot be empty");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            Client client = clientList.addClient(1, "firstName", "lastName", null);
            fail("phone number cannot be null");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        //verify client was not added
        assertTrue(clientList.findAll().isEmpty());
    }
    @Test
    public void testAddClient_Valid(){
        ClientList clientList = new ClientFactory().makeClientList();
        Client client = clientList.addClient(10, "first", "last", "1234567890");
        //verify that the client was created
        assertEquals(10, client.getID());
        assertEquals("first", client.getFirstName());
        assertEquals("last", client.getLastName());
        assertEquals("1234567890", client.getPhoneNumber());
        //verify that the client was added
        assertEquals(1, clientList.findAll().size());
        assertTrue(clientList.findAll().contains(client));
    }

    @Test
    public void clear_EmptyBefore() {
        ClientList clientList = new ClientFactory().makeClientList();
        clientList.clear();
        assertTrue(clientList.findAll().isEmpty());
    }@Test
    public void clear_NotEmptyBefore() {
        ClientList clientList = new ClientFactory().makeClientList();
        for(int i=1;i<=10;i++) {
            clientList.addClient(i,"first"+i, "last"+i, "phone"+i);
        }
        assertEquals(10, clientList.findAll().size());
        clientList.clear();
        assertTrue(clientList.findAll().isEmpty());
    }

    @Test
    public void findAll_NoParameter_Valid() {
        ClientList clientList = new ClientFactory().makeClientList();
        for(int i=1;i<=10;i++) {
            clientList.addClient(i,"first"+i, "last"+i, "phone"+i);
        }
        assertEquals(10, clientList.findAll().size());
        for(int i=1;i<=10;i++) {
            assertTrue(clientList.findAll().contains(clientList.findOne(i)));
        }
    }
    @Test
    public void findAll_NoParameter_Empty() {
        ClientList clientList = new ClientFactory().makeClientList();
        assertTrue(clientList.findAll().isEmpty());
    }

    @Test
    public void findAll_Assigned_Valid() {
        ClientList clientList = new ClientFactory().makeClientList();
        Client client;
        for(int i=1;i<=10;i++) {
            client = clientList.addClient(i,"first"+i, "last"+i, "phone"+i);
            if(i%2 == 0) {
                client.assignDepartment("department"+i);
            }
        }
        assertEquals(5, clientList.findAll(true).size());
        assertEquals(5, clientList.findAll(false).size());
        for(int i=1;i<=10;i++) {
            if(i%2 == 0) {
                assertTrue(clientList.findAll(true).contains(clientList.findOne(i)));
            } else {
                assertTrue(clientList.findAll(false).contains(clientList.findOne(i)));
            }

        }
    }
    @Test
    public void findAll_Assigned_Empty() {
        ClientList clientList = new ClientFactory().makeClientList();
        clientList.addClient(1,"first", "last", "1234567890");
        //can't find client in 'true' assigned search
        assertFalse(clientList.findAll(true).contains(clientList.findOne(1)));
        assertTrue(clientList.findAll(true).isEmpty());

        //1 client found in false assigned search
        assertTrue(clientList.findAll(false).contains(clientList.findOne(1)));
        assertEquals(1,clientList.findAll(false).size());
    }

    @Test
    public void findAll_Department_OnlySearchForOneDepartment() {
        ClientList clientList = new ClientFactory().makeClientList();
        Client client;
        client = clientList.addClient(1,"first", "last", "1234567890");
        client.assignDepartment("1");
        client = clientList.addClient(2,"first", "last", "1234567890");
        client.assignDepartment("1");
        client = clientList.addClient(3,"first", "last", "1234567890");
        client.assignDepartment("1");
        client = clientList.addClient(4,"first", "last", "1234567890");
        client.assignDepartment("2");
        client = clientList.addClient(5,"first", "last", "1234567890");

        assertEquals(3, clientList.findAll("1").size());
        assertTrue(clientList.findAll("1").contains(clientList.findOne(1)));
        assertTrue(clientList.findAll("1").contains(clientList.findOne(2)));
        assertTrue(clientList.findAll("1").contains(clientList.findOne(3)));
        assertEquals(1, clientList.findAll("2").size());
        assertTrue(clientList.findAll("2").contains(clientList.findOne(4)));
    }
    @Test
    public void findAll_Department_SearchMultipleDepartments() {
        ClientList clientList = new ClientFactory().makeClientList();
        Client client;
        //Note that clients in the same department are not adjacent
        client = clientList.addClient(1,"first", "last", "1234567890");
        client.assignDepartment("1");
        client = clientList.addClient(2,"first", "last", "1234567890");
        client.assignDepartment("2");
        client = clientList.addClient(3,"first", "last", "1234567890");
        client.assignDepartment("1");
        client = clientList.addClient(4,"first", "last", "1234567890");
        client.assignDepartment("2");
        client = clientList.addClient(5,"first", "last", "1234567890");
        client.assignDepartment("3");

        //search 2 departments
        List<Client> clients = clientList.findAll("1","3");
        assertEquals(3, clients.size());
        assertTrue(clients.contains(clientList.findOne(1)));
        assertTrue(clients.contains(clientList.findOne(3)));
        assertTrue(clients.contains(clientList.findOne(5)));
        //search all departments
        clients = clientList.findAll("1", "2", "3");
        assertEquals(5, clients.size());
        assertTrue(clients.contains(clientList.findOne(1)));
        assertTrue(clients.contains(clientList.findOne(2)));
        assertTrue(clients.contains(clientList.findOne(3)));
        assertTrue(clients.contains(clientList.findOne(4)));
        assertTrue(clients.contains(clientList.findOne(5)));
    }
    @Test
    public void findAll_Department_Empty() {
        ClientList clientList = new ClientFactory().makeClientList();
        clientList.addClient(1,"first", "last", "1234567890");
        clientList.addClient(2,"first", "last", "1234567890");
        Client client = clientList.addClient(3,"first", "last", "1234567890");
        client.assignDepartment("4");
        assertTrue(clientList.findAll("1","2","3").isEmpty());
    }


    @Test
    public void findOne_Valid() {
        ClientList clientList = new ClientFactory().makeClientList();
        Client client;
        for(int i=1;i<=10;i++) {
            client = clientList.addClient(i,"first"+i, "last"+i, "phone"+i);
            //findOne the most recently added client
            assertEquals(client, clientList.findOne(i));
        }
        //findOne first added client
        assertNotNull(clientList.findOne(1));

        //findOne middle added client
        assertNotNull(clientList.findOne(5));
    }
    @Test
    public void findOne_NoMatchFound() {
        ClientList clientList = new ClientFactory().makeClientList();
        clientList.addClient(1,"first","last","1234567890");
        assertNull(clientList.findOne(2));
    }
    @Test
    public void findOne_ZeroID() {
        ClientList clientList = new ClientFactory().makeClientList();
        clientList.addClient(1,"first","last","1234567890");
        try {
            clientList.findOne(0);
            fail("ID 0 should throw IllegalArgumentException");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void findOne_NegativeID() {
        ClientList clientList = new ClientFactory().makeClientList();
        clientList.addClient(1,"first","last","1234567890");
        try {
            assertNull(clientList.findOne(-1));
            fail("Negative ID throw IllegalArgumentException");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void remove_Valid() {
        ClientList clientList = new ClientFactory().makeClientList();
        for(int i=1;i<=10;i++) {
            clientList.addClient(i,"first"+i,"last"+i,"phone"+i);
        }
        assertEquals(10,clientList.findAll().size());
        //remove last added
        assertTrue(clientList.remove(10));
        assertEquals(9,clientList.findAll().size());
        assertNull(clientList.findOne(10));
        assertFalse(clientList.remove(10));
        //remove middle added
        assertTrue(clientList.remove(5));
        assertEquals(8,clientList.findAll().size());
        assertNull(clientList.findOne(5));
        assertFalse(clientList.remove(5));
        //remove first added
        assertTrue(clientList.remove(1));
        assertEquals(7,clientList.findAll().size());
        assertNull(clientList.findOne(1));
        assertFalse(clientList.remove(1));


    }
    @Test
    public void remove_NoMatchFound() {
        ClientList clientList = new ClientFactory().makeClientList();
        clientList.addClient(1,"first","last","1234567890");
        assertFalse(clientList.remove(2));
    }
    @Test
    public void remove_ZeroID() {
        ClientList clientList = new ClientFactory().makeClientList();
        clientList.addClient(1,"first","last","1234567890");
        try {
            clientList.remove(0);
            fail("ID 0 should throw IllegalArgumentException");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void remove_NegativeID() {
        ClientList clientList = new ClientFactory().makeClientList();
        clientList.addClient(1,"first","last","1234567890");
        try {
            clientList.remove(-1);
            fail("Negative ID should throw IllegalArgumentException");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }
}