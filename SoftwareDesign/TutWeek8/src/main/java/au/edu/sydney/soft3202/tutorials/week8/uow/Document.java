package au.edu.sydney.soft3202.tutorials.week8.uow;

public class Document {
    private int id;
    private SlowDatabase backingDatabase;
    private String contents;

    public Document(int id, SlowDatabase backingDatabase) {
        this.id = id;
        this.backingDatabase = backingDatabase;
    }

    public int getID() {
        return this.id;
    }

    public String getContents() {
        System.out.println("Getting contents");
        return backingDatabase.getDocumentContents(this.id);
    }

    public void setContents(String contents) {
        System.out.println("Setting contents");
        this.contents = contents;
        this.backingDatabase.saveDocumentContents(this.id, this.contents);
    }

    public void appendToContents(String append) {
        System.out.println("Appending to contents");
        this.contents = contents + append;
        this.backingDatabase.saveDocumentContents(this.id, this.contents);
    }
}
