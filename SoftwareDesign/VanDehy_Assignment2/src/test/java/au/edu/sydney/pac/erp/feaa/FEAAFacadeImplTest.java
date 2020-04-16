package au.edu.sydney.pac.erp.feaa;

import au.edu.sydney.pac.erp.auth.AuthModule;
import au.edu.sydney.pac.erp.auth.AuthToken;
import au.edu.sydney.pac.erp.client.Client;
import au.edu.sydney.pac.erp.client.ClientList;
import au.edu.sydney.pac.erp.client.ClientListImpl;
import au.edu.sydney.pac.erp.email.EmailService;
import au.edu.sydney.pac.erp.fax.FaxService;
import au.edu.sydney.pac.erp.print.PrintService;
import au.edu.sydney.pac.erp.reporting.ReportFacade;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.security.AuthProvider;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FEAAFacadeImplTest {

//    //create FEAAFacadeImpl with clientProvider and 5 valid clients
//    private FEAAFacadeImpl setup() {
//        FEAAFacadeImpl feaa = new FEAAFacadeImpl();
//        feaa.setClientProvider(new ClientListImpl());
//        feaa.addClient("Zeke", "Van Dehy", "+1(720)231 8709");
//        feaa.addClient("Mary Margaret", "Greene", "0484 604 675");
//        feaa.addClient("Client", "Three", "1234567890");
//        feaa.addClient("Fourth", "Four", "444 444 4444");
//        feaa.addClient("Fifth", "Five", "555 555 5555");
//        return feaa;
//    }
//    //create valid accounts for feaa from setup()
//    private FEAAFacadeImpl setup2() {
//        FEAAFacadeImpl feaa = setup();
//        feaa.addAccount(1, feaa.getAllClients().get(0).getID(),"Account 1", 100,50, "1111111111", "email1@gmail.com");
//        feaa.addAccount(2, feaa.getAllClients().get(1).getID(),"Account 2", 100,50, "2222222222", "email2@gmail.com");
//        feaa.addAccount(3, feaa.getAllClients().get(2).getID(),"Account 3", 100,50, "3333333333", "email3@gmail.com");
//        feaa.addAccount(4, feaa.getAllClients().get(3).getID(),"Account 4", 100,50, "4444444444", "email4@gmail.com");
//        feaa.addAccount(5, feaa.getAllClients().get(4).getID(),"Account 5", 100,50, "5555555555", "email5@gmail.com");
//
//        feaa.addAccount(6, feaa.getAllClients().get(0).getID(),"Account 1b", 100,50, "1111111111", "email6@gmail.com");
//        feaa.addAccount(7, feaa.getAllClients().get(0).getID(),"Account 1c", 100,50, "7777777777", "email1@gmail.com");
//        return feaa;
//    }

    //addClient
    //setup creates FEAAFacade that is ready to test illegal entries
    private FEAAFacade setupAddClient() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        //mocks so that no IllegalState or SecurityExceptions are thrown
        ClientList clientProvider = mock(ClientList.class);
        AuthModule authProvider = mock(AuthModule.class);
        feaa.setAuthProvider(authProvider);
        when(authProvider.login(anyString(), anyString())).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);
        assertTrue(feaa.login("username", "password"));

        feaa.setClientProvider(clientProvider);
        return feaa;
    }
    @Test
    public void testAddClient_IllegalFirstName() {
        FEAAFacade feaa = setupAddClient();

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
        FEAAFacade feaa = setupAddClient();
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
        FEAAFacade feaa = setupAddClient();
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
    public void testAddClient_NullClientOrAuthProvider() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        //both null
        try {
            feaa.addClient("first","last","7202318709");
            fail("Should throw exception when no providers are set");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }

        //null auth only
        FEAAFacade feaa1 = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        when(clientProvider.addClient(anyInt(), anyString(), anyString(), anyString())).thenReturn(mock(Client.class));
        feaa1.setClientProvider(clientProvider);
        try {
            feaa.addClient("first","last","7202318709");
            fail("Should throw exception when authProvider is null");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }

        //null client only
        FEAAFacade feaa2 = new FEAAFacadeImpl();
        AuthModule authProvider = mock(AuthModule.class);
        when(authProvider.login(anyString(),anyString())).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);
        feaa2.setAuthProvider(authProvider);
        feaa2.login("username","password");
        try {
            feaa.addClient("first", "last", "7202318709");
            fail("Should throw exception when clientProvider is null");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testAddClient_SecurityException() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        when(clientProvider.addClient(anyInt(), anyString(), anyString(), anyString())).thenReturn(mock(Client.class));
        feaa.setClientProvider(clientProvider);

        AuthModule authProvider = mock(AuthModule.class);
        when(authProvider.login(anyString(),anyString())).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);
        feaa.setAuthProvider(authProvider);

        //not logged in
        try {
            feaa.addClient("first", "last", "7202318709");
        } catch (Exception e) {
            assertTrue(e instanceof SecurityException);
        }

        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(false);
        feaa.login("username", "password");
        //logged in but not authenticated
        try {
            feaa.addClient("first", "last", "7202318709");
        } catch (Exception e) {
            assertTrue(e instanceof SecurityException);
        }
    }
    @Test
    public void testAddClient_Valid() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        AuthModule authProvider = mock(AuthModule.class);
        feaa.setAuthProvider(authProvider);
        when(authProvider.login(anyString(), anyString())).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);
        assertTrue(feaa.login("username", "password"));
        feaa.setClientProvider(clientProvider);

        feaa.addClient("first", "last", "+1(720) 231 8709");
        verify(clientProvider, times(1)).addClient(anyInt(),eq("first"),eq("last"),eq("+1(720) 231 8709"));
    }

    //assignClient
    //setup a FEAAFacade with providers
    private FEAAFacade setupAssignClient() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        when(clientProvider.findOne(anyInt())).thenReturn(mock(Client.class)); //always return a Client when findOne() is called
        when(clientProvider.findOne(5)).thenReturn(null); //except for ID 5

        AuthModule authProvider = mock(AuthModule.class);
        feaa.setAuthProvider(authProvider);
        when(authProvider.login(anyString(), anyString())).thenReturn(mock(AuthToken.class)); //always successfully login
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true); //always successfully authenticate
        assertTrue(feaa.login("username", "password"));
        feaa.setClientProvider(clientProvider);
        return feaa;
    }
    @Test
    public void testAssignClient_IllegalID() {
        FEAAFacade feaa = setupAssignClient();
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
    public void testAssignClient_IllegalDepartment() {
        FEAAFacade feaa = setupAssignClient();
        //any ID that assignClient() doesn't catch is valid
        try {
            feaa.assignClient(1, "ERROR");
            fail("Should throw exception if department code is invalid");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }
    @Test
    public void testAssignClient_NoMatchingID() {
        FEAAFacade feaa = setupAssignClient();
        //5 is setup to be unmatched in clientProvider mock
        try {
            feaa.assignClient(5, "DOMESTIC");
            fail("Should throw exception if given ID isn't in clientList");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }

    }
    @Test
    public void testAssignClient_DuplicateIDAssigned() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        when(clientProvider.findOne(anyInt())).thenReturn(mock(Client.class)); //always return a Client when findOne() is called
        when(clientProvider.findOne(5)).thenReturn(null); //except for ID 5
        AuthModule authProvider = mock(AuthModule.class);
        feaa.setAuthProvider(authProvider);
        when(authProvider.login(anyString(), anyString())).thenReturn(mock(AuthToken.class)); //always successfully login
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true); //always successfully authenticate
        assertTrue(feaa.login("username", "password"));
        feaa.setClientProvider(clientProvider);
        //id 1 already assigned
        Client clientMock = mock(Client.class);
        when(clientProvider.findOne(1)).thenReturn(clientMock);
        when(clientMock.isAssigned()).thenReturn(true);

        try {
            feaa.assignClient(1, "INTERNATIONAL");
            fail("Should throw exception if given ID is already assigned to a department");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testAssignClient_Valid_LongVersion() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        AuthModule authProvider = mock(AuthModule.class);
        feaa.setAuthProvider(authProvider);
        when(authProvider.login(anyString(), anyString())).thenReturn(mock(AuthToken.class)); //always successfully login
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true); //always successfully authenticate
        assertTrue(feaa.login("username", "password"));
        feaa.setClientProvider(clientProvider);

        Client clientMock = mock(Client.class);
        ArgumentCaptor<String> departments = ArgumentCaptor.forClass(String.class);
        when(clientProvider.findOne(1)).thenReturn(clientMock);
        when(clientMock.isAssigned()).thenReturn(false);

        feaa.assignClient(1,"DOMESTIC");
        feaa.assignClient(1,"INTERNATIONAL");
        feaa.assignClient(1,"LARGE ACCOUNTS");

       verify(clientMock, times(3)).assignDepartment(departments.capture());
       assertTrue(departments.getAllValues().contains("DOMESTIC"));
       assertTrue(departments.getAllValues().contains("INTERNATIONAL"));
       assertTrue(departments.getAllValues().contains("LARGE ACCOUNTS"));
    }
    @Test
    public void testAssignClient_Valid_ShortVersion() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        AuthModule authProvider = mock(AuthModule.class);
        feaa.setAuthProvider(authProvider);
        when(authProvider.login(anyString(), anyString())).thenReturn(mock(AuthToken.class)); //always successfully login
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true); //always successfully authenticate
        assertTrue(feaa.login("username", "password"));
        feaa.setClientProvider(clientProvider);

        Client clientMock = mock(Client.class);
        ArgumentCaptor<String> departments = ArgumentCaptor.forClass(String.class);
        when(clientProvider.findOne(1)).thenReturn(clientMock);
        when(clientMock.isAssigned()).thenReturn(false);

        feaa.assignClient(1,"DOM");
        feaa.assignClient(1,"INT");
        feaa.assignClient(1,"LRG");

        verify(clientMock, times(3)).assignDepartment(departments.capture());
        assertTrue(departments.getAllValues().contains("DOMESTIC"));
        assertTrue(departments.getAllValues().contains("INTERNATIONAL"));
        assertTrue(departments.getAllValues().contains("LARGE ACCOUNTS"));
    }
    @Test
    public void testAssignClient_NullClientOrAuthProvider() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        //both null
        try {
            feaa.assignClient(1,"INT");
            fail("Should throw exception when no providers are set");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }

        //null auth only
        FEAAFacade feaa1 = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        when(clientProvider.findOne(1)).thenReturn(mock(Client.class));
        feaa1.setClientProvider(clientProvider);
        try {
            feaa.assignClient(1,"INT");
            fail("Should throw exception when authProvider is null");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }

        //null client only
        FEAAFacade feaa2 = new FEAAFacadeImpl();
        AuthModule authProvider = mock(AuthModule.class);
        when(authProvider.login(anyString(),anyString())).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);
        feaa2.setAuthProvider(authProvider);
        feaa2.login("username","password");
        try {
            feaa.assignClient(1,"INT");
            fail("Should throw exception when clientProvider is null");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testAssignClient_SecurityException() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        when(clientProvider.addClient(anyInt(), anyString(), anyString(), anyString())).thenReturn(mock(Client.class));
        feaa.setClientProvider(clientProvider);

        AuthModule authProvider = mock(AuthModule.class);
        when(authProvider.login(anyString(),anyString())).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);
        feaa.setAuthProvider(authProvider);

        //not logged in
        try {
            feaa.assignClient(1, "INT");
        } catch (Exception e) {
            assertTrue(e instanceof SecurityException);
        }

        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(false);
        feaa.login("username", "password");
        //logged in but not authenticated
        try {
            feaa.assignClient(1, "INT");
        } catch (Exception e) {
            assertTrue(e instanceof SecurityException);
        }
    }

    //viewAllClients
    @Test
    public void testViewAllClients() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        AuthModule authProvider = mock(AuthModule.class);
        feaa.setAuthProvider(authProvider);
        when(authProvider.login(anyString(), anyString())).thenReturn(mock(AuthToken.class)); //always successfully login
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true); //always successfully authenticate
        assertTrue(feaa.login("username", "password"));
        feaa.setClientProvider(clientProvider);

        List<String> expected = new ArrayList<>();
        expected.add("last, first");
        expected.add("last2, first2");
        expected.add("last3, first3");

        Client client = mock(Client.class);
        Client client2 = mock(Client.class);
        Client client3 = mock(Client.class);

        when(client.getFirstName()).thenReturn("first");
        when(client.getLastName()).thenReturn("last");
        when(client2.getFirstName()).thenReturn("first2");
        when(client2.getLastName()).thenReturn("last2");
        when(client3.getFirstName()).thenReturn("first3");
        when(client3.getLastName()).thenReturn("last3");

        List<Client> clients = new ArrayList<>();
        clients.add(client);
        clients.add(client2);
        clients.add(client3);
        when(clientProvider.findAll()).thenReturn(clients);
        List<String> result = feaa.viewAllClients();

        assertTrue(result.contains(expected.get(0)));
        assertTrue(result.contains(expected.get(1)));
        assertTrue(result.contains(expected.get(2)));
        assertEquals(3, result.size());
        verify(clientProvider, atLeastOnce()).findAll();
    }
    @Test
    public void testViewAllClients_Empty() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        AuthModule authProvider = mock(AuthModule.class);
        feaa.setAuthProvider(authProvider);
        when(authProvider.login(anyString(), anyString())).thenReturn(mock(AuthToken.class)); //always successfully login
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true); //always successfully authenticate
        assertTrue(feaa.login("username", "password"));
        feaa.setClientProvider(clientProvider);

        List<String> result = feaa.viewAllClients();
        verify(clientProvider, atLeastOnce()).findAll();
        assertTrue(result.isEmpty());
    }
    @Test
    public void testViewAllClients_NullClientOrAuthProviders() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        //both null
        try {
            feaa.viewAllClients();
            fail("Should throw exception when no providers are set");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }

        //null auth only
        FEAAFacade feaa1 = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        feaa1.setClientProvider(clientProvider);
        try {
            feaa.viewAllClients();
            fail("Should throw exception when authProvider is null");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }

        //null client only
        FEAAFacade feaa2 = new FEAAFacadeImpl();
        AuthModule authProvider = mock(AuthModule.class);
        when(authProvider.login(anyString(),anyString())).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);
        feaa2.setAuthProvider(authProvider);
        feaa2.login("username","password");
        try {
            feaa.viewAllClients();
            fail("Should throw exception when clientProvider is null");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testViewAllClients_SecurityException() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        feaa.setClientProvider(clientProvider);

        AuthModule authProvider = mock(AuthModule.class);
        when(authProvider.login(anyString(),anyString())).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);
        feaa.setAuthProvider(authProvider);

        //not logged in
        try {
            feaa.viewAllClients();
        } catch (Exception e) {
            assertTrue(e instanceof SecurityException);
        }

        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(false);
        feaa.login("username", "password");
        //logged in but not authenticated
        try {
            feaa.viewAllClients();
        } catch (Exception e) {
            assertTrue(e instanceof SecurityException);
        }
    }

    //getAllClients
    @Test
    public void testGetAllClients() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        AuthModule authProvider = mock(AuthModule.class);
        feaa.setAuthProvider(authProvider);
        when(authProvider.login(anyString(), anyString())).thenReturn(mock(AuthToken.class)); //always successfully login
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true); //always successfully authenticate
        assertTrue(feaa.login("username", "password"));
        feaa.setClientProvider(clientProvider);

        Client client = mock(Client.class);
        Client client2 = mock(Client.class);
        Client client3 = mock(Client.class);

        when(client.getFirstName()).thenReturn("first");
        when(client.getLastName()).thenReturn("last");
        when(client2.getFirstName()).thenReturn("first2");
        when(client2.getLastName()).thenReturn("last2");
        when(client3.getFirstName()).thenReturn("first3");
        when(client3.getLastName()).thenReturn("last3");

        List<Client> clients = new ArrayList<>();
        clients.add(client);
        clients.add(client2);
        clients.add(client3);
        when(clientProvider.findAll()).thenReturn(clients);

        List<Client> result = feaa.getAllClients();

        assertTrue(result.contains(client));
        assertTrue(result.contains(client2));
        assertTrue(result.contains(client3));

        verify(clientProvider, atLeastOnce()).findAll();
    }
    @Test
    public void testGetAllClients_Empty() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        AuthModule authProvider = mock(AuthModule.class);
        feaa.setAuthProvider(authProvider);
        when(authProvider.login(anyString(), anyString())).thenReturn(mock(AuthToken.class)); //always successfully login
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true); //always successfully authenticate
        assertTrue(feaa.login("username", "password"));
        feaa.setClientProvider(clientProvider);

        List<Client> result = feaa.getAllClients();
        verify(clientProvider, atLeastOnce()).findAll();
        assertTrue(result.isEmpty());
    }
    @Test
    public void testGetAllClients_NullClientOrAuthProviders() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        //both null
        try {
            feaa.getAllClients();
            fail("Should throw exception when no providers are set");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }

        //null auth only
        FEAAFacade feaa1 = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        feaa1.setClientProvider(clientProvider);
        try {
            feaa.getAllClients();
            fail("Should throw exception when authProvider is null");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }

        //null client only
        FEAAFacade feaa2 = new FEAAFacadeImpl();
        AuthModule authProvider = mock(AuthModule.class);
        when(authProvider.login(anyString(),anyString())).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);
        feaa2.setAuthProvider(authProvider);
        feaa2.login("username","password");
        try {
            feaa.getAllClients();
            fail("Should throw exception when clientProvider is null");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testGetAllClients_SecurityException() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        feaa.setClientProvider(clientProvider);

        AuthModule authProvider = mock(AuthModule.class);
        when(authProvider.login(anyString(),anyString())).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);
        feaa.setAuthProvider(authProvider);

        //not logged in
        try {
            feaa.getAllClients();
        } catch (Exception e) {
            assertTrue(e instanceof SecurityException);
        }

        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(false);
        feaa.login("username", "password");
        //logged in but not authenticated
        try {
            feaa.getAllClients();
        } catch (Exception e) {
            assertTrue(e instanceof SecurityException);
        }
    }

    //addAccount
    //setup a FEAAFacade with providers
    public FEAAFacade setupAddAcount() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        //mock client
        Client mockedClient = mock(Client.class);
        when(clientProvider.findOne(anyInt())).thenReturn(mockedClient); //always return a Client when findOne() is called
        when(mockedClient.getFirstName()).thenReturn("first");
        when(mockedClient.getLastName()).thenReturn("last");
        when(clientProvider.findOne(5)).thenReturn(null); //except for ID 5, which is unmatched

        AuthModule authProvider = mock(AuthModule.class);
        feaa.setAuthProvider(authProvider);
        when(authProvider.login(anyString(), anyString())).thenReturn(mock(AuthToken.class)); //always successfully login
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true); //always successfully authenticate
        assertTrue(feaa.login("username", "password"));


        feaa.setClientProvider(clientProvider);
        return feaa;
    }
    @Test
    public void testAddAccount_IllegalClientID() {
        FEAAFacade feaa = setupAddAcount();
        try {
            feaa.addAccount(1, 0, "accountName", 0,0, "1111111111", "email@gmail.com");
            fail("Should throw exception when client ID is zero");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            feaa.addAccount(1, -1, "accountName", 0,0, "1111111111", "email@gmail.com");
            fail("Should throw exception when client ID is negative");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            feaa.addAccount(1, 5, "accountName", 0,0, "1111111111", "email@gmail.com");
            fail("Should throw exception when client ID is not found in client provider");
        } catch(IllegalStateException e) {
            assertTrue(true);
        }
        assertTrue(feaa.getAccounts().isEmpty());
    }
    @Test
    public void testAddAccount_IllegalAccountID() {
        FEAAFacade feaa = setupAddAcount();
        try {
            feaa.addAccount(0, 1, "accountName", 0,0, "1111111111", "email@gmail.com");
            fail("Should throw exception when account ID is zero");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            feaa.addAccount(-1, 1, "accountName", 0,0, "1111111111", "email@gmail.com");
            fail("Should throw exception when account ID is negative");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            feaa.addAccount(1, 1, "accountName", 0,0, "1111111111", "email@gmail.com");
            feaa.addAccount(1, 1, "accountName2", 2,2, "2222222222", "email2@gmail.com");
            fail("Should throw exception when account ID is not unique");
        } catch(IllegalArgumentException e) {
            assertTrue(true);
        }
        assertEquals(1, feaa.getAccounts().size());
    }
    @Test
    public void testAddAccount_IllegalClientID_NullAfterExplicit() {
        FEAAFacade feaa = setupAddAcount();
        //valid addAccount
        feaa.addAccount(1,1,"Account 1", 100,50, "111", "email@gmail.com");
        //can't be null after provided
        try {
            feaa.addAccount(null, 1, "Account 2", 0,0, "111", "email@gmail.com");
            fail("Should throw exception when client ID is null after not-null id was provided");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testAddAccount_IllegalAccountName() {
        FEAAFacade feaa = setupAddAcount();
        try {
            feaa.addAccount(1, 1, "", 0,0, "111", "email@gmail.com");
            fail("Should throw exception when account name is empty");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            feaa.addAccount(1, 1, null, 0,0, "111", "email@gmail.com");
            fail("Should throw exception when account name is null");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }
    @Test
    public void testAddAccount_IllegalInitialIncoming() {
        FEAAFacade feaa = setupAddAcount();
        try {
            feaa.addAccount(1, 1, "accountName", -1,0, "111", "email@gmail.com");
            fail("Should throw exception when initial incomings is negative");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }
    @Test
    public void testAddAccount_IllegalInitialOutgoing() {
        FEAAFacade feaa = setupAddAcount();
        try {
            feaa.addAccount(1, 1, "accountName", 0,-1, "111", "email@gmail.com");
            fail("Should throw exception when initial outgoings is negative");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }
    @Test
    public void testAddAccount_ReportPhone() {
        FEAAFacade feaa = setupAddAcount();
        try {
            feaa.addAccount(1, 1, "accountName", 500,100, "", "email@gmail.com");
            fail("Should throw exception when reportPhone is empty");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            feaa.addAccount(1, 1, "accountName", 500,100, "11abc11", "email@gmail.com");
            fail("Should throw exception when reportPhone contains letters");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            feaa.addAccount(1, 1, "accountName", 500,100, "111 111 1111", "email@gmail.com");
            fail("Should throw exception when reportPhone contains spaces");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            feaa.addAccount(1, 1, "accountName", 500,100, "111-111-1111", "email@gmail.com");
            fail("Should throw exception when reportPhone contains dashes");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        assertEquals(100, feaa.addAccount(100, 1, "accountName", 500,100, null, "email@gmail.com"));
    }
    @Test
    public void testAddAccount_ReportEmail() {
        FEAAFacade feaa = setupAddAcount();
        try {
            feaa.addAccount(1, 1, "accountName", 500,100, "111", "");
            fail("Should throw exception when reportPhone is empty");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            feaa.addAccount(1, 1, "accountName", 500,100, "111", "email.gmail.com");
            fail("Should throw exception when reportPhone doesn't contain at least one @");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        assertEquals(200, feaa.addAccount(200, 1, "accountName", 500,100, "111", null));
        assertEquals(300, feaa.addAccount(300, 1, "accountName", 500,100, "111", "@@@@@"));
    }
    @Test
    public void testAddAccount_NullReportPhoneAndEmail() {
        FEAAFacade feaa = setupAddAcount();
        try {
            feaa.addAccount(1, 1, "accountName", 500,100, null, null);
            fail("Should throw exception when reportPhone and reportEmail are both null");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }
    @Test
    public void testAddAccount_Valid_NullThenExplicit() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        //mock client
        Client mockedClient = mock(Client.class);
        when(clientProvider.findOne(anyInt())).thenReturn(mockedClient); //always return a Client when findOne() is called
        when(mockedClient.getFirstName()).thenReturn("first");
        when(mockedClient.getLastName()).thenReturn("last");
        when(clientProvider.findOne(5)).thenReturn(null); //except for ID 5, which is unmatched

        AuthModule authProvider = mock(AuthModule.class);
        feaa.setAuthProvider(authProvider);
        when(authProvider.login(anyString(), anyString())).thenReturn(mock(AuthToken.class)); //always successfully login
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true); //always successfully authenticate
        assertTrue(feaa.login("username", "password"));


        feaa.setClientProvider(clientProvider);
        //valid addAccount
        feaa.addAccount(null, 11,"Account 1", 100,50, "111", "1@");
        feaa.addAccount(null, 22, "Account 2", 100,50, "222", "2@");
        feaa.addAccount(null, 33,"Account 3", 100,50, "333", "3@");
        feaa.addAccount(null, 44, "Account 4", 100,50, "444", "4@");
        feaa.addAccount(null, 55,"Account 5", 100,50, "555", "5@");

        //verify 5 accounts were added
        assertEquals(5,feaa.getAccounts().size());
        //verify 1 account was added for each client
        assertEquals(1,feaa.getAccounts(11).size());
        assertEquals(1,feaa.getAccounts(22).size());
        assertEquals(1,feaa.getAccounts(33).size());
        assertEquals(1,feaa.getAccounts(44).size());
        assertEquals(1,feaa.getAccounts(55).size());

        //add more than one account for one client with explicit ID
        feaa.addAccount(25, 11,"Account 1b", 100,50, "111", "1@");
        feaa.addAccount(100, 11, "Account 1c", 100,50, "111", "1@");

        //verify there are now 7 accounts
        assertEquals(7,feaa.getAccounts().size());
        //verify there are 3 accounts for client 0
        assertEquals(3,feaa.getAccounts(11).size());
    }
    @Test
    public void testAddAccount_NullClientOrAuthProvider() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        //both null
        try {
            feaa.addAccount(1, 1, "accountName", 500,100, "111", "email@gmail.com");
            fail("Should throw exception when no providers are set");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }

        //null auth only
        FEAAFacade feaa1 = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        feaa1.setClientProvider(clientProvider);
        try {
            feaa1.addAccount(1, 1, "accountName", 500,100, "111", "email@gmail.com");
            fail("Should throw exception when authProvider is null");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }

        //null client only
        FEAAFacade feaa2 = new FEAAFacadeImpl();
        AuthModule authProvider = mock(AuthModule.class);
        when(authProvider.login(anyString(),anyString())).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);
        feaa2.setAuthProvider(authProvider);
        feaa2.login("username","password");
        try {
            feaa2.addAccount(1, 1, "accountName", 500,100, "111", "email@gmail.com");
            fail("Should throw exception when clientProvider is null");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testAddAccount_SecurityException() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        feaa.setClientProvider(clientProvider);

        AuthModule authProvider = mock(AuthModule.class);
        when(authProvider.login(anyString(),anyString())).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);
        feaa.setAuthProvider(authProvider);

        //not logged in
        try {
            feaa.addAccount(1, 1, "accountName", 500,100, "111", "email@gmail.com");
        } catch (Exception e) {
            assertTrue(e instanceof SecurityException);
        }

        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(false);
        feaa.login("username", "password");
        //logged in but not authenticated
        try {
            feaa.addAccount(1, 1, "accountName", 500,100, "111", "email@gmail.com");
        } catch (Exception e) {
            assertTrue(e instanceof SecurityException);
        }
    }

    //getAccounts_NoParameter
    @Test
    public void testGetAccounts_NoParameter_Empty() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        AuthModule authProvider = mock(AuthModule.class);
        feaa.setAuthProvider(authProvider);
        when(authProvider.login(anyString(), anyString())).thenReturn(mock(AuthToken.class)); //always successfully login
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true); //always successfully authenticate
        assertTrue(feaa.login("username", "password"));
        feaa.setClientProvider(clientProvider);

        List<String> result = feaa.getAccounts();
        assertTrue(result.isEmpty());
    }
    @Test
    public void testGetAccounts_NoParameter_Valid() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        //mock client
        Client mockedClient = mock(Client.class);
        when(clientProvider.findOne(anyInt())).thenReturn(mockedClient); //always return a Client when findOne() is called
        when(mockedClient.getFirstName()).thenReturn("first");
        when(mockedClient.getLastName()).thenReturn("last");
        when(clientProvider.findOne(5)).thenReturn(null); //except for ID 5, which is unmatched

        AuthModule authProvider = mock(AuthModule.class);
        feaa.setAuthProvider(authProvider);
        when(authProvider.login(anyString(), anyString())).thenReturn(mock(AuthToken.class)); //always successfully login
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true); //always successfully authenticate
        assertTrue(feaa.login("username", "password"));


        feaa.setClientProvider(clientProvider);
        //valid addAccount
        feaa.addAccount(1, 11,"Account 1", 100,50, "111", "1@");
        feaa.addAccount(2, 22, "Account 2", 100,50, "222", "2@");
        feaa.addAccount(3, 33,"Account 3", 100,50, "333", "3@");
        feaa.addAccount(4, 44, "Account 4", 100,50, "444", "4@");
        feaa.addAccount(5, 55,"Account 5", 100,50, "555", "5@");

        //verify 5 accounts were added
        List<String> result = feaa.getAccounts();
        assertEquals(5,result.size());
        assertTrue(result.contains("1: Account 1"));
        assertTrue(result.contains("2: Account 2"));
        assertTrue(result.contains("3: Account 3"));
        assertTrue(result.contains("4: Account 4"));
        assertTrue(result.contains("5: Account 5"));
    }
    @Test
    public void testGetAccounts_NoParameter_NullAuthProvider() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        feaa.setClientProvider(clientProvider);
        try {
            feaa.getAccounts();
            fail("Should throw exception when authProvider is null");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testGetAccounts_NoParameter_SecurityException() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        feaa.setClientProvider(clientProvider);

        AuthModule authProvider = mock(AuthModule.class);
        when(authProvider.login(anyString(),anyString())).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);
        feaa.setAuthProvider(authProvider);

        //not logged in
        try {
            feaa.getAccounts();
        } catch (Exception e) {
            assertTrue(e instanceof SecurityException);
        }

        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(false);
        feaa.login("username", "password");
        //logged in but not authenticated
        try {
            feaa.getAccounts();
        } catch (Exception e) {
            assertTrue(e instanceof SecurityException);
        }
    }

    //getAccounts_ClientID
    private FEAAFacade setupGetAccounts_ClientID() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        //mock client
        Client mockedClient = mock(Client.class);
        when(clientProvider.findOne(anyInt())).thenReturn(mockedClient); //always return a Client when findOne() is called
        when(mockedClient.getFirstName()).thenReturn("first");
        when(mockedClient.getLastName()).thenReturn("last");
        when(clientProvider.findOne(5)).thenReturn(null); //except for ID 5, which is unmatched

        AuthModule authProvider = mock(AuthModule.class);
        feaa.setAuthProvider(authProvider);
        when(authProvider.login(anyString(), anyString())).thenReturn(mock(AuthToken.class)); //always successfully login
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true); //always successfully authenticate
        assertTrue(feaa.login("username", "password"));


        feaa.setClientProvider(clientProvider);
        return feaa;
    }
    @Test
    public void testGetAccounts_ClientID_Empty() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        Client mockedClient = mock(Client.class);
        when(clientProvider.findOne(anyInt())).thenReturn(mockedClient); //always return a Client when findOne() is called
        when(mockedClient.getFirstName()).thenReturn("first");
        when(mockedClient.getLastName()).thenReturn("last");
        when(clientProvider.findOne(5)).thenReturn(null); //except for ID 5, which is unmatched
        AuthModule authProvider = mock(AuthModule.class);
        feaa.setAuthProvider(authProvider);
        when(authProvider.login(anyString(), anyString())).thenReturn(mock(AuthToken.class)); //always successfully login
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true); //always successfully authenticate
        assertTrue(feaa.login("username", "password"));
        feaa.setClientProvider(clientProvider);

        List<Integer> result = feaa.getAccounts(1);
        assertTrue(result.isEmpty());

        //valid addAccount
        feaa.addAccount(1, 11,"Account 1", 100,50, "111", "1@");
        result = feaa.getAccounts(2);
        assertTrue(result.isEmpty());
    }
    @Test
    public void testGetAccounts_ClientID_Valid() {
        FEAAFacade feaa = setupGetAccounts_ClientID();
        //valid addAccount
        feaa.addAccount(1, 11,"Account 1", 100,50, "111", "1@");
        feaa.addAccount(2, 22, "Account 2", 100,50, "222", "2@");
        feaa.addAccount(3, 22,"Account 3", 100,50, "333", "3@");

        //verify 5 accounts were added
        List<Integer> result1 = feaa.getAccounts(11);
        List<Integer> result2 = feaa.getAccounts(22);
        assertEquals(1,result1.size());
        assertEquals(2,result2.size());
        assertTrue(result1.contains(1));
        assertTrue(result2.contains(2));
        assertTrue(result2.contains(3));
    }
    @Test
    public void testGetAccounts_ClientID_NullClientOrAuthProvider() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        //both null
        try {
            feaa.getAccounts(1);
            fail("Should throw exception when no providers are set");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }

        //null auth only
        FEAAFacade feaa1 = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        feaa1.setClientProvider(clientProvider);
        try {
            feaa1.getAccounts(1);
            fail("Should throw exception when authProvider is null");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }

        //null client only
        FEAAFacade feaa2 = new FEAAFacadeImpl();
        AuthModule authProvider = mock(AuthModule.class);
        when(authProvider.login(anyString(),anyString())).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);
        feaa2.setAuthProvider(authProvider);
        feaa2.login("username","password");
        try {
            feaa2.getAccounts(1);
            fail("Should throw exception when clientProvider is null");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testGetAccounts_ClientID_SecurityException() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        feaa.setClientProvider(clientProvider);

        AuthModule authProvider = mock(AuthModule.class);
        when(authProvider.login(anyString(),anyString())).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);
        feaa.setAuthProvider(authProvider);

        //not logged in
        try {
            feaa.getAccounts(1);
        } catch (Exception e) {
            assertTrue(e instanceof SecurityException);
        }

        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(false);
        feaa.login("username", "password");
        //logged in but not authenticated
        try {
            feaa.getAccounts(1);
        } catch (Exception e) {
            assertTrue(e instanceof SecurityException);
        }
    }
    @Test
    public void testGetAccounts_ClientID_NoMatchingID() {
        FEAAFacade feaa = setupGetAccounts_ClientID();
        //ID 5 is setup to not match a client ID
        try {
            feaa.getAccounts(5);
            fail("Should throw exception if client Id is not in client list");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testGetAccounts_ClientID_IllegalID() {
        FEAAFacade feaa = setupGetAccounts_ClientID();
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

    //setup a Facade with accounts to be tested
    private FEAAFacade setupAccounts() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        Client mockedClient = mock(Client.class);
        when(clientProvider.findOne(anyInt())).thenReturn(mockedClient); //always return a Client when findOne() is called
        when(clientProvider.findOne(5)).thenReturn(null); //except for ID 5, which is unmatched
        //mock client
        when(clientProvider.findOne(anyInt())).thenReturn(mockedClient);
        when(mockedClient.getFirstName()).thenReturn("first");
        when(mockedClient.getLastName()).thenReturn("last");
        AuthModule authProvider = mock(AuthModule.class);
        feaa.setAuthProvider(authProvider);
        when(authProvider.login(anyString(), anyString())).thenReturn(mock(AuthToken.class)); //always successfully login
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true); //always successfully authenticate
        assertTrue(feaa.login("username", "password"));
        feaa.setClientProvider(clientProvider);

        PrintService printProvider = mock(PrintService.class);
        EmailService emailProvider = mock(EmailService.class);
        FaxService faxProvider = mock(FaxService.class);
        ReportFacade reportProvider = mock(ReportFacade.class);
        //mock reportFacade
        when(reportProvider.makeReport(any(AuthToken.class), anyInt(), anyString(), anyString(), anyInt())).thenReturn(BigDecimal.ONE);
        when(reportProvider.getLifetimeCommission(any(AuthToken.class), anyInt())).thenReturn(BigDecimal.ONE);

        feaa.setPrintProvider(printProvider);
        feaa.setFaxProvider(faxProvider);
        feaa.setEmailProvider(emailProvider);
        feaa.setReportingProvider(reportProvider);

        feaa.addAccount(1, 11,"Account 1", 100,50, "111", "1@");
        feaa.addAccount(2, 22, "Account 2", 50,100, "222", "2@");
        feaa.addAccount(3, 22,"Account 3", 10000,0, "333", "3@");

        return feaa;
    }

    //getAccountName
    @Test
    public void testGetAccountName_IllegalID() {
        FEAAFacade feaa = setupAccounts();
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
        try {
            feaa.getAccountName(5);
            fail("Should throw exception if ID is not found");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testGetAccountName_NoProvider() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        //both null
        try {
            feaa.getAccountName(1);
            fail("Should throw exception when no providers are set");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }

        //null auth only
        FEAAFacade feaa1 = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        feaa1.setClientProvider(clientProvider);
        try {
            feaa1.getAccountName(1);
            fail("Should throw exception when authProvider is null");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }

        //null client only
        FEAAFacade feaa2 = new FEAAFacadeImpl();
        AuthModule authProvider = mock(AuthModule.class);
        when(authProvider.login(anyString(),anyString())).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);
        feaa2.setAuthProvider(authProvider);
        feaa2.login("username","password");
        try {
            feaa2.getAccountName(1);
            fail("Should throw exception when clientProvider is null");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testGetAccountName_SecurityException() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        feaa.setClientProvider(clientProvider);

        AuthModule authProvider = mock(AuthModule.class);
        when(authProvider.login(anyString(),anyString())).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);
        feaa.setAuthProvider(authProvider);

        //not logged in
        try {
            feaa.getAccountName(1);
        } catch (Exception e) {
            assertTrue(e instanceof SecurityException);
        }

        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(false);
        feaa.login("username", "password");
        //logged in but not authenticated
        try {
            feaa.getAccountName(1);
        } catch (Exception e) {
            assertTrue(e instanceof SecurityException);
        }
    }
    @Test
    public void testGetAccountName_Valid() {
        FEAAFacade feaa = setupAccounts();
        String account1 = "Account 1";
        String account2 = "Account 2";
        String account3 = "Account 3";

        assertEquals(account1, feaa.getAccountName(1));
        assertEquals(account2, feaa.getAccountName(2));
        assertEquals(account3, feaa.getAccountName(3));
    }

    //getAccountBalance
    @Test
    public void testGetAccountBalance_IllegalID() {
        FEAAFacade feaa = setupAccounts();
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
        try {
            feaa.getAccountBalance(5);
            fail("Should throw exception if ID is not found");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testGetAccountBalance_NoProvider() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        //both null
        try {
            feaa.getAccountBalance(1);
            fail("Should throw exception when no providers are set");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }

        //null auth only
        FEAAFacade feaa1 = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        feaa1.setClientProvider(clientProvider);
        try {
            feaa1.getAccountBalance(1);
            fail("Should throw exception when authProvider is null");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }

        //null client only
        FEAAFacade feaa2 = new FEAAFacadeImpl();
        AuthModule authProvider = mock(AuthModule.class);
        when(authProvider.login(anyString(),anyString())).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);
        feaa2.setAuthProvider(authProvider);
        feaa2.login("username","password");
        try {
            feaa2.getAccountBalance(1);
            fail("Should throw exception when clientProvider is null");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testGetAccountBalance_SecurityException() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        feaa.setClientProvider(clientProvider);

        AuthModule authProvider = mock(AuthModule.class);
        when(authProvider.login(anyString(),anyString())).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);
        feaa.setAuthProvider(authProvider);

        //not logged in
        try {
            feaa.getAccountBalance(1);
        } catch (Exception e) {
            assertTrue(e instanceof SecurityException);
        }

        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(false);
        feaa.login("username", "password");
        //logged in but not authenticated
        try {
            feaa.getAccountBalance(1);
        } catch (Exception e) {
            assertTrue(e instanceof SecurityException);
        }
    }
    @Test
    public void testGetAccountBalance_Valid() {
        FEAAFacade feaa = setupAccounts();

        assertEquals(50, feaa.getAccountBalance(1));
        assertEquals(-50, feaa.getAccountBalance(2));
        assertEquals(10000, feaa.getAccountBalance(3));
    }
    @Test
    public void testGetAccountBalance_AfterChanged() {
        FEAAFacade feaa = setupAccounts();

        assertEquals(50, feaa.getAccountBalance(1));

        //change incomings and outgoings
        feaa.setAccountOutgoings(1,100);
        feaa.setAccountIncomings(1,200);
        assertEquals(100,feaa.getAccountBalance(1));
    }

    //getAccountIncomings
    @Test
    public void testGetAccountIncomings_IllegalID() {
        FEAAFacade feaa = setupAccounts();
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
        try {
            feaa.getAccountIncomings(5);
            fail("Should throw exception if ID is not found");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testGetAccountIncomings_NoProvider() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        //both null
        try {
            feaa.getAccountIncomings(1);
            fail("Should throw exception when no providers are set");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }

        //null auth only
        FEAAFacade feaa1 = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        feaa1.setClientProvider(clientProvider);
        try {
            feaa1.getAccountIncomings(1);
            fail("Should throw exception when authProvider is null");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }

        //null client only
        FEAAFacade feaa2 = new FEAAFacadeImpl();
        AuthModule authProvider = mock(AuthModule.class);
        when(authProvider.login(anyString(),anyString())).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);
        feaa2.setAuthProvider(authProvider);
        feaa2.login("username","password");
        try {
            feaa2.getAccountIncomings(1);
            fail("Should throw exception when clientProvider is null");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testGetAccountIncomings_SecurityException() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        feaa.setClientProvider(clientProvider);

        AuthModule authProvider = mock(AuthModule.class);
        when(authProvider.login(anyString(),anyString())).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);
        feaa.setAuthProvider(authProvider);

        //not logged in
        try {
            feaa.getAccountIncomings(1);
        } catch (Exception e) {
            assertTrue(e instanceof SecurityException);
        }

        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(false);
        feaa.login("username", "password");
        //logged in but not authenticated
        try {
            feaa.getAccountIncomings(1);
        } catch (Exception e) {
            assertTrue(e instanceof SecurityException);
        }
    }
    @Test
    public void testGetAccountIncomings_Valid() {
        FEAAFacade feaa = setupAccounts();

        assertEquals(100, feaa.getAccountIncomings(1));
        assertEquals(50, feaa.getAccountIncomings(2));
        assertEquals(10000, feaa.getAccountIncomings(3));
    }
    @Test
    public void testGetAccountIncomings_AfterChanged() {
        FEAAFacade feaa = setupAccounts();

        assertEquals(100, feaa.getAccountIncomings(1));

        //change incomings and outgoings
        feaa.setAccountIncomings(1,200);
        assertEquals(200,feaa.getAccountIncomings(1));
    }

    //getAccountOutgoings
    @Test
    public void testGetAccountOutgoings_IllegalID() {
        FEAAFacade feaa = setupAccounts();
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
        try {
            feaa.getAccountOutgoings(5);
            fail("Should throw exception if ID is not found");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testGetAccountOutgoings_NoProvider() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        //both null
        try {
            feaa.getAccountOutgoings(1);
            fail("Should throw exception when no providers are set");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }

        //null auth only
        FEAAFacade feaa1 = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        feaa1.setClientProvider(clientProvider);
        try {
            feaa1.getAccountOutgoings(1);
            fail("Should throw exception when authProvider is null");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }

        //null client only
        FEAAFacade feaa2 = new FEAAFacadeImpl();
        AuthModule authProvider = mock(AuthModule.class);
        when(authProvider.login(anyString(),anyString())).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);
        feaa2.setAuthProvider(authProvider);
        feaa2.login("username","password");
        try {
            feaa2.getAccountOutgoings(1);
            fail("Should throw exception when clientProvider is null");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testGetAccountOutgoings_SecurityException() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        feaa.setClientProvider(clientProvider);

        AuthModule authProvider = mock(AuthModule.class);
        when(authProvider.login(anyString(),anyString())).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);
        feaa.setAuthProvider(authProvider);

        //not logged in
        try {
            feaa.getAccountOutgoings(1);
        } catch (Exception e) {
            assertTrue(e instanceof SecurityException);
        }

        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(false);
        feaa.login("username", "password");
        //logged in but not authenticated
        try {
            feaa.getAccountOutgoings(1);
        } catch (Exception e) {
            assertTrue(e instanceof SecurityException);
        }
    }
    @Test
    public void testGetAccountOutgoings_Valid() {
        FEAAFacade feaa = setupAccounts();

        assertEquals(50, feaa.getAccountOutgoings(1));
        assertEquals(100, feaa.getAccountOutgoings(2));
        assertEquals(0, feaa.getAccountOutgoings(3));
    }
    @Test
    public void testGetAccountOutgoings_AfterChanged() {
        FEAAFacade feaa = setupAccounts();

        assertEquals(50, feaa.getAccountOutgoings(1));

        //change incomings and outgoings
        feaa.setAccountOutgoings(1,100);
        assertEquals(100,feaa.getAccountOutgoings(1));
    }

    //setAccountIncomings
    @Test
    public void testSetAccountIncomings_IllegalID() {
        FEAAFacade feaa = setupAccounts();
        try {
            feaa.setAccountIncomings(0, 100);
            fail("Should throw exception if ID is zero");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            feaa.setAccountIncomings(-1, 100);
            fail("Should throw exception if ID is negative");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            feaa.setAccountIncomings(5, 100);
            fail("Should throw exception if ID is not found");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testSetAccountIncomings_NoProvider() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        //both null
        try {
            feaa.setAccountIncomings(1, 100);
            fail("Should throw exception when no providers are set");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }

        //null auth only
        FEAAFacade feaa1 = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        feaa1.setClientProvider(clientProvider);
        try {
            feaa1.setAccountIncomings(1,100);
            fail("Should throw exception when authProvider is null");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }

        //null client only
        FEAAFacade feaa2 = new FEAAFacadeImpl();
        AuthModule authProvider = mock(AuthModule.class);
        when(authProvider.login(anyString(),anyString())).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);
        feaa2.setAuthProvider(authProvider);
        feaa2.login("username","password");
        try {
            feaa2.setAccountIncomings(1, 100);
            fail("Should throw exception when clientProvider is null");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testSetAccountIncomings_SecurityException() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        feaa.setClientProvider(clientProvider);

        AuthModule authProvider = mock(AuthModule.class);
        when(authProvider.login(anyString(),anyString())).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);
        feaa.setAuthProvider(authProvider);

        //not logged in
        try {
            feaa.setAccountIncomings(1, 100);
        } catch (Exception e) {
            assertTrue(e instanceof SecurityException);
        }

        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(false);
        feaa.login("username", "password");
        //logged in but not authenticated
        try {
            feaa.setAccountIncomings(1, 100);
        } catch (Exception e) {
            assertTrue(e instanceof SecurityException);
        }
    }
    @Test
    public void testSetAccountIncomings_Valid() {
        FEAAFacade feaa = setupAccounts();

        assertEquals(100, feaa.getAccountIncomings(1));

        //change incomings and outgoings
        feaa.setAccountIncomings(1,200);
        assertEquals(200,feaa.getAccountIncomings(1));
    }

    //setAccountOutgoings
    @Test
    public void testSetAccountOutgoings_IllegalID() {
        FEAAFacade feaa = setupAccounts();
        try {
            feaa.setAccountOutgoings(0, 100);
            fail("Should throw exception if ID is zero");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            feaa.setAccountOutgoings(-1, 100);
            fail("Should throw exception if ID is negative");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            feaa.setAccountOutgoings(5, 100);
            fail("Should throw exception if ID is not found");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testSetAccountOutgoings_NoProvider() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        //both null
        try {
            feaa.setAccountOutgoings(1, 100);
            fail("Should throw exception when no providers are set");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }

        //null auth only
        FEAAFacade feaa1 = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        feaa1.setClientProvider(clientProvider);
        try {
            feaa1.setAccountOutgoings(1, 100);
            fail("Should throw exception when authProvider is null");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }

        //null client only
        FEAAFacade feaa2 = new FEAAFacadeImpl();
        AuthModule authProvider = mock(AuthModule.class);
        when(authProvider.login(anyString(),anyString())).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);
        feaa2.setAuthProvider(authProvider);
        feaa2.login("username","password");
        try {
            feaa2.setAccountOutgoings(1, 100);
            fail("Should throw exception when clientProvider is null");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testSetAccountOutgoings_SecurityException() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        feaa.setClientProvider(clientProvider);

        AuthModule authProvider = mock(AuthModule.class);
        when(authProvider.login(anyString(),anyString())).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);
        feaa.setAuthProvider(authProvider);

        //not logged in
        try {
            feaa.setAccountOutgoings(1, 100);
        } catch (Exception e) {
            assertTrue(e instanceof SecurityException);
        }

        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(false);
        feaa.login("username", "password");
        //logged in but not authenticated
        try {
            feaa.setAccountOutgoings(1, 100);
        } catch (Exception e) {
            assertTrue(e instanceof SecurityException);
        }
    }
    @Test
    public void testSetAccountOutgoings_Valid() {
        FEAAFacade feaa = setupAccounts();

        assertEquals(50, feaa.getAccountOutgoings(1));

        //change incomings and outgoings
        feaa.setAccountOutgoings(1,100);
        assertEquals(100,feaa.getAccountOutgoings(1));
    }

    //setReportPreferences
    @Test
    public void testSetReportPreferences_Valid() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        //mocks
        ClientList clientProvider = mock(ClientList.class);
        Client mockedClient = mock(Client.class);
        AuthModule authProvider = mock(AuthModule.class);

        PrintService printProvider = mock(PrintService.class);
        EmailService emailProvider = mock(EmailService.class);
        FaxService faxProvider = mock(FaxService.class);
        ReportFacade reportProvider = mock(ReportFacade.class);

        feaa.setClientProvider(clientProvider);
        feaa.setAuthProvider(authProvider);

        feaa.setPrintProvider(printProvider);
        feaa.setFaxProvider(faxProvider);
        feaa.setEmailProvider(emailProvider);
        feaa.setReportingProvider(reportProvider);

        //mock client
        when(clientProvider.findOne(anyInt())).thenReturn(mockedClient);
        when(mockedClient.getFirstName()).thenReturn("first");
        when(mockedClient.getLastName()).thenReturn("last");

        //mock reportFacade
        when(reportProvider.makeReport(any(AuthToken.class), anyInt(), anyString(), anyString(), anyInt())).thenReturn(BigDecimal.ONE);
        when(reportProvider.getLifetimeCommission(any(AuthToken.class), anyInt())).thenReturn(BigDecimal.ONE);

        //mock auth
        when(authProvider.login("username", "password")).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);
        assertTrue(feaa.login("username", "password"));

        //add account for report
        feaa.addAccount(1, 1, "Account 1", 100, 50, "111", "email@gmail.com");

        //report made for all 3
        feaa.setReportPreferences(1, true, true, true);
        feaa.makeReport(1);
        verify(reportProvider, times(1)).makeReport(any(AuthToken.class), anyInt(), anyString(), anyString(), anyInt());
        verify(printProvider).printReport(any(AuthToken.class), anyString());
        verify(emailProvider).printReport(any(AuthToken.class), eq("email@gmail.com"), anyString());
        verify(faxProvider).faxReport(any(AuthToken.class), eq("111"), anyString());

        //report made for 2
        feaa.setReportPreferences(1, true, false, true);
        feaa.makeReport(1);
        verify(reportProvider, times(2)).makeReport(any(AuthToken.class), anyInt(), anyString(), anyString(), anyInt());
        verify(emailProvider, times(2)).printReport(any(
                AuthToken.class), eq("email@gmail.com"), anyString());
        verify(faxProvider, times(2)).faxReport(any(AuthToken.class), eq("111"), anyString());

        feaa.setReportPreferences(1, true, true, false);
        feaa.makeReport(1);
        verify(reportProvider, times(3)).makeReport(any(AuthToken.class), anyInt(), anyString(), anyString(), anyInt());
        verify(emailProvider, times(3)).printReport(any(
                AuthToken.class), eq("email@gmail.com"), anyString());
        verify(printProvider, times(2)).printReport(any(AuthToken.class), anyString());

        feaa.setReportPreferences(1, false, true, true);
        feaa.makeReport(1);
        verify(reportProvider, times(4)).makeReport(any(AuthToken.class), anyInt(), anyString(), anyString(), anyInt());
        verify(printProvider, times(3)).printReport(any(
                AuthToken.class), anyString());
        verify(faxProvider, times(3)).faxReport(any(AuthToken.class), eq("111"), anyString());

        //report made for 1
        feaa.setReportPreferences(1, false, false, true);
        feaa.makeReport(1);
        verify(reportProvider, times(5)).makeReport(any(AuthToken.class), anyInt(), anyString(), anyString(), anyInt());
        verify(faxProvider, times(4)).faxReport(any(AuthToken.class), eq("111"), anyString());

        feaa.setReportPreferences(1, false, true, false);
        feaa.makeReport(1);
        verify(reportProvider, times(6)).makeReport(any(AuthToken.class), anyInt(), anyString(), anyString(), anyInt());
        verify(printProvider, times(4)).printReport(any(AuthToken.class), anyString());

        feaa.setReportPreferences(1, true, false, false);
        feaa.makeReport(1);
        verify(reportProvider, times(7)).makeReport(any(AuthToken.class), anyInt(), anyString(), anyString(), anyInt());
        verify(emailProvider, times(4)).printReport(any(AuthToken.class), eq("email@gmail.com"), anyString());

    }
    @Test
    public void testSetReportPreferences_IllegalID() {
        FEAAFacade feaa = setupAccounts();
        try {
            feaa.setReportPreferences(0, true, true, true);
            fail("Should throw exception if ID is zero");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            feaa.setReportPreferences(-1, true, true, true);
            fail("Should throw exception if ID is negative");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            feaa.setReportPreferences(5, true, true, true);
            fail("Should throw exception if ID is not found");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testSetReportPreferences_NullAuthProvider() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        feaa.setClientProvider(clientProvider);
        try {
            feaa.setReportPreferences(1,true, true, true);
            fail("Should throw exception when authProvider is null");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testSetReportPreferences_SecurityException() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        feaa.setClientProvider(clientProvider);

        AuthModule authProvider = mock(AuthModule.class);
        when(authProvider.login(anyString(),anyString())).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);
        feaa.setAuthProvider(authProvider);

        //not logged in
        try {
            feaa.setReportPreferences(1, true, true, true);
        } catch (Exception e) {
            assertTrue(e instanceof SecurityException);
        }

        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(false);
        feaa.login("username", "password");
        //logged in but not authenticated
        try {
            feaa.setReportPreferences(1, true, true, true);
        } catch (Exception e) {
            assertTrue(e instanceof SecurityException);
        }
    }

    //makeReport
    @Test
    public void testMakeReport_Valid() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        //mocks
        ClientList clientProvider = mock(ClientList.class);
        Client mockedClient = mock(Client.class);
        AuthModule authProvider = mock(AuthModule.class);

        PrintService printProvider = mock(PrintService.class);
        EmailService emailProvider = mock(EmailService.class);
        FaxService faxProvider = mock(FaxService.class);
        ReportFacade reportProvider = mock(ReportFacade.class);


        feaa.setClientProvider(clientProvider);
        feaa.setAuthProvider(authProvider);

        feaa.setPrintProvider(printProvider);
        feaa.setFaxProvider(faxProvider);
        feaa.setEmailProvider(emailProvider);
        feaa.setReportingProvider(reportProvider);

        //mock client
        when(clientProvider.findOne(anyInt())).thenReturn(mockedClient);
        when(mockedClient.getFirstName()).thenReturn("first");
        when(mockedClient.getLastName()).thenReturn("last");

        //mock reportFacade
        when(reportProvider.makeReport(any(AuthToken.class), anyInt(), anyString(), anyString(), anyInt())).thenReturn(BigDecimal.ONE); //set cost of commission for 1 report to 1
        when(reportProvider.getLifetimeCommission(any(AuthToken.class), anyInt())).thenReturn(BigDecimal.TEN);

        //mock auth
        when(authProvider.login("username", "password")).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);
        assertTrue(feaa.login("username", "password"));

        //add account for report
        feaa.addAccount(1, 1, "Account 1", 100, 50, "111", "email@gmail.com");

        //report made for all 3
        feaa.setReportPreferences(1, true, true, true);
        assertEquals(1,feaa.makeReport(1));
        verify(reportProvider, times(1)).makeReport(any(AuthToken.class), anyInt(), anyString(), anyString(), anyInt());
        verify(printProvider).printReport(any(AuthToken.class), anyString());
        verify(emailProvider).printReport(any(AuthToken.class), eq("email@gmail.com"), anyString());
        verify(faxProvider).faxReport(any(AuthToken.class), eq("111"), anyString());

        //report made for 2
        feaa.setReportPreferences(1, true, false, true);
        assertEquals(1,feaa.makeReport(1));
        verify(reportProvider, times(2)).makeReport(any(AuthToken.class), anyInt(), anyString(), anyString(), anyInt());
        verify(emailProvider, times(2)).printReport(any(
                AuthToken.class), eq("email@gmail.com"), anyString());
        verify(faxProvider, times(2)).faxReport(any(AuthToken.class), eq("111"), anyString());

        //report made for 1
        feaa.setReportPreferences(1, false, false, true);
        assertEquals(1,feaa.makeReport(1));
        verify(reportProvider, times(3)).makeReport(any(AuthToken.class), anyInt(), anyString(), anyString(), anyInt());
        verify(faxProvider, times(3)).faxReport(any(AuthToken.class), eq("111"), anyString());


        when(reportProvider.makeReport(any(AuthToken.class), anyInt(), anyString(), anyString(), anyInt())).thenReturn(BigDecimal.valueOf(.49)); //set cost of commission for 1 report to 1
        assertEquals(0,feaa.makeReport(1));
        when(reportProvider.makeReport(any(AuthToken.class), anyInt(), anyString(), anyString(), anyInt())).thenReturn(BigDecimal.valueOf(.50)); //set cost of commission for 1 report to 1
        assertEquals(1,feaa.makeReport(1));
        when(reportProvider.makeReport(any(AuthToken.class), anyInt(), anyString(), anyString(), anyInt())).thenReturn(BigDecimal.valueOf(10.50)); //set cost of commission for 1 report to 1
        assertEquals(11,feaa.makeReport(1));
    }
    @Test
    public void testMakeReport_IllegalID() {
        FEAAFacade feaa = setupAccounts();
        try {
            feaa.makeReport(0);
            fail("Should throw exception if ID is zero");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            feaa.makeReport(-1);
            fail("Should throw exception if ID is negative");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }
    @Test
    public void testMakeReport_NullAuthProvider() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        feaa.setClientProvider(clientProvider);
        try {
            feaa.makeReport(1);
            fail("Should throw exception when authProvider is null");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testMakeReport_NullReportProvider() {
        FEAAFacade feaa = setupAccounts();
        //email
        feaa.setEmailProvider(null);
        try {
            feaa.setReportPreferences(1,true, false, false);
            feaa.makeReport(1);
            fail("Should throw exception when preferred channel provider is null");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
        //fax
        feaa.setFaxProvider(null);
        try {
            feaa.setReportPreferences(1,false, false, true);
            feaa.makeReport(1);
            fail("Should throw exception when preferred channel provider is null");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
        //print
        feaa.setPrintProvider(null);
        try {
            feaa.setReportPreferences(1,false, true, false);
            feaa.makeReport(1);
            fail("Should throw exception when preferred channel provider is null");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testMakeReport_SecurityException() {
        FEAAFacade feaa = setupAccounts();
        AuthModule authProvider = mock(AuthModule.class);
        feaa.setReportPreferences(1, true, true, true);

        feaa.logout();
        //not logged in
        try {
            feaa.makeReport(1);
        } catch (SecurityException e) {
            assertTrue(true);
        }

        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(false);
        feaa.login("username", "password");
        //logged in but not authenticated
        try {
            feaa.makeReport(1);
        } catch (SecurityException e) {
            assertTrue(true);
        }
    }

    //getTotalLifeTimeCommission
    //makeReport
    @Test
    public void testGetTotalLifetimeCommission_Valid() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        //mocks
        ClientList clientProvider = mock(ClientList.class);
        Client mockedClient = mock(Client.class);
        AuthModule authProvider = mock(AuthModule.class);

        PrintService printProvider = mock(PrintService.class);
        EmailService emailProvider = mock(EmailService.class);
        FaxService faxProvider = mock(FaxService.class);
        ReportFacade reportProvider = mock(ReportFacade.class);


        feaa.setClientProvider(clientProvider);
        feaa.setAuthProvider(authProvider);

        feaa.setPrintProvider(printProvider);
        feaa.setFaxProvider(faxProvider);
        feaa.setEmailProvider(emailProvider);
        feaa.setReportingProvider(reportProvider);

        //mock client
        when(clientProvider.findOne(anyInt())).thenReturn(mockedClient);
        when(mockedClient.getFirstName()).thenReturn("first");
        when(mockedClient.getLastName()).thenReturn("last");

        //mock reportFacade
        when(reportProvider.makeReport(any(AuthToken.class), anyInt(), anyString(), anyString(), anyInt())).thenReturn(BigDecimal.ONE); //set cost of commission for 1 report to 1

        //mock auth
        when(authProvider.login("username", "password")).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);
        assertTrue(feaa.login("username", "password"));

        //add account for report
        feaa.addAccount(1, 1, "Account 1", 100, 50, "111", "email@gmail.com");

        when(reportProvider.getLifetimeCommission(any(AuthToken.class), anyInt())).thenReturn(BigDecimal.TEN);
        assertEquals(10, feaa.getTotalLifetimeCommission(1));

        when(reportProvider.getLifetimeCommission(any(AuthToken.class), anyInt())).thenReturn(BigDecimal.valueOf(.00000001));
        assertEquals(1, feaa.getTotalLifetimeCommission(1));

        when(reportProvider.getLifetimeCommission(any(AuthToken.class), anyInt())).thenReturn(BigDecimal.valueOf(0));
        assertEquals(0, feaa.getTotalLifetimeCommission(1));

        when(reportProvider.getLifetimeCommission(any(AuthToken.class), anyInt())).thenReturn(BigDecimal.valueOf(1.00000001));
        assertEquals(1, feaa.getTotalLifetimeCommission(1));

        when(reportProvider.getLifetimeCommission(any(AuthToken.class), anyInt())).thenReturn(BigDecimal.valueOf(1.5));
        assertEquals(2, feaa.getTotalLifetimeCommission(1));

        when(reportProvider.getLifetimeCommission(any(AuthToken.class), anyInt())).thenReturn(BigDecimal.valueOf(0.5));
        assertEquals(1, feaa.getTotalLifetimeCommission(1));
    }
    @Test
    public void testTotalLifetimeCommission_IllegalID() {
        FEAAFacade feaa = setupAccounts();
        try {
            feaa.getTotalLifetimeCommission(0);
            fail("Should throw exception if ID is zero");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            feaa.getTotalLifetimeCommission(-1);
            fail("Should throw exception if ID is negative");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }
    @Test
    public void testTotalLifetimeCommission_NullAuthProvider() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        ClientList clientProvider = mock(ClientList.class);
        feaa.setClientProvider(clientProvider);
        try {
            feaa.getTotalLifetimeCommission(1);
            fail("Should throw exception when authProvider is null");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }
    @Test
    public void testTotalLifetimeCommission_SecurityException() {
        FEAAFacade feaa = setupAccounts();
        AuthModule authProvider = mock(AuthModule.class);

        feaa.logout();
        //not logged in
        try {
            feaa.getTotalLifetimeCommission(1);
        } catch (SecurityException e) {
            assertTrue(true);
        }

        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(false);
        feaa.login("username", "password");
        //logged in but not authenticated
        try {
            feaa.getTotalLifetimeCommission(1);
        } catch (SecurityException e) {
            assertTrue(true);
        }
    }


    //login
    @Test
    public void testLogin_IllegalUsername() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        AuthModule authProvider = mock(AuthModule.class);
        feaa.setAuthProvider(authProvider);
        try {
            feaa.login(null, "password");
            fail("Username may not be null");
        } catch(IllegalArgumentException e) {
            assertTrue(true);
        }
        try {
            feaa.login("", "password");
            fail("Username may not be empty");
        } catch(IllegalArgumentException e) {
            assertTrue(true);
        }
    }
    @Test
    public void testLogin_IllegalPassword() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        AuthModule authProvider = mock(AuthModule.class);
        feaa.setAuthProvider(authProvider);
        try {
            feaa.login("username", null);
            fail("password may not be null");
        } catch(IllegalArgumentException e) {
            assertTrue(true);
        }
        try {
            feaa.login("username", "");
            fail("password may not be empty");
        } catch(IllegalArgumentException e) {
            assertTrue(true);
        }
    }
    @Test
    public void testLogin_IllegalState() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        AuthModule authProvider = mock(AuthModule.class);
        try {
            feaa.login("username", "password");
            fail("Should throw Illegal State Exception when authProvider is not set");
        } catch(IllegalStateException e){
            assertTrue(true);
        }
        feaa.setAuthProvider(null);
        try {
            feaa.login("username", "password");
            fail("Should throw Illegal State Exception when authProvider is not set");
        } catch(IllegalStateException e){
            assertTrue(true);
        }
        feaa.setAuthProvider(authProvider);
        when(authProvider.login(anyString(), anyString())).thenReturn(mock(AuthToken.class));//login is now valid
        feaa.login("username", "password");
        try {
            feaa.login("username", "password");
            fail("Should throw Illegal State Exception when already in logged-in state");
        } catch(IllegalStateException e){
            assertTrue(true);
        }
    }

    //logout
    @Test
    public void testLogout() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        try {
            feaa.logout();
            fail("Should throw IllegalStateException when AuthProvider set to null");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
        AuthModule authProvider = mock(AuthModule.class);
        when(authProvider.login(anyString(), anyString())).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);
        feaa.setAuthProvider(authProvider);

        try {
            feaa.logout();
            fail("Should throw IllegalStateException when already in logged-out state");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }

        feaa.login("username", "password");
        assertTrue(feaa.getAccounts().isEmpty()); //logged in state works
        feaa.logout();
        try {
            feaa.getAccounts();
            fail("Should throw SecurityException when in logged-out state");
        } catch(Exception e) {
            assertTrue(e instanceof SecurityException);
        }

        try {
            feaa.logout();
            fail("Should throw IllegalStateException when already in logged-out state");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }

    }

    //setReportingProvider
    @Test
    public void testSetReportingProvider_NormalConditions() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        //mocks
        ClientList clientProvider = mock(ClientList.class);
        AuthModule authProvider = mock(AuthModule.class);
        PrintService printProvider = mock(PrintService.class);
        EmailService emailProvider = mock(EmailService.class);
        FaxService faxProvider = mock(FaxService.class);
        ReportFacade reportProvider = mock(ReportFacade.class);
        Client mockedClient = mock(Client.class);
        feaa.setClientProvider(clientProvider);
        feaa.setAuthProvider(authProvider);
        feaa.setReportingProvider(reportProvider);
        feaa.setPrintProvider(printProvider);
        feaa.setFaxProvider(faxProvider);
        feaa.setEmailProvider(emailProvider);

        //mock client
        when(clientProvider.findOne(anyInt())).thenReturn(mockedClient);
        when(mockedClient.getFirstName()).thenReturn("first");
        when(mockedClient.getLastName()).thenReturn("last");

        //mock reportFacade
        when(reportProvider.makeReport(any(AuthToken.class), anyInt(), anyString(), anyString(), anyInt())).thenReturn(BigDecimal.ONE);
        when(reportProvider.getLifetimeCommission(any(AuthToken.class), anyInt())).thenReturn(BigDecimal.ONE);
        //mock auth
        when(authProvider.login("username", "password")).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);

        assertTrue(feaa.login("username", "password"));

        //add account for report
        feaa.addAccount(1, 1, "Account 1", 100, 50, "111", "email@gmail.com");

        //report made for all 3
        feaa.setReportPreferences(1, true, true, true);
        feaa.makeReport(1);
        verify(reportProvider, times(1)).makeReport(any(AuthToken.class), anyInt(), anyString(), anyString(), anyInt());
        verify(printProvider).printReport(any(AuthToken.class), anyString());
        verify(emailProvider).printReport(any(AuthToken.class), eq("email@gmail.com"), anyString());
        verify(faxProvider).faxReport(any(AuthToken.class), eq("111"), anyString());

        //report made for 2
        feaa.setReportPreferences(1, true, false, true);
        feaa.makeReport(1);
        verify(reportProvider, times(2)).makeReport(any(AuthToken.class), anyInt(), anyString(), anyString(), anyInt());
        verify(emailProvider, times(2)).printReport(any(
                AuthToken.class), eq("email@gmail.com"), anyString());
        verify(faxProvider, times(2)).faxReport(any(AuthToken.class), eq("111"), anyString());

        //report made for 1
        feaa.setReportPreferences(1, false, false, true);
        feaa.makeReport(1);
        verify(reportProvider, times(3)).makeReport(any(AuthToken.class), anyInt(), anyString(), anyString(), anyInt());
        verify(faxProvider, times(3)).faxReport(any(AuthToken.class), eq("111"), anyString());

        //getTotalLifeTimeCommission uses reportProvider
        feaa.getTotalLifetimeCommission(1);
        verify(reportProvider, atLeastOnce()).getLifetimeCommission(any(AuthToken.class), eq(1));
    }
    @Test
    public void testSetReportingProvider_Null() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        //mocks
        ClientList clientProvider = mock(ClientList.class);
        AuthModule authProvider = mock(AuthModule.class);
        PrintService printProvider = mock(PrintService.class);
        EmailService emailProvider = mock(EmailService.class);
        FaxService faxProvider = mock(FaxService.class);
        Client mockedClient = mock(Client.class);
        feaa.setClientProvider(clientProvider);
        feaa.setAuthProvider(authProvider);
        feaa.setPrintProvider(printProvider);
        feaa.setFaxProvider(faxProvider);
        feaa.setEmailProvider(emailProvider);

        feaa.setReportingProvider(null);

        //mock client
        when(clientProvider.findOne(anyInt())).thenReturn(mockedClient);
        when(mockedClient.getFirstName()).thenReturn("first");
        when(mockedClient.getLastName()).thenReturn("last");

        //mock auth
        when(authProvider.login("username", "password")).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);

        assertTrue(feaa.login("username", "password"));

        //add account for report
        feaa.addAccount(1, 1, "Account 1", 100, 50, "111", "email@gmail.com");

        //report for all 3
        feaa.setReportPreferences(1, true, true, true);

        try {
            feaa.makeReport(1);
            fail("Can't create report when set to null");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }


        //report for 2
        feaa.setReportPreferences(1, true, false, true);
        try {
            feaa.makeReport(1);
            fail("Can't create report when set to null");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }

        //report for 1
        feaa.setReportPreferences(1, false, false, true);
        try {
            feaa.makeReport(1);
            fail("Can't create report when set to null");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }

        //getTotalLifeTimeCommission uses reportProvider
        try {
            feaa.getTotalLifetimeCommission(1);
            fail("Can't get total lifetime commission without reporting provider");
        } catch(Exception e) {
            assertTrue(e instanceof NullPointerException);
        }
    }

    //setPrintProvider
    @Test
    public void testSetPrintProvider_NormalConditions() {
    FEAAFacade feaa = new FEAAFacadeImpl();
    //mocks
    ClientList clientProvider = mock(ClientList.class);
    AuthModule authProvider = mock(AuthModule.class);
    PrintService printProvider = mock(PrintService.class);
    ReportFacade reportProvider = mock(ReportFacade.class);
    Client mockedClient = mock(Client.class);
    feaa.setClientProvider(clientProvider);
    feaa.setAuthProvider(authProvider);
    feaa.setReportingProvider(reportProvider);
    feaa.setPrintProvider(printProvider);

    //mock client
    when(clientProvider.findOne(anyInt())).thenReturn(mockedClient);
    when(mockedClient.getFirstName()).thenReturn("first");
    when(mockedClient.getLastName()).thenReturn("last");

    //mock reportFacade
    when(reportProvider.makeReport(any(AuthToken.class), anyInt(), anyString(), anyString(), anyInt())).thenReturn(BigDecimal.ONE);
    //mock auth
    when(authProvider.login("username", "password")).thenReturn(mock(AuthToken.class));
    when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);

    assertTrue(feaa.login("username", "password"));

    //add account for report
    feaa.addAccount(1, 1, "Account 1", 100, 50, "111", "email@gmail.com");
    //get print report
    assertEquals(50, feaa.getAccountBalance(1));
    feaa.setReportPreferences(1, false, true, false);
    feaa.makeReport(1);

    //verify that printProvider prints report
    verify(printProvider).printReport(any(AuthToken.class), anyString());
}
    @Test
    public void testSetPrintProvider_Null() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        //mocks
        ClientList clientProvider = mock(ClientList.class);
        AuthModule authProvider = mock(AuthModule.class);
        ReportFacade reportProvider = mock(ReportFacade.class);
        Client mockedClient = mock(Client.class);
        feaa.setClientProvider(clientProvider);
        feaa.setAuthProvider(authProvider);
        feaa.setReportingProvider(reportProvider);
        //set printProvider to null
        feaa.setPrintProvider(null);

        //mock client
        when(clientProvider.findOne(anyInt())).thenReturn(mockedClient);
        when(mockedClient.getFirstName()).thenReturn("first");
        when(mockedClient.getLastName()).thenReturn("last");

        //mock reportFacade
        when(reportProvider.makeReport(any(AuthToken.class), anyInt(), anyString(), anyString(), anyInt())).thenReturn(BigDecimal.ONE);

        //mock auth
        when(authProvider.login("username", "password")).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);

        assertTrue(feaa.login("username", "password"));

        //add account for report
        feaa.addAccount(1, 1, "Account 1", 100, 50, "111", "email@gmail.com");
        //get print report
        assertEquals(50, feaa.getAccountBalance(1));
        feaa.setReportPreferences(1, false, true, false);

        //can't make report with null print provider
        try {
            feaa.makeReport(1);
            fail("Can't make report with null Print Provider");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }

    //setFaxProvider
    @Test
    public void testSetFaxProvider_NormalConditions() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        //mocks
        ClientList clientProvider = mock(ClientList.class);
        AuthModule authProvider = mock(AuthModule.class);
        FaxService faxProvider = mock(FaxService.class);
        ReportFacade reportProvider = mock(ReportFacade.class);
        Client mockedClient = mock(Client.class);
        feaa.setClientProvider(clientProvider);
        feaa.setAuthProvider(authProvider);
        feaa.setReportingProvider(reportProvider);
        feaa.setFaxProvider(faxProvider);

        //mock client
        when(clientProvider.findOne(anyInt())).thenReturn(mockedClient);
        when(mockedClient.getFirstName()).thenReturn("first");
        when(mockedClient.getLastName()).thenReturn("last");

        //mock reportFacade
        when(reportProvider.makeReport(any(AuthToken.class), anyInt(), anyString(), anyString(), anyInt())).thenReturn(BigDecimal.ONE);
        //mock auth
        when(authProvider.login("username", "password")).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);

        assertTrue(feaa.login("username", "password"));

        //add account for report
        feaa.addAccount(1, 1, "Account 1", 100, 50, "111", "email@gmail.com");
        //get fax report
        assertEquals(50, feaa.getAccountBalance(1));
        feaa.setReportPreferences(1, false, false, true);
        feaa.makeReport(1);

        //verify that faxProvider prints report
        verify(faxProvider).faxReport(any(AuthToken.class), eq("111"), anyString());
    }
    @Test
    public void testSetFaxProvider_Null() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        //mocks
        ClientList clientProvider = mock(ClientList.class);
        AuthModule authProvider = mock(AuthModule.class);
        ReportFacade reportProvider = mock(ReportFacade.class);
        Client mockedClient = mock(Client.class);
        feaa.setClientProvider(clientProvider);
        feaa.setAuthProvider(authProvider);
        feaa.setReportingProvider(reportProvider);
        //set faxProvider to null
        feaa.setFaxProvider(null);

        //mock client
        when(clientProvider.findOne(anyInt())).thenReturn(mockedClient);
        when(mockedClient.getFirstName()).thenReturn("first");
        when(mockedClient.getLastName()).thenReturn("last");

        //mock reportFacade
        when(reportProvider.makeReport(any(AuthToken.class), anyInt(), anyString(), anyString(), anyInt())).thenReturn(BigDecimal.ONE);

        //mock auth
        when(authProvider.login("username", "password")).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);

        assertTrue(feaa.login("username", "password"));

        //add account for report
        feaa.addAccount(1, 1, "Account 1", 100, 50, "111", "email@gmail.com");
        //get fax report
        assertEquals(50, feaa.getAccountBalance(1));
        feaa.setReportPreferences(1, false, false, true);

        //can't make report with null faxProvider
        try {
            feaa.makeReport(1);
            fail("Can't make report with null Fax Provider");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }

    //setEmailProvider
    @Test
    public void testSetEmailProvider_NormalConditions() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        //mocks
        ClientList clientProvider = mock(ClientList.class);
        AuthModule authProvider = mock(AuthModule.class);
        EmailService emailProvider = mock(EmailService.class);
        ReportFacade reportProvider = mock(ReportFacade.class);
        Client mockedClient = mock(Client.class);
        feaa.setClientProvider(clientProvider);
        feaa.setAuthProvider(authProvider);
        feaa.setReportingProvider(reportProvider);
        feaa.setEmailProvider(emailProvider);

        //mock client
        when(clientProvider.findOne(anyInt())).thenReturn(mockedClient);
        when(mockedClient.getFirstName()).thenReturn("first");
        when(mockedClient.getLastName()).thenReturn("last");

        //mock reportFacade
        when(reportProvider.makeReport(any(AuthToken.class), anyInt(), anyString(), anyString(), anyInt())).thenReturn(BigDecimal.ONE);
        //mock auth
        when(authProvider.login("username", "password")).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);

        assertTrue(feaa.login("username", "password"));

        //add account for report
        feaa.addAccount(1, 1, "Account 1", 100, 50, "111", "email@gmail.com");
        //get email report
        assertEquals(50, feaa.getAccountBalance(1));
        feaa.setReportPreferences(1, true, false, false);
        feaa.makeReport(1);

        //verify that emailProvider prints report
        verify(emailProvider).printReport(any(AuthToken.class), eq("email@gmail.com"), anyString());

    }
    @Test
    public void testSetEmailProvider_Null() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        //mocks
        ClientList clientProvider = mock(ClientList.class);
        AuthModule authProvider = mock(AuthModule.class);
        ReportFacade reportProvider = mock(ReportFacade.class);
        Client mockedClient = mock(Client.class);
        feaa.setClientProvider(clientProvider);
        feaa.setAuthProvider(authProvider);
        feaa.setReportingProvider(reportProvider);
        //set emailProvider to null
        feaa.setEmailProvider(null);

        //mock client
        when(clientProvider.findOne(anyInt())).thenReturn(mockedClient);
        when(mockedClient.getFirstName()).thenReturn("first");
        when(mockedClient.getLastName()).thenReturn("last");

        //mock reportFacade
        when(reportProvider.makeReport(any(AuthToken.class), anyInt(), anyString(), anyString(), anyInt())).thenReturn(BigDecimal.ONE);

        //mock auth
        when(authProvider.login("username", "password")).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);

        assertTrue(feaa.login("username", "password"));

        //add account for report
        feaa.addAccount(1, 1, "Account 1", 100, 50, "111", "email@gmail.com");
        //get email report
        assertEquals(50, feaa.getAccountBalance(1));
        feaa.setReportPreferences(1, true, false, false);

        //can't make report with null emailProvider
        try {
            feaa.makeReport(1);
            fail("Can't make report with null Email Provider");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }

    //setAuthProvider
    @Test
    public void testSetAuthProvider_NormalConditions() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        //mocks
        ClientList clientProvider = mock(ClientList.class);
        AuthModule authProvider = mock(AuthModule.class);

        feaa.setClientProvider(clientProvider);
        feaa.setAuthProvider(authProvider);
        //mock valid login
        when(authProvider.login("username", "password")).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);
        assertTrue(feaa.login("username", "password"));

        //assert that authProvider is called
        verify(authProvider).login("username", "password");
        try {
            feaa.addClient("first", "last", "111");
        } catch (Exception e) {
            fail("No exception should be thrown when valid authProvider is set");
        }
        verify(authProvider).authenticate(any(AuthToken.class));
        feaa.logout();
    }
    @Test
    public void testSetAuthProvider_LogsOut() {
        FEAAFacadeImpl feaa = new FEAAFacadeImpl();
        //mocks
        ClientList clientProvider = mock(ClientList.class);
        AuthModule authProvider = mock(AuthModule.class);

        feaa.setClientProvider(clientProvider);
        feaa.setAuthProvider(authProvider);

        //logged-out state after setting authProvider the first time
        try {
            feaa.addClient("first", "last", "111");
        } catch(Exception e) {
            assertTrue(e instanceof SecurityException);
        }

        //mock valid login
        when(authProvider.login("username", "password")).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);
        assertTrue(feaa.login("username", "password"));

        //assert that authProvider is called
        verify(authProvider).login("username", "password");

        try {
            feaa.addClient("first", "last", "111");
        } catch (Exception e) {
            fail("No exception should be thrown when valid authProvider is set");
        }
        verify(authProvider).authenticate(any(AuthToken.class));
        //logged-out state after setting authProvider again
        feaa.setAuthProvider(authProvider);
        try {
            feaa.addClient("first2", "last2", "222");
        } catch(Exception e) {
            assertTrue(e instanceof SecurityException);
        }
    }
    @Test
    public void testSetAuthProvider_Null() {
        FEAAFacadeImpl feaa = new FEAAFacadeImpl();
        //mocks
        ClientList clientProvider = mock(ClientList.class);

        feaa.setClientProvider(clientProvider);
        try {
            feaa.setAuthProvider(null);
        } catch (Exception e) {
            fail("Exception should not be thrown when setting authProvider to null");
        }

        //can't log in
        try {
            feaa.login("username", "password");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }

        //can't use FEAA functionality
        try {
            feaa.addClient("first", "last", "111");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }

    //setClientProvider
    @Test
    public void testSetClientProvider_NewProvider_NullIDStateReset() {
        FEAAFacadeImpl feaa = new FEAAFacadeImpl();
        //mocks
        ClientList clientProvider = mock(ClientList.class);
        ClientList clientProvider2 = mock(ClientList.class);
        AuthModule authProvider = mock(AuthModule.class);
        feaa.setClientProvider(clientProvider);
        feaa.setAuthProvider(authProvider);
        //mock valid login
        when(authProvider.login("username", "password")).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);
        assertTrue(feaa.login("username", "password"));
        //clientProvider contains any client ID
        when(clientProvider.findOne(anyInt())).thenReturn(mock(Client.class));
        when(clientProvider2.findOne(anyInt())).thenReturn(mock(Client.class));
        //add account with an id
        feaa.addAccount(1, 1, "Account 1", 100,50, "1111111111", "email1@gmail.com");

        //can't be null before
        try {
            feaa.addAccount(null, 1, "Account 2", 100,500, "1111111111", "email1@gmail.com");
            fail("Can't assign with null ID after explicit ID");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
        //setClientProvider to new provider
        feaa.setClientProvider(clientProvider2);

        //can be null after (no exception thrown)
        feaa.addAccount(null, 1, "Account 2", 100,500, "1111111111", "email1@gmail.com");
    }
    @Test
    public void testSetClientProvider_NormalConditions() {
        FEAAFacadeImpl feaa = new FEAAFacadeImpl();
        //mocks
        ClientList clientProvider = mock(ClientList.class);
        AuthModule authProvider = mock(AuthModule.class);
        feaa.setClientProvider(clientProvider);
        feaa.setAuthProvider(authProvider);
        //mock valid login
        when(authProvider.login("username", "password")).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);
        assertTrue(feaa.login("username", "password"));

        //assert that clientProvider works
        feaa.addClient("first","last","1234567890");
        verify(clientProvider).addClient(anyInt(), eq("first"), eq("last"), eq("1234567890"));

    }
    @Test
    public void testSetClientProvider_NewProviderClearsAccounts() {
        FEAAFacadeImpl feaa = new FEAAFacadeImpl();
        //mocks
        ClientList clientProvider = mock(ClientList.class);
        ClientList clientProvider2 = mock(ClientList.class);
        AuthModule authProvider = mock(AuthModule.class);
        feaa.setClientProvider(clientProvider);
        feaa.setAuthProvider(authProvider);
        //mock valid login
        when(authProvider.login("username", "password")).thenReturn(mock(AuthToken.class));
        when(authProvider.authenticate(any(AuthToken.class))).thenReturn(true);
        assertTrue(feaa.login("username", "password"));
        //clientProvider contains any client ID
        when(clientProvider.findOne(anyInt())).thenReturn(mock(Client.class));
        when(clientProvider2.findOne(anyInt())).thenReturn(mock(Client.class));
        //add account with an id
        feaa.addAccount(1, 1, "Account 1", 100,50, "1111111111", "email1@gmail.com");

        //can't have same id of 1
        try {
            feaa.addAccount(1, 1, "Account 1", 100,50, "1111111111", "email1@gmail.com");
            fail("Account ID must be unique");
        } catch(Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        //setClientProvider to new provider clears accounts
        feaa.setClientProvider(clientProvider2);

        //ID can be 1 after (no exception thrown)
        feaa.addAccount(1, 1, "Account 1", 100,50, "1111111111", "email1@gmail.com");
    }
    @Test
    public void testSetClientProvider_SetToNull() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        AuthModule authProvider = mock(AuthModule.class);
        try {
            feaa.setClientProvider(null);
        } catch(Exception e){
            fail("The provider to inject may be null");
        }
        try {
            //set authProvider and login so only IllegalStateException because of null client provider can be thrown
            feaa.setAuthProvider(authProvider);
            when(authProvider.login("username", "password")).thenReturn(mock(AuthToken.class));
            assertTrue(feaa.login("username", "password"));
            feaa.addClient("first","last","1234567890");
            fail("Should throw exceptions when doing client operations with null client provider");
        } catch(Exception e){
            assertTrue(e instanceof IllegalStateException);
        }
    }
}