package bookWithBridge;

public class ShortBook implements BookLength{

    @Override
    public String getBookLength() {
        return "Short Book";
    }
}
