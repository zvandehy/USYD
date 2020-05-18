package proxy;

public class Client {
    public static void main(String[] args) {
        SmartProxy database = new SmartProxy(new RealDatabase());
        try {
            database.connectTo("public-db");
            database.connectTo("private-db");
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
