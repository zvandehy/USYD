package proxy;

public class RealDatabase implements Database {
    public void connectTo(String db) {
        System.out.println("Connecting to " + db);
    }
}
