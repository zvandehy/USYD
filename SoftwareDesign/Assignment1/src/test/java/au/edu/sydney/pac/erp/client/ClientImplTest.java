package au.edu.sydney.pac.erp.client;

import org.junit.Test;
import static org.junit.Assert.*;

public class ClientImplTest {

    @Test
    public void ClientImpl(){
        //todo: Find out if the Exceptions testing shown in the slides is better practice than instanceof
        Client client;
        //test invalid id input
        try {
            client = new ClientImpl(0, "firstName", "lastName", "phoneNumber");
            fail("id cannot be 0");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            client = new ClientImpl(-1, "firstName", "lastName", "phoneNumber");
            fail("id cannot be negative");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            client = new ClientImpl(Integer.MIN_VALUE, "firstName", "lastName", "phoneNumber");
            fail("id cannot be negative");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        //test invalid firstName input
        try {
            client = new ClientImpl(1, "", "lastName", "phoneNumber");
            fail("first name cannot be empty");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            client = new ClientImpl(1, null, "lastName", "phoneNumber");
            fail("first name cannot be null");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        //test invalid lastName input
        try {
            client = new ClientImpl(1, "firstName", "", "phoneNumber");
            fail("last name cannot be empty");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            client = new ClientImpl(1, "firstName", null, "phoneNumber");
            fail("last name cannot be null");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        //test invalid phoneNumber input
        try {
            client = new ClientImpl(1, "firstName", "lastName", "");
            fail("phone number cannot be empty");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            client = new ClientImpl(1, "firstName", "lastName", null);
            fail("phone number cannot be null");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        //verify that valid inputs create the corresponding Client
        client = new ClientImpl(10, "Zeke", "Van Dehy", "7202318709");
        assertEquals(10, client.getID());
        assertEquals("Zeke", client.getFirstName());
        assertEquals("Van Dehy", client.getLastName());
        assertEquals("7202318709", client.getPhoneNumber());

        //verify id works on max int just for fun
        client = new ClientImpl(Integer.MAX_VALUE, "Zeke", "Van Dehy", "7202318709");
        assertEquals(Integer.MAX_VALUE, client.getID());

    }

    @Test
    public void assignDepartment() {
        Client client =  new ClientImpl(1, "first", "last", "1234567890");

        //assign department to unassigned client should work
        assertFalse(client.isAssigned());
        client.assignDepartment("001");
        assertEquals("001", client.getDepartmentCode());
        assertTrue(client.isAssigned());

        //assign department to assigned client should fail
        try {
            client.assignDepartment("002");
            fail("Cannot assign department to client who is already assigned");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
        //department should not change
        assertEquals("001", client.getDepartmentCode());
        assertTrue(client.isAssigned());

        //check null and empty inputs on unassigned client
        client = new ClientImpl(2,"first2", "last2", "0123456789");
        //department code can't be empty
        try {
            client.assignDepartment("");
            fail("department code cannot be empty");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        assertNull(client.getDepartmentCode());
        assertFalse(client.isAssigned());

        //department code can't be null
        try {
            client.assignDepartment(null);
            fail("department code cannot be null");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        assertNull(client.getDepartmentCode());
        assertFalse(client.isAssigned());

        //todo: should I check null and empty inputs on unassigned client?
    }

    @Test
    public void isAssigned() {
        Client client =  new ClientImpl(1, "first", "last", "1234567890");

        //unassigned client should return false
        assertFalse(client.isAssigned());

        //assigned client should return true
        client.assignDepartment("001");
        assertTrue(client.isAssigned());
    }

    @Test
    public void getDepartmentCode() {
        Client client = new ClientImpl(1, "first", "last", "1234567890");

        //unassigned client should return null
        assertNull(client.getDepartmentCode());

        //assigned client should return the department code
        client.assignDepartment("001");
        assertEquals("001", client.getDepartmentCode());

        //reassigning a client should not work (return same department code)
        try {
            client.assignDepartment("002");
            fail("Cannot assign department to client who is already assigned");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
        //department should not change
        assertEquals("001", client.getDepartmentCode());
        assertTrue(client.isAssigned());
    }

    @Test
    public void getID() {
        Client client;
        for (int i=1; i < 100; i++) {
            client = new ClientImpl(i,"first" + i, "last" + i, "phone" + i);
            int id = client.getID();
            if(id <= 0) {
                fail("id should not be negative or zero");
            }
            assertEquals(i,id);
        }
    }

    @Test
    public void getFirstName() {
        Client client;
        for (int i=1; i < 100; i++) {
            client = new ClientImpl(i,"first" + i, "last" + i, "phone" + i);
            String fname = client.getFirstName();
            assertNotEquals("", fname);
            assertNotNull(fname);
            assertEquals("first" + i, fname);
        }
    }

    @Test
    public void getLastName() {
        Client client;
        for (int i=1; i < 100; i++) {
            client = new ClientImpl(i,"first" + i, "last" + i, "phone" + i);
            String lname = client.getLastName();
            assertNotEquals("", lname);
            assertNotNull(lname);
            assertEquals("last" + i, lname);
        }
    }

    @Test
    public void getPhoneNumber() {
        Client client;
        for (int i=1; i < 100; i++) {
            client = new ClientImpl(i,"first" + i, "last" + i, "phone" + i);
            String phone = client.getPhoneNumber();
            assertNotEquals("", phone);
            assertNotNull(phone);
            assertEquals("phone" + i, phone);
        }
    }
}