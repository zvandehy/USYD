package au.edu.sydney.pac.erp.feaa;

import au.edu.sydney.pac.erp.client.Client;
import au.edu.sydney.pac.erp.client.ClientFactory;
import au.edu.sydney.pac.erp.client.ClientListImpl;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FEAAFacadeImplTest {
    //TODO: Test with .jar

    //create FEAAFacadeImpl with clientProvider and 5 valid clients
    private FEAAFacadeImpl setup() {
        FEAAFacadeImpl feaa = new FEAAFacadeImpl();
        feaa.setClientProvider(new ClientListImpl());
        feaa.addClient("Zeke", "Van Dehy", "+1(720)231 8709");
        feaa.addClient("Mary Margaret", "Greene", "0484 604 675");
        feaa.addClient("Client", "Three", "1234567890");
        feaa.addClient("Fourth", "Four", "444 444 4444");
        feaa.addClient("Fifth", "Five", "555 555 5555");
        return feaa;
    }
    //create valid accounts for feaa from setup()
    private FEAAFacadeImpl setup2() {
        FEAAFacadeImpl feaa = setup();
        feaa.addAccount(1, feaa.getAllClients().get(0).getID(),"Account 1", 100,50);
        feaa.addAccount(2, feaa.getAllClients().get(1).getID(), "Account 2", 100,50);
        feaa.addAccount(3, feaa.getAllClients().get(2).getID(),"Account 3", 100,50);
        feaa.addAccount(4, feaa.getAllClients().get(3).getID(), "Account 4", 100,50);
        feaa.addAccount(5, feaa.getAllClients().get(4).getID(),"Account 5", 100,50);

        feaa.addAccount(6, feaa.getAllClients().get(0).getID(),"Account 1b", 100,50);
        feaa.addAccount(7, feaa.getAllClients().get(0).getID(), "Account 1c", 100,50);
        return feaa;
    }


    @Test
    public void testAddClient_IllegalFirstName() {
        FEAAFacadeImpl feaa = new FEAAFacadeImpl();
        feaa.setClientProvider(new ClientListImpl());
        try {
            feaa.addClient("","last","720318709");
            fail("Should throw exception when first name is empty");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            feaa.addClient(null,"last","720318709");
            fail("Should throw exception when first name is null");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

    }
    @Test
    public void testAddClient_IllegalLastName() {
        FEAAFacadeImpl feaa = new FEAAFacadeImpl();
        feaa.setClientProvider(new ClientListImpl());
        try {
            feaa.addClient("first","","720318709");
            fail("Should throw exception when last name is empty");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            feaa.addClient("first",null,"720318709");
            fail("Should throw exception when last name is null");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

    }
    @Test
    public void testAddClient_IllegalPhoneNumber() {
        FEAAFacadeImpl feaa = new FEAAFacadeImpl();
        feaa.setClientProvider(new ClientListImpl());
        try {
            feaa.addClient("first","last","");
            fail("Should throw exception when phone number is empty");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            feaa.addClient("first","last",null);
            fail("Should throw exception when phone number is null");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            feaa.addClient("first","last","abc");
            fail("Should throw exception when phone number contains letters");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            feaa.addClient("first","last","720-231-8709");
            fail("Should throw exception when phone number contains dashes");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }


    }
    @Test
    public void testAddClient_NoClientProvider() {
        FEAAFacadeImpl feaa = new FEAAFacadeImpl();
        try {
            feaa.addClient("first","last","7202318709");
            fail("Should throw exception when no clientProvider is set");
        } catch(Exception e) {
            assert(e instanceof IllegalStateException);
        }
    }

    @Test
    public void testAddClient_Valid() {
        FEAAFacadeImpl feaa = new FEAAFacadeImpl();
        feaa.setClientProvider(new ClientListImpl());
        Client client = feaa.addClient("Zeke","Van Dehy","+1(720)231 8709");
        assertEquals(1, feaa.getAllClients().size());
        assertTrue(feaa.getAllClients().contains(client));
        client = feaa.addClient("client","two","0484 604 675");
        assertEquals(2, feaa.getAllClients().size());
        assertTrue(feaa.getAllClients().contains(client));
    }

    @Test
    public void testAssignClient_IllegalID() {
        FEAAFacadeImpl feaa = setup();
        try {
            feaa.assignClient(0, "DOMESTIC");
            fail("Should throw exception when client ID is zero");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            feaa.assignClient(-1, "DOMESTIC");
            fail("Should throw exception when client ID is negative");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

    }
    @Test
    public void testAssignClient_NoMatchingID() {
        FEAAFacadeImpl feaa = setup();
        //can't assume how ID is set, so need to get list of IDs
        ArrayList<Integer> clientIDs = new ArrayList<>();
        for(int i=0;i<5;i++) {
            clientIDs.add(feaa.getAllClients().get(i).getID());
        }
        //find ID that is not in feaa
        int uniqueID = 1;
        while(clientIDs.contains(uniqueID)) {
            uniqueID += 1;
        }
        try {
            feaa.assignClient(uniqueID, "DOMESTIC");
            fail("Should throw exception if given ID isn't in clientList");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testAssignClient_DuplicateIDAssigned() {
        FEAAFacadeImpl feaa = setup();
        //can't assume how ID is set, so need to get a clientID that is in the system
        int firstClientID = feaa.getAllClients().get(0).getID();
        feaa.assignClient(firstClientID,"DOMESTIC");
        try {
            feaa.assignClient(firstClientID, "INTERNATIONAL");
            fail("Should throw exception if given ID is already assigned to a department");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }

    @Test
    public void testAssignClient_IllegalDepartment() {
        FEAAFacadeImpl feaa = setup();
        //can't assume how ID is set, so need to get a clientID that is in the system
        int clientID = feaa.getAllClients().get(0).getID();
        try {
            feaa.assignClient(clientID, "ERROR");
            fail("Should throw exception if department code is invalid");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }
    @Test
    public void testAssignClient_Valid_LongVersion() {
        FEAAFacadeImpl feaa = setup();
        //can't assume how ID is set, so need to get a clientID that is in the system
        int firstClientID = feaa.getAllClients().get(0).getID();
        int secondClientID = feaa.getAllClients().get(1).getID();
        int thirdClientID = feaa.getAllClients().get(2).getID();
        feaa.assignClient(firstClientID,"DOMESTIC");
        feaa.assignClient(secondClientID,"INTERNATIONAL");
        feaa.assignClient(thirdClientID,"LARGE ACCOUNTS");
        //verify client is assigned
        assertTrue(feaa.getAllClients().get(0).isAssigned());
        assertTrue(feaa.getAllClients().get(1).isAssigned());
        assertTrue(feaa.getAllClients().get(2).isAssigned());
        //verify department code is correct
        assertEquals("DOMESTIC", feaa.getAllClients().get(0).getDepartmentCode());
        assertEquals("INTERNATIONAL", feaa.getAllClients().get(1).getDepartmentCode());
        assertEquals("LARGE ACCOUNTS", feaa.getAllClients().get(2).getDepartmentCode());
    }
    @Test
    public void testAssignClient_Valid_ShortVersion() {
        FEAAFacadeImpl feaa = setup();
        //can't assume how ID is set, so need to get a clientID that is in the system
        int firstClientID = feaa.getAllClients().get(0).getID();
        int secondClientID = feaa.getAllClients().get(1).getID();
        int thirdClientID = feaa.getAllClients().get(2).getID();
        feaa.assignClient(firstClientID,"DOM");
        feaa.assignClient(secondClientID,"INT");
        feaa.assignClient(thirdClientID,"LRG");
        //verify client is assigned
        assertTrue(feaa.getAllClients().get(0).isAssigned());
        assertTrue(feaa.getAllClients().get(1).isAssigned());
        assertTrue(feaa.getAllClients().get(2).isAssigned());
        //verify department code is correct
        assertEquals("DOM", feaa.getAllClients().get(0).getDepartmentCode());
        assertEquals("INT", feaa.getAllClients().get(1).getDepartmentCode());
        assertEquals("LRG", feaa.getAllClients().get(2).getDepartmentCode());
    }


    @Test
    public void testViewAllClients() {
        String client1 = "Van Dehy, Zeke";
        String client2 = "Greene, Mary Margaret";
        String client3 = "Three, Client";
        String client4 = "Four, Fourth";
        String client5 = "Five, Fifth";

        FEAAFacadeImpl feaa = setup();
        ArrayList<String> clients = (ArrayList<String>) feaa.viewAllClients();
        assertEquals(5, clients.size());
        assertTrue("Contains: " + client1, clients.contains(client1));
        assertTrue("Contains: " + client2, clients.contains(client2));
        assertTrue("Contains: " + client3, clients.contains(client3));
        assertTrue("Contains: " + client4, clients.contains(client4));
        assertTrue("Contains: " + client5, clients.contains(client5));
    }
    @Test
    public void testViewAllClients_Empty() {
        FEAAFacadeImpl feaa = new FEAAFacadeImpl();
        feaa.setClientProvider(new ClientListImpl());
        assertTrue(feaa.viewAllClients().isEmpty());
    }

    @Test
    public void testGetAllClients_NoClientProvider() {
        FEAAFacadeImpl feaa = new FEAAFacadeImpl();
        try {
            feaa.getAllClients();
            fail("Should throw exception when no clientProvider is set");
        } catch(Exception e) {
            assert(e instanceof IllegalStateException);
        }
    }

    @Test
    public void testGetAllClients_Valid() {
        //since we cannot assume the ID of the created clients,
        //I test if the size of the returned list is the same as
        //the number of added clients (5) to verify that the number of returned clients
        //is the same amount that have been added to the system

        //then I verify that all the first names of the added clients
        //are found in the returned list

        String client1 = "Zeke";
        String client2 = "Mary Margaret";
        String client3 = "Client";
        String client4 = "Fourth";
        String client5 = "Fifth";


        FEAAFacadeImpl feaa = setup();
        ArrayList<Client> returnedClients = (ArrayList<Client>) feaa.getAllClients();
        ArrayList<String> returnedClientFirstNames = new ArrayList<>();
        for(Client client: returnedClients) {
            returnedClientFirstNames.add(client.getFirstName());
        }

        assertEquals(5, returnedClients.size());
        assertTrue("Contains: " + client1, returnedClientFirstNames.contains(client1));
        assertTrue("Contains: " + client2, returnedClientFirstNames.contains(client2));
        assertTrue("Contains: " + client3, returnedClientFirstNames.contains(client3));
        assertTrue("Contains: " + client4, returnedClientFirstNames.contains(client4));
        assertTrue("Contains: " + client5, returnedClientFirstNames.contains(client5));
    }
    @Test
    public void testGetAllClients_Empty() {
        FEAAFacadeImpl feaa = new FEAAFacadeImpl();
        feaa.setClientProvider(new ClientListImpl());
        assertTrue(feaa.getAllClients().isEmpty());
    }

    @Test
    public void testAddAccount_IllegalClientID() {
        FEAAFacadeImpl feaa = setup();
        try {
            feaa.addAccount(1, 0, "accountName", 0,0);
            fail("Should throw exception when client ID is zero");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            feaa.addAccount(1, -1, "accountName", 0,0);
            fail("Should throw exception when client ID is negative");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

    }
    @Test
    public void testAddAccount_IllegalClientID_NullAfterProvided() {
        FEAAFacadeImpl feaa = setup();
        //valid addAccount
        feaa.addAccount(1,feaa.getAllClients().get(0).getID(),"Account 1", 100,50);
        //can't be null after provided
        try {
            feaa.addAccount(null, feaa.getAllClients().get(1).getID(), "accountName", 0,0);
            fail("Should throw exception when client ID is null after not-null id was provided");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }
    @Test
    public void testAddAccount_IllegalClientID_ClientNotInProvider() {
        FEAAFacadeImpl feaa = setup();
        //can't assume how ID is set, so need to get list of IDs
        ArrayList<Integer> clientIDs = new ArrayList<>();
        for(int i=0;i<5;i++) {
            clientIDs.add(feaa.getAllClients().get(i).getID());
        }
        //find ID that is not in feaa
        int uniqueID = 1;
        while(clientIDs.contains(uniqueID)) {
            uniqueID += 1;
        }

        try {
            feaa.addAccount(null, uniqueID, "accountName", 0,0);
            fail("Should throw exception when client ID is not in client provider");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testAddAccount_NoClientProvider() {
        FEAAFacadeImpl feaa = new FEAAFacadeImpl();
        try {
            feaa.addAccount(null, 0, "accountName", 0,0);
            fail("Should throw exception when clientProvider has not been set");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testAddAccount_Valid_OnlyNull() {
        FEAAFacadeImpl feaa = setup();
        //valid addAccount
        feaa.addAccount(null, feaa.getAllClients().get(0).getID(),"Account 1", 100,50);
        feaa.addAccount(null, feaa.getAllClients().get(1).getID(), "Account 2", 100,50);
        feaa.addAccount(null, feaa.getAllClients().get(2).getID(),"Account 3", 100,50);
        feaa.addAccount(null, feaa.getAllClients().get(3).getID(), "Account 4", 100,50);
        feaa.addAccount(null, feaa.getAllClients().get(4).getID(),"Account 5", 100,50);

        //verify 5 accounts were added
        assertEquals(5,feaa.getAccounts().size());
        //verify 1 account was added for each client
        assertEquals(1,feaa.getAccounts(feaa.getAllClients().get(0).getID()).size());
        assertEquals(1,feaa.getAccounts(feaa.getAllClients().get(1).getID()).size());
        assertEquals(1,feaa.getAccounts(feaa.getAllClients().get(2).getID()).size());
        assertEquals(1,feaa.getAccounts(feaa.getAllClients().get(3).getID()).size());
        assertEquals(1,feaa.getAccounts(feaa.getAllClients().get(4).getID()).size());
        //add more than one account for one client
        feaa.addAccount(null, feaa.getAllClients().get(0).getID(),"Account 1b", 100,50);
        feaa.addAccount(null, feaa.getAllClients().get(0).getID(), "Account 1c", 100,50);

        //verify there are now 7 accounts
        assertEquals(7,feaa.getAccounts().size());
        //verify there are 3 accounts for client 0
        assertEquals(3,feaa.getAccounts(feaa.getAllClients().get(0).getID()).size());
    }
    @Test
    public void testAddAccount_Valid_OnlyProvided() {
        FEAAFacadeImpl feaa = setup();
        //valid addAccount
        feaa.addAccount(1, feaa.getAllClients().get(0).getID(),"Account 1", 100,50);
        feaa.addAccount(2, feaa.getAllClients().get(1).getID(), "Account 2", 100,50);
        feaa.addAccount(3, feaa.getAllClients().get(2).getID(),"Account 3", 100,50);
        feaa.addAccount(4, feaa.getAllClients().get(3).getID(), "Account 4", 100,50);
        feaa.addAccount(5, feaa.getAllClients().get(4).getID(),"Account 5", 100,50);

        //verify 5 accounts were added
        assertEquals(5,feaa.getAccounts().size());
        //verify the correct account was created
        String account1 = "1:Account 1";
        String account2 = "2:Account 2";
        String account3 = "3:Account 3";
        String account4 = "4:Account 4";
        String account5 = "5:Account 5";

        assertTrue(feaa.getAccounts().contains(account1));
        assertTrue(feaa.getAccounts().contains(account2));
        assertTrue(feaa.getAccounts().contains(account3));
        assertTrue(feaa.getAccounts().contains(account4));
        assertTrue(feaa.getAccounts().contains(account5));

        //add more than one account for one client
        feaa.addAccount(6, feaa.getAllClients().get(0).getID(),"Account 1b", 100,50);
        feaa.addAccount(7, feaa.getAllClients().get(0).getID(), "Account 1c", 100,50);

        //verify there are now 7 accounts
        assertEquals(7,feaa.getAccounts().size());

        //verify there correct account IDs for client 0
        assertTrue(feaa.getAccounts(feaa.getAllClients().get(0).getID()).contains(1));
        assertTrue(feaa.getAccounts(feaa.getAllClients().get(0).getID()).contains(6));
        assertTrue(feaa.getAccounts(feaa.getAllClients().get(0).getID()).contains(7));

    }



    @Test
    public void testAddAccount_IllegalAccountName() {
            FEAAFacadeImpl feaa = setup();
            try {
                feaa.addAccount(1, feaa.getAllClients().get(0).getID(), "", 0,0);
                fail("Should throw exception when account name is empty");
            } catch(Exception e) {
                assertTrue(e instanceof IllegalArgumentException);
            }
            try {
                feaa.addAccount(1, feaa.getAllClients().get(0).getID(), null, 0,0);
                fail("Should throw exception when account name is null");
            } catch(Exception e) {
                assertTrue(e instanceof IllegalArgumentException);
            }
    }
    @Test
    public void testAddAccount_IllegalInitialIncoming() {
            FEAAFacadeImpl feaa = setup();
            try {
                feaa.addAccount(1, feaa.getAllClients().get(0).getID(), "accountName", -1,0);
                fail("Should throw exception when initial incomings is negative");
            } catch(Exception e) {
                assertTrue(e instanceof IllegalArgumentException);
            }
    }
    @Test
    public void testAddAccount_IllegalInitialOutgoing() {
        FEAAFacadeImpl feaa = setup();
        try {
            feaa.addAccount(1, feaa.getAllClients().get(0).getID(), "accountName", 0,-1);
            fail("Should throw exception when initial outgoings is negative");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }
    @Test
    public void testGetAccounts_NoParameter_Empty() {
        FEAAFacadeImpl feaa = setup();
        assertTrue(feaa.getAccounts().isEmpty());
    }
    @Test
    public void testGetAccounts_NoParameter_Valid() {
        FEAAFacadeImpl feaa = setup2();
        String account1 = "1:Account 1";
        String account2 = "2:Account 2";
        String account3 = "3:Account 3";
        String account4 = "4:Account 4";
        String account5 = "5:Account 5";
        String account6 = "6:Account 1b";
        String account7 = "7:Account 1c";

        assertEquals(7,feaa.getAccounts().size());
        assertTrue(feaa.getAccounts().contains(account1));
        assertTrue(feaa.getAccounts().contains(account2));
        assertTrue(feaa.getAccounts().contains(account3));
        assertTrue(feaa.getAccounts().contains(account4));
        assertTrue(feaa.getAccounts().contains(account5));
        assertTrue(feaa.getAccounts().contains(account6));
        assertTrue(feaa.getAccounts().contains(account7));
    }
    @Test
    public void testGetAccounts_ClientID_Empty() {
        FEAAFacadeImpl feaa = setup();
        assertTrue(feaa.getAccounts(feaa.getAllClients().get(0).getID()).isEmpty());
    }
    @Test
    public void testGetAccounts_ClientID_Valid() {
        //feaa without clients added
        FEAAFacadeImpl feaa = setup();

        //assign client IDs for clarity
        int clientId1 = feaa.getAllClients().get(0).getID();
        int clientId2 = feaa.getAllClients().get(1).getID();

        //give clientId1 3 accounts
        feaa.addAccount(1, clientId1,"Account 1", 100,50);
        feaa.addAccount(2, clientId1, "Account 2", 100,50);
        feaa.addAccount(3, clientId1,"Account 3", 100,50);
        //give clientId2 1 account
        feaa.addAccount(4, clientId2, "Account 4", 100,50);

        assertEquals(3,feaa.getAccounts(clientId1).size());
        assertEquals(1,feaa.getAccounts(clientId2).size());

        assertTrue(feaa.getAccounts(clientId1).contains(1));
        assertTrue(feaa.getAccounts(clientId1).contains(2));
        assertTrue(feaa.getAccounts(clientId1).contains(3));
        assertTrue(feaa.getAccounts(clientId2).contains(4));
    }
    @Test
    public void testGetAccounts_ClientID_NoProvider() {
        FEAAFacadeImpl feaa = new FEAAFacadeImpl();
        //don't setClientProvider()
        try {
            feaa.getAccounts(1);
            fail("Should throw exception if feaa has not set client provider");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }


    @Test
    public void testGetAccounts_ClientID_NoMatchingID() {
        FEAAFacadeImpl feaa = setup2();
        //can't assume how ID is set, so need to get list of IDs
        ArrayList<Integer> clientIDs = new ArrayList<>();
        for(int i=0;i<5;i++) {
            clientIDs.add(feaa.getAllClients().get(i).getID());
        }
        //find ID that is not in feaa
        int uniqueID = 1;
        while(clientIDs.contains(uniqueID)) {
            uniqueID += 1;
        }
        try {
            feaa.getAccounts(uniqueID);
            fail("Should throw exception if client Id is not in client list");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testGetAccounts_ClientID_IllegalID() {
        FEAAFacadeImpl feaa = setup2();
        try {
            feaa.getAccounts(0);
            fail("Should throw exception if ID is zero");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            feaa.getAccounts(-1);
            fail("Should throw exception if ID is negative");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void testGetAccountName_IllegalID() {
        FEAAFacadeImpl feaa = setup2();
        try {
            feaa.getAccountName(0);
            fail("Should throw exception if ID is zero");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            feaa.getAccountName(-1);
            fail("Should throw exception if ID is negative");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }
    @Test
    public void testGetAccountName_NoMatchingID() {
        FEAAFacadeImpl feaa = setup2();
        //we know the account IDs are int 1 to 7
        try {
            feaa.getAccountName(10);
            fail("Should throw exception if ID is not an account ID");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testGetAccountName_NoProvider() {
        FEAAFacadeImpl feaa = new FEAAFacadeImpl();
        //don't setClientProvider()
        try {
            feaa.getAccountName(1);
            fail("Should throw exception if feaa has not set client provider");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testGetAccountName_Valid() {
        FEAAFacadeImpl feaa = setup2();
        String account1 = "1:Account 1";
        String account2 = "2:Account 2";
        String account3 = "3:Account 3";
        String account4 = "4:Account 4";
        String account5 = "5:Account 5";
        String account6 = "6:Account 1b";
        String account7 = "7:Account 1c";

        assertEquals(account1, feaa.getAccountName(1));
        assertEquals(account2, feaa.getAccountName(2));
        assertEquals(account3, feaa.getAccountName(3));
        assertEquals(account4, feaa.getAccountName(4));
        assertEquals(account5, feaa.getAccountName(5));
        assertEquals(account6, feaa.getAccountName(6));
        assertEquals(account7, feaa.getAccountName(7));
    }

    @Test
    public void testGetAccountBalance_IllegalID() {
        FEAAFacadeImpl feaa = setup2();
        try {
            feaa.getAccountBalance(0);
            fail("Should throw exception if ID is zero");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            feaa.getAccountBalance(-1);
            fail("Should throw exception if ID is negative");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }
    @Test
    public void testGetAccountBalance_NoMatchingID() {
        FEAAFacadeImpl feaa = setup2();
        try {
            feaa.getAccountBalance(10);
            fail("Should throw exception if ID is not an account id");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testGetAccountBalance_NoProvider() {
        FEAAFacadeImpl feaa = new FEAAFacadeImpl();
        try {
            feaa.getAccountBalance(1);
            fail("Should throw exception if client provider is not set");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testGetAccountBalance_Valid() {
        FEAAFacadeImpl feaa = setup();

        feaa.addAccount(1, feaa.getAllClients().get(0).getID(),"Account 1", 100,50);
        feaa.addAccount(2, feaa.getAllClients().get(1).getID(), "Account 2", 100,100);
        feaa.addAccount(3, feaa.getAllClients().get(2).getID(),"Account 3", 50,100);

        assertEquals(50, feaa.getAccountBalance(1));
        assertEquals(0, feaa.getAccountBalance(2));
        assertEquals(-50, feaa.getAccountBalance(3));
    }
    @Test
    public void testGetAccountBalance_AfterChanged() {
        FEAAFacadeImpl feaa = setup();

        feaa.addAccount(1, feaa.getAllClients().get(0).getID(),"Account 1", 100,50);
        assertEquals(50, feaa.getAccountBalance(1));

        //change incomings and outgoings
        feaa.setAccountOutgoings(1,100);
        feaa.setAccountIncomings(1,200);
        assertEquals(100,feaa.getAccountBalance(1));
    }

    @Test
    public void testGetAccountIncomings_Valid() {
        FEAAFacadeImpl feaa = setup();

        feaa.addAccount(1, feaa.getAllClients().get(0).getID(),"Account 1", 0,50);
        feaa.addAccount(2, feaa.getAllClients().get(1).getID(), "Account 2", 1,100);
        feaa.addAccount(3, feaa.getAllClients().get(2).getID(),"Account 3", 100,100);

        assertEquals(0, feaa.getAccountIncomings(1));
        assertEquals(1, feaa.getAccountIncomings(2));
        assertEquals(100, feaa.getAccountIncomings(3));
    }
    @Test
    public void testGetAccountIncomings_IllegalID() {
        FEAAFacadeImpl feaa = setup2();
        try {
            feaa.getAccountIncomings(0);
            fail("Should throw exception if ID is zero");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            feaa.getAccountIncomings(-1);
            fail("Should throw exception if ID is negative");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }
    @Test
    public void testGetAccountIncomings_NoMatchingID() {
        FEAAFacadeImpl feaa = setup2();
        try {
            feaa.getAccountIncomings(10);
            fail("Should throw exception if ID is not an account id");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testGetAccountIncomings_NoProvider() {
        FEAAFacadeImpl feaa = new FEAAFacadeImpl();
        try {
            feaa.getAccountIncomings(1);
            fail("Should throw exception if client provider is not set");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }

    @Test
    public void testGetAccountOutgoings_Valid() {
        FEAAFacadeImpl feaa = setup();

        feaa.addAccount(1, feaa.getAllClients().get(0).getID(),"Account 1", 50,0);
        feaa.addAccount(2, feaa.getAllClients().get(1).getID(), "Account 2", 50,1);
        feaa.addAccount(3, feaa.getAllClients().get(2).getID(),"Account 3", 50,100);

        assertEquals(0, feaa.getAccountOutgoings(1));
        assertEquals(1, feaa.getAccountOutgoings(2));
        assertEquals(100, feaa.getAccountOutgoings(3));
    }
    @Test
    public void testGetAccountOutgoings_IllegalID() {
        FEAAFacadeImpl feaa = setup2();
        try {
            feaa.getAccountOutgoings(0);
            fail("Should throw exception if ID is zero");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            feaa.getAccountOutgoings(-1);
            fail("Should throw exception if ID is negative");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }
    @Test
    public void testGetAccountOutgoings_NoMatchingID() {
        FEAAFacadeImpl feaa = setup2();
        try {
            feaa.getAccountOutgoings(10);
            fail("Should throw exception if ID is not an account id");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testGetAccountOutgoings_NoProvider() {
        FEAAFacadeImpl feaa = new FEAAFacadeImpl();
        try {
            feaa.getAccountOutgoings(1);
            fail("Should throw exception if client provider is not set");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testSetAccountIncomings_Valid() {
        FEAAFacadeImpl feaa = setup();

        feaa.addAccount(1, feaa.getAllClients().get(0).getID(),"Account 1", 0,50);

        assertEquals(0, feaa.getAccountIncomings(1));
        feaa.setAccountIncomings(1,100);
        assertEquals(100, feaa.getAccountIncomings(1));
    }
    @Test
    public void testSetAccountIncomings_IllegalID() {
        FEAAFacadeImpl feaa = setup2();
        try {
            feaa.setAccountIncomings(0,100);
            fail("Should throw exception if ID is zero");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            feaa.setAccountIncomings(-1,100);
            fail("Should throw exception if ID is negative");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }
    @Test
    public void testSetAccountIncomings_NoMatchingID() {
        FEAAFacadeImpl feaa = setup2();
        try {
            feaa.setAccountIncomings(10,100);
            fail("Should throw exception if ID is not an account id");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testSetAccountIncomings_NoProvider() {
        FEAAFacadeImpl feaa = new FEAAFacadeImpl();
        try {
            feaa.setAccountIncomings(1,100);
            fail("Should throw exception if client provider is not set");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void setAccountOutgoings_Valid() {
        FEAAFacadeImpl feaa = setup();

        feaa.addAccount(1, feaa.getAllClients().get(0).getID(),"Account 1", 50,0);

        assertEquals(0, feaa.getAccountOutgoings(1));
        feaa.setAccountOutgoings(1,100);
        assertEquals(100, feaa.getAccountOutgoings(1));
    }
    @Test
    public void testSetAccountOutgoings_IllegalID() {
        FEAAFacadeImpl feaa = setup2();
        try {
            feaa.setAccountOutgoings(0,100);
            fail("Should throw exception if ID is zero");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            feaa.setAccountOutgoings(-1,100);
            fail("Should throw exception if ID is negative");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }
    @Test
    public void testSetAccountOutgoings_NoMatchingID() {
        FEAAFacadeImpl feaa = setup2();
        try {
            feaa.setAccountOutgoings(10,100);
            fail("Should throw exception if ID is not an account id");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testSetAccountOutgoings_NoProvider() {
        FEAAFacadeImpl feaa = new FEAAFacadeImpl();
        try {
            feaa.setAccountOutgoings(1,100);
            fail("Should throw exception if client provider is not set");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }

    @Test
    public void testSetClientProvider_NormalConditions() {
        FEAAFacadeImpl feaa = new FEAAFacadeImpl();
        feaa.setClientProvider(new ClientListImpl());
        //assert that clientProvider works
        assertNotNull(feaa.addClient("firstName", "lastName", "123"));
        assertFalse(feaa.getAllClients().isEmpty());
    }
    @Test
    public void testSetClientProvider_NewProviderClearsAccounts() {
        FEAAFacadeImpl feaa = setup2();
        //show there are currently accounts
        assertFalse(feaa.getAccounts().isEmpty());
        feaa.setClientProvider(new ClientListImpl());
        assertTrue(feaa.getAccounts().isEmpty());
    }
    @Test
    public void testSetClientProvider_NewProvider_NullIDStateReset() {
        FEAAFacadeImpl feaa = setup();
        feaa.addAccount(1, feaa.getAllClients().get(0).getID(),"Account 1", 100,50);
        //show Null id state isn't allowed anymore
        try {
            feaa.addAccount(null, feaa.getAllClients().get(1).getID(),"Account 2", 100,50);
            fail("Should throw exception when null ID state is violated. Error in addAccount()");
        } catch(Exception e) {
            assertTrue("addAccount() error",e instanceof IllegalArgumentException);
        }
        //reset Null ID state
        feaa.setClientProvider(new ClientListImpl());
        feaa.addClient("first","last","1111111111");
        feaa.addClient("first2","last2","2222222222");
        //null is allowed
        feaa.addAccount(null,feaa.getAllClients().get(0).getID(),"Account 1", 100,50);
        feaa.addAccount(null,feaa.getAllClients().get(0).getID(),"Account 1", 100,50);
        assertEquals(2, feaa.getAccounts().size());
    }
    @Test
    public void testSetClientProvider_SetToNull() {
        FEAAFacadeImpl feaa = new FEAAFacadeImpl();
        try {
            feaa.setClientProvider(null);
        } catch(Exception e){
            fail("The provider to inject may be null");
        }
        try {
            feaa.addClient("first","last","1234567890");
            fail("Should throw exceptions when doing client operations with null provider");
        } catch(Exception e){
            assertTrue(e instanceof IllegalStateException);
        }
    }
}