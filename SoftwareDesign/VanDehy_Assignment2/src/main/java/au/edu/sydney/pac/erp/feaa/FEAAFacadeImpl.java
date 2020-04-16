package au.edu.sydney.pac.erp.feaa;//package au.edu.sydney.pac.erp.feaa;//package au.edu.sydney.pac.erp.feaa;
//
//import au.edu.sydney.pac.erp.auth.AuthModule;
//import au.edu.sydney.pac.erp.client.Client;
//import au.edu.sydney.pac.erp.client.ClientList;
//import au.edu.sydney.pac.erp.email.EmailService;
//import au.edu.sydney.pac.erp.fax.FaxService;
//import au.edu.sydney.pac.erp.print.PrintService;
//import au.edu.sydney.pac.erp.reporting.ReportFacade;
//
//import java.util.List;
//
//public class FEAAFacadeImpl implements FEAAFacade {
//
//    @Override
//    public void setClientProvider(ClientList provider) {
//
//    }
//
//    @Override
//    public void setAuthProvider(AuthModule provider) {
//
//    }
//
//    @Override
//    public void setEmailProvider(EmailService provider) {
//
//    }
//
//    @Override
//    public void setFaxProvider(FaxService provider) {
//
//    }
//
//    @Override
//    public void setReportingProvider(ReportFacade provider) {
//
//    }
//
//    @Override
//    public void setPrintProvider(PrintService provider) {
//
//    }
//
//    @Override
//    public Client addClient(String fName, String lName, String phoneNumber) throws SecurityException, IllegalStateException, IllegalArgumentException {
//        return null;
//    }
//
//    @Override
//    public void assignClient(int clientID, String departmentCode) throws SecurityException, IllegalStateException, IllegalArgumentException {
//
//    }
//
//    @Override
//    public List<String> viewAllClients() throws SecurityException, IllegalStateException {
//        return null;
//    }
//
//    @Override
//    public List<Client> getAllClients() throws SecurityException, IllegalStateException {
//        return null;
//    }
//
//    @Override
//    public int addAccount(Integer accountID, int clientID, String accountName, int initialIncomings, int initialOutgoings, String reportPhone, String reportEmail) throws SecurityException, IllegalStateException, IllegalArgumentException {
//        return 0;
//    }
//
//    @Override
//    public List<String> getAccounts() throws SecurityException, IllegalStateException {
//        return null;
//    }
//
//    @Override
//    public List<Integer> getAccounts(int clientID) throws SecurityException, IllegalStateException, IllegalArgumentException {
//        return null;
//    }
//
//    @Override
//    public String getAccountName(int id) throws SecurityException, IllegalStateException, IllegalArgumentException {
//        return null;
//    }
//
//    @Override
//    public int getAccountBalance(int id) throws SecurityException, IllegalStateException, IllegalArgumentException {
//        return 0;
//    }
//
//    @Override
//    public int getAccountIncomings(int id) throws SecurityException, IllegalStateException, IllegalArgumentException {
//        return 0;
//    }
//
//    @Override
//    public int getAccountOutgoings(int id) throws SecurityException, IllegalStateException, IllegalArgumentException {
//        return 0;
//    }
//
//    @Override
//    public void setAccountIncomings(int id, int incomings) throws SecurityException, IllegalStateException, IllegalArgumentException {
//
//    }
//
//    @Override
//    public void setAccountOutgoings(int id, int outgoings) throws SecurityException, IllegalStateException, IllegalArgumentException {
//
//    }
//
//    @Override
//    public void setReportPreferences(int accountID, boolean email, boolean print, boolean fax) throws SecurityException, IllegalArgumentException, IllegalStateException {
//
//    }
//
//    @Override
//    public int makeReport(int accountID) throws SecurityException, IllegalArgumentException, IllegalStateException {
//        return 0;
//    }
//
//    @Override
//    public int getTotalLifetimeCommission(int accountID) throws SecurityException, IllegalArgumentException, IllegalStateException {
//        return 0;
//    }
//
//    @Override
//    public boolean login(String userName, String password) throws IllegalArgumentException, IllegalStateException {
//        return false;
//    }
//
//    @Override
//    public void logout() throws IllegalStateException {
//
//    }
//}
