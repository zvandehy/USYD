package au.edu.sydney.pac.erp.feaa;

import au.edu.sydney.pac.erp.auth.AuthModule;
import au.edu.sydney.pac.erp.client.Client;
import au.edu.sydney.pac.erp.client.ClientList;
import au.edu.sydney.pac.erp.fax.FaxService;
import au.edu.sydney.pac.erp.print.PrintService;
import au.edu.sydney.pac.erp.reporting.ReportFacade;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.Assert.*;

public class FEAASystemQA {

    private FEAAFacade setup() {
        FEAAFacade feaa = new FEAAFacadeImpl();
        FEAATestFactory factory = new FEAATestFactory();
        AuthModule authProvider = factory.makeAuthModule();
        ClientList clientProvider = factory.makeClientModule();
        ReportFacade reportProvider = factory.makeReportFacade(authProvider);
        FaxService faxProvider = factory.makeFaxService();
        PrintService printProvider = factory.makePrintService();
        feaa.setClientProvider(clientProvider);
        feaa.setAuthProvider(authProvider);
        feaa.setReportingProvider(reportProvider);
        feaa.setFaxProvider(faxProvider);
        feaa.setPrintProvider(printProvider);
        return feaa;
    }


    @Test
    public void testAddInternationalClient() {
        FEAAFacade feaa = setup();
        feaa.login("Terry Gilliam", "hunter2");
        Client client = feaa.addClient("Zeke", "Van Dehy", "+1(720)2318709");
        client.assignDepartment("INTERNATIONAL");
        List<Client> clients = feaa.getAllClients();
        feaa.logout();

        assertEquals(1, clients.size());
        assertTrue(clients.get(0).isAssigned());
        assertEquals("INTERNATIONAL", clients.get(0).getDepartmentCode());
    }

    @Test
    public void testCreateReport() {
        FEAAFacade feaa = setup();
        feaa.login("Terry Gilliam", "hunter2");
        Client client = feaa.addClient("Zeke", "Van Dehy", "+1(720)2318709");

        feaa.addAccount(1, client.getID(), "ZekesAccount",
                1000, 250, "+1(720)2318709", "zvandehy@gmail.com");
        feaa.setReportPreferences(1, false, true, true);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        int commission = feaa.makeReport(1);

        assertEquals(commission, feaa.getTotalLifetimeCommission(1));

        assertTrue(outContent.toString().contains("Printing that report!"));
        assertTrue(outContent.toString().contains("Faxing that report!"));
        assertFalse(outContent.toString().contains("Emailing that report!"));
    }
}