package au.edu.sydney.pac.erp.client;

import org.junit.Test;
import static org.junit.Assert.*;

public class ClientImplTest {

    @Test
    public void ClientImpl_InvalidID() {
        //test invalid id input
        try {
            Client client = new ClientImpl(0, "firstName", "lastName", "phoneNumber");
            fail("id cannot be 0");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            Client client = new ClientImpl(-1, "firstName", "lastName", "phoneNumber");
            fail("id cannot be negative");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            Client client = new ClientImpl(Integer.MIN_VALUE, "firstName", "lastName", "phoneNumber");
            fail("id cannot be negative");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }
    @Test
    public void ClientImpl_InvalidFirstName() {
        //test invalid firstName input
        try {
            Client client = new ClientImpl(1, "", "lastName", "phoneNumber");
            fail("first name cannot be empty");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            Client client = new ClientImpl(1, null, "lastName", "phoneNumber");
            fail("first name cannot be null");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }
    @Test
    public void ClientImpl_InvalidLastName() {
        //test invalid lastName input
        try {
            Client client = new ClientImpl(1, "firstName", "", "phoneNumber");
            fail("last name cannot be empty");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            Client client = new ClientImpl(1, "firstName", null, "phoneNumber");
            fail("last name cannot be null");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }
    @Test
    public void ClientImpl_InvalidPhoneNumber() {
        //test invalid phoneNumber input
        try {
            Client client = new ClientImpl(1, "firstName", "lastName", "");
            fail("phone number cannot be empty");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            Client client = new ClientImpl(1, "firstName", "lastName", null);
            fail("phone number cannot be null");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }
    @Test
    public void ClientImpl_Valid(){
        //verify that valid inputs create the corresponding Client
        Client client = new ClientImpl(10, "Zeke", "Van Dehy", "7202318709");
        assertEquals(10, client.getID());
        assertEquals("Zeke", client.getFirstName());
        assertEquals("Van Dehy", client.getLastName());
        assertEquals("7202318709", client.getPhoneNumber());

        //verify id works on max int
        client = new ClientImpl(Integer.MAX_VALUE, "Zeke", "Van Dehy", "7202318709");
        assertEquals(Integer.MAX_VALUE, client.getID());
    }

    @Test
    public void assignDepartment_Valid() {
        Client client = new ClientImpl(1, "first", "last", "1234567890");

        //assign department to unassigned client should work
        assertFalse(client.isAssigned());
        client.assignDepartment("001");
        assertEquals("001", client.getDepartmentCode());
        assertTrue(client.isAssigned());
    }
    @Test (expected = IllegalStateException.class)
    public void assignDepartment_AlreadyAssigned() {
        Client client = new ClientImpl(1, "first", "last", "1234567890");

        client.assignDepartment("001");
        assertTrue(client.isAssigned());
        //assign department to assigned client should fail
        client.assignDepartment("002");
        //department should not change
        assertEquals("001", client.getDepartmentCode());
        assertTrue(client.isAssigned());
    }
    @Test (expected = IllegalArgumentException.class)
    public void assignDepartment_EmptyDepartment() {
        //check empty inputs on unassigned client
        Client client = new ClientImpl(1, "first", "last", "1234567890");
        //department code can't be empty
        client.assignDepartment("");
        //shouldn't assign department
        assertNull(client.getDepartmentCode());
        assertFalse(client.isAssigned());
    }
    @Test (expected = IllegalArgumentException.class)
    public void assignDepartment_NullDepartment() {
        //check nul department code on unassigned client
        Client client = new ClientImpl(1, "first", "last", "1234567890");
        //department code can't be null
        client.assignDepartment(null);
        //shouldn't assign department
        assertNull(client.getDepartmentCode());
        assertFalse(client.isAssigned());
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
    public void getDepartmentCode_Unassigned() {
        Client client = new ClientImpl(1, "first", "last", "1234567890");

        //unassigned client should return null
        assertNull(client.getDepartmentCode());
    }
    @Test
    public void getDepartmentCode_Valid() {
        Client client = new ClientImpl(1, "first", "last", "1234567890");
        //assigned client should return the department code
        client.assignDepartment("001");
        assertEquals("001", client.getDepartmentCode());
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