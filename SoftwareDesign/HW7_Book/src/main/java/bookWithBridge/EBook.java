package bookWithBridge;

public class EBook implements BookType {
    @Override
    public String getBookType() {
        return "an Ebook";
    }
}
