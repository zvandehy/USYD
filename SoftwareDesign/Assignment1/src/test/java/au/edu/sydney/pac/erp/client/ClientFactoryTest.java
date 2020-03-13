package au.edu.sydney.pac.erp.client;

import org.junit.Test;

import static org.junit.Assert.*;

//really a ClientListFactory
public class ClientFactoryTest {

    @Test
    public void testClientFactory() {
        try {
            ClientFactory factory = new ClientFactory();
        } catch(Exception e) {
            fail("Should not throw exception");
        }

    }

    @Test
    public void testMakeClientList() {
        assertNotNull(new ClientFactory().makeClientList());
        ClientFactory factory = new ClientFactory();
        assertTrue(new ClientFactory().makeClientList() instanceof ClientList);
    }


}