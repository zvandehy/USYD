import java.rmi.Naming;

public class RMIClient {
    //rmi://host:port/name
    static final String LOCAL_IP= "127.0.0.1";
    static final String RMI = "rmi://";

    public static void main(String args[]) {
        try {
            String name = "RMICounterObject";
            String host = RMI + LOCAL_IP + "/" + name;
            RemoteCounter counterServer = (RemoteCounter) Naming.lookup(host);
            System.out.println(counterServer.inc());
        }catch(Exception e){
            System.err.println(e);
        }
    }

}
