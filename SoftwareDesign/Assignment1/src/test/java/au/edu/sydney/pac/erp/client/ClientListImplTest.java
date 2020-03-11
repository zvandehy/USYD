package au.edu.sydney.pac.erp.client;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ClientListImplTest {

    @Test
    public void testAddClient_InvalidID() {
        ClientListImpl clientList = new ClientListImpl();
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
    }
    @Test (expected = IllegalStateException.class)
    public void testAddClient_DuplicateID() {
        ClientListImpl clientList = new ClientListImpl();

        Client client = clientList.addClient(1, "firstName", "lastName", "phoneNumber");
        Client client2 = clientList.addClient(1, "firstName", "lastName", "phoneNumber");
    }
    @Test
    public void testAddClient_InvalidFirstName() {
        ClientListImpl clientList = new ClientListImpl();
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
    }
    @Test
    public void testAddClient_InvalidLastName() {
        ClientListImpl clientList = new ClientListImpl();
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
    }
    @Test
    public void testAddClient_InvalidPhoneNumber() {
        ClientListImpl clientList = new ClientListImpl();
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
    }
    @Test
    public void testAddClient_Valid(){
        ClientListImpl clientList = new ClientListImpl();
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
    public void clear() {
    }

    @Test
    public void findAll() {
    }

    @Test
    public void findAll1() {
    }

    @Test
    public void findAll2() {
    }

    @Test
    public void findOne() {
    }

    @Test
    public void remove() {
    }
}