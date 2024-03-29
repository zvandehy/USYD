package au.edu.sydney.cpa.erp.feaa;

import au.edu.sydney.cpa.erp.ordering.Client;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

public class ClientLoadLagTest {

    private FEAAFacade facade = new FEAAFacade();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    PrintStream standard = System.out;



    @Test
    public void testLoadClients() {

        System.out.println("#clients | time");
        System.setOut(new PrintStream(outContent));
        facade.login("Terry Gilliam", "hunter2");
        int numClients = 3;
        for(int i=1;i<=numClients;i++) {
            long start = System.currentTimeMillis();
            for(int j=0;j<i; j++) {
                //gets a new client
                Client client = facade.getClient(0);//gets all 11 fields from Database
                client.getFName();//gets field from Client (no waiting)
                client.getLName();//gets field from Client (no waiting)
                client.getEmailAddress();//gets field from Client (no waiting)
                client.getPhoneNumber();//gets field from Client (no waiting)
                client.getFName();//gets field from Client (again) (no waiting)
                //only *uses* 4 fields, even though Client got all 11 from database
            }
            long end = System.currentTimeMillis();
            System.setOut(standard);
            System.out.println(i + "clients | " + (end-start) + "(milliseconds) = " + (end-start)/1000 + "(seconds)");
            System.setOut(new PrintStream(outContent));

        }
        Pattern pattern = Pattern.compile("Getting client field.done!");
        Matcher matcher = pattern.matcher(outContent.toString());
        long matches = matcher.results().count();
        //numClients = n
        //total clients = n(n+1) / 2
        //getting 11 fields for each client (and one that is already stored)
        assertEquals(((numClients * (numClients+1)/2) * 11), (int) matches);

        System.setOut(standard);
        System.out.println("Total number of fields retrieved: " + matches);
    }
}
