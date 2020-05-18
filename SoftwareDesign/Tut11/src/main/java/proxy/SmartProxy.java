package proxy;

public class SmartProxy implements Database {

    public SmartProxy(RealDatabase database) {
        this.database = database;
    }

    RealDatabase database;
    @Override
    public void connectTo(String db) throws Exception {
        if(db.equals("public-db")) {
            database.connectTo(db);
        } else {
            throw new Exception("No access provided to non-public database");
        }
    }
}
