import java.rmi.*;

public interface RemoteCounter extends Remote {

    int inc() throws RemoteException;
}
