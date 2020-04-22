package au.edu.sydney.soft3202.tutorials.week8.uow;

public class MainDriver {
    public static void main(String[] args) {
        SlowDatabase database = new SlowDatabase();

        Document first = new Document(0, database);
        first.setContents("First document");
        Document second = new Document(1, database);
        second.setContents("Second document");
        Document third = new Document(2, database);
        third.setContents("Third document");
        Document fourth = new Document(3, database);
        fourth.setContents("Fourth document");

        System.out.println("First document contents:\n" + first.getContents());
        // Add in your own get behaviour here, do some appending, etc.





        // Use this here
        // YourUnitOfWorkObject.commit();
    }
}
