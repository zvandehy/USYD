import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class RemoteCounterImpl extends UnicastRemoteObject implements RemoteCounter {
    int counter=0;
    static final String SERVER_NAME="RMICounterObject";

    public RemoteCounterImpl() throws RemoteException{}
    @Override
    public int inc() throws RemoteException {
        return ++counter;
    }
    public static void main(String args[]) {
        try {
            RemoteCounterImpl counterServer = new RemoteCounterImpl();
            Naming.rebind(SERVER_NAME, counterServer);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
